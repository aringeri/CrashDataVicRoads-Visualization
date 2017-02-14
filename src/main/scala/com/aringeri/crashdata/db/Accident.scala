package com.aringeri.crashdata.db
import scalikejdbc._

final case class Accident(
  accidentNo: String,
  accidentdate: java.sql.Date,
  accidenttime: String,
  accidentType: Int,
  accidentTypeDesc: String,
  dayOfWeek: Int,
  dayWeekDescription: String,
  dcaCode: Int,
  dcaDescription: String,
  directory: String,
  edition: String,
  page: String,
  gridReferenceX: String,
  gridReferenceY: String,
  lightCondition: Int,
  lightConditionDesc: String,
  nodeId: Int,
  noOfVehicles: Int,
  noPersons: Int,
  noPersonsInj2: Int,
  noPersonsInj3: Int,
  noPersonsKilled: Int,
  noPersonsNotInj: Int,
  policeAttend: Int,
  roadGeometry: Int,
  roadGeometryDesc: String,
  severity: Int,
  speedZone: Int,
  node: Option[Node] = None)

object Accident extends SQLSyntaxSupport[Accident] {
  override val tableName = "accident"
  def apply(a: ResultName[Accident])(rs: WrappedResultSet) = new Accident(
    rs.string(a.accidentNo),
    rs.date(a.accidentdate),
    rs.string(a.accidenttime),
    rs.int(a.accidentType),
    rs.string(a.accidentTypeDesc),
    rs.int(a.dayOfWeek),
    rs.string(a.dayWeekDescription),
    rs.int(a.dcaCode),
    rs.string(a.dcaDescription),
    rs.string(a.directory),
    rs.string(a.edition),
    rs.string(a.page),
    rs.string(a.gridReferenceX),
    rs.string(a.gridReferenceY),
    rs.int(a.lightCondition),
    rs.string(a.lightConditionDesc),
    rs.int(a.nodeId),
    rs.int(a.noOfVehicles),
    rs.int(a.noPersons),
    /*TODO scalikejdbc issue where column names have numbers.
      - workaround: redo db schema without numbers
      - report. */
    0,//rs.int(a.noPersonsInj2),
    0,//rs.int(a.noPersonsInj3),
    rs.int(a.noPersonsKilled),
    rs.int(a.noPersonsNotInj),
    rs.int(a.policeAttend),
    rs.int(a.roadGeometry),
    rs.string(a.roadGeometryDesc),
    rs.int(a.severity),
    rs.int(a.speedZone)
  )

  def apply(a: ResultName[Accident], n: ResultName[Node])(rs: WrappedResultSet): Accident =  {
    apply(a)(rs).copy(node = rs.intOpt(n.nodeId).map(_ => Node(n)(rs)))
  }
}
    