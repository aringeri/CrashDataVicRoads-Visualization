package com.aringeri.crashdata.tool

import java.io.File
import scala.io.Source
import java.nio.file.{Path,Paths}
import java.nio.file.Files

object SqlTableToCaseClass {
  val outPath = Paths.get("src/main/scala/db")
  val targetPackage = "com.aringeri.crashdata.db"
  val sqlToScala = Map(
      "varchar" -> "String",
      "integer" -> "Int",
      "date" -> "java.sql.Date",
      "double precision" -> "Double")
      
  val resultSetMethods = Map(
      "varchar" -> "string",
      "integer" -> "int",
      "date" -> "date",
      "double precision" -> "double")

  def sqlToCaseClass(sqlPath: String, outPath: String): Unit =
    sqlToCaseClass(Paths.get(sqlPath), Paths.get(outPath))


  def sqlToCaseClass(sqlPath: Path, outPath: Path): Unit = {
    sqlPath.toFile.listFiles()
      .filter(_.getName endsWith ".sql")
      .map(createCaseClass)
      .foreach { case (name, content) =>
        Files.write(outPath.resolve(Paths.get(name + ".scala")), content.getBytes) }
  }
  
  def createCaseClass(file: File): (String, String) = {
    val fileName = FileUtil.trimExtension(file)
    val className = toClassName(fileName)
    
    val sqlFields = findSqlFields(file)
    
    val scalaFields = convertToScala(sqlFields)
    
    val constructorArgs = scalaFields.map(f => s"  ${f._1}: ${f._2}").mkString(",\n")

    val nameProvider = className.charAt(0).toLower

    val resultSetConv = //sqlFields
      sqlFields.map(_._2).zip(scalaFields.map(_._1))
      .map { case (sqlType, fieldName) =>
        s"""    rs.${resultSetMethods(sqlType)}($nameProvider.$fieldName)""" }
      .mkString(",\n")
    
    val contents = s"""package $targetPackage
      |import scalikejdbc._
      |
      |final case class $className(
      |$constructorArgs)
      |
      |object $className extends SQLSyntaxSupport[$className] {
      |  override val tableName = "${fileName.toLowerCase}"
      |  def apply($nameProvider: ResultName[$className])(rs: WrappedResultSet) = new $className(
      |$resultSetConv
      |  )
      |}
    """.stripMargin('|')
    
    className -> contents
  }
  
  def toClassName(fileName: String): String = {
    fileName.zipWithIndex
      .map( p => { 
        if (p._2 == 0 || fileName(p._2 - 1) == '_')
          p._1.toUpper 
        else 
          p._1.toChar.toLower
      })
      .filter(_ != '_')
      .mkString
  }
  
  def findSqlFields(file: File): List[(String, String)] = {
    Source.fromFile(file).getLines()
      .filter(!_.startsWith("create table"))
      .filter(!_.trim.startsWith("primary key"))
      .filter(!_.trim.startsWith(")"))
      .map(_.replaceAll("(primary key)|,|\\)", "").trim.split("\\s+", 2))
      .filter(_.length > 1)
      .map(words => words(0) -> words(1))
      .toList
  }
  
  def convertToScala(sqlFields: Seq[(String, String)]): List[(String, String)] = {
    sqlFields.map { case (name, datatype) =>
      toFieldName(name) -> sqlToScala.getOrElse(datatype, "")
    }.toList
  }
  
  def toFieldName(sqlName: String): String = {
    val name = toClassName(sqlName)
    name(0).toLower + name.drop(1)
  }
}