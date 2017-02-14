package com.aringeri.crashdata.tool

import java.io.File
import com.github.tototoshi.csv.CSVParser
import scala.io.Source
import java.nio.file.Paths
import java.nio.file.Files


object CsvToSqlTable {
  val csvPath = Paths.get("resources/ACCIDENT")
  val outPath = Paths.get("src/main/sql")
  
  def main(args: Array[String]) {
    csvPath.toFile.listFiles
      .map(file => file -> FileUtil.trimExtension(file))
      .map(pair => pair._2 -> buildTableDef(pair._1, pair._2))
      .foreach(pair => Files.write(outPath.resolve(pair._1 + ".sql"), pair._2.getBytes))
  }
  
  private def buildTableDef(file: File, tableName: String): String = {
    "create table " + tableName + " (\n" +
       listColumnDefs(file, "  ") +
    "\n);"
  }
  
  private def listColumnDefs(file: File, indent: String = ""): String = {
    def getHeader(file: File): List[String] = {
      val header = Source.fromFile(file).getLines.next
      CSVParser.parse(header, '\\', ',', '"')
        .getOrElse(List.empty) 
    }
    
    getHeader(file)
      .map(indent + buildColumnDef(_))
      .mkString(",\n")
  }
  
  private def buildColumnDef(colName: String): String = {
    def fixBlank : String => String = _.replaceAll("\\s", "_")
    
    def guessSqlType(name: String): String = {
      val lower = name.toLowerCase
      if (lower.endsWith("id") || lower.contains("id_")) "integer"
      else if (lower.contains("date")) "date"
      else if (lower.contains("time")) "time"
      else "varchar"
    }
    
    val name = fixBlank(colName)
    name + " " + guessSqlType(name)
  }
}