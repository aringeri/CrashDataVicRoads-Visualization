package com.aringeri.crashdata.db
import scalikejdbc._

final case class AccidentEvent(
  accidentNo: String,
  eventSeqNo: Int,
  eventType: String,
  eventTypeDesc: String,
  vehicle1Id: String,
  vehicle1CollPt: String,
  vehicle1CollPtDesc: String,
  vehicle2Id: String,
  vehicle2CollPt: String,
  vehicle2CollPtDesc: String,
  personId: String,
  objectType: Int,
  objectTypeDesc: String)

object AccidentEvent extends SQLSyntaxSupport[AccidentEvent] {
  override val tableName = "accident_event"
  def apply(a: ResultName[AccidentEvent])(rs: WrappedResultSet) = new AccidentEvent(
    rs.string(a.accidentNo),
    rs.int(a.eventSeqNo),
    rs.string(a.eventType),
    rs.string(a.eventTypeDesc),
    rs.string(a.vehicle1Id),
    rs.string(a.vehicle1CollPt),
    rs.string(a.vehicle1CollPtDesc),
    rs.string(a.vehicle2Id),
    rs.string(a.vehicle2CollPt),
    rs.string(a.vehicle2CollPtDesc),
    rs.string(a.personId),
    rs.int(a.objectType),
    rs.string(a.objectTypeDesc)
  )
}
    