package com.aringeri.crashdata.db
import scalikejdbc._

final case class AccidentLocation(
  accidentNo: String,
  nodeId: Int,
  roadRoute1: String,
  roadName: String,
  roadType: String,
  roadNameInt: String,
  roadTypeInt: String,
  distanceLocation: String,
  directionLocation: String,
  nearestKmPost: String,
  offRoadLocation: String)

object AccidentLocation extends SQLSyntaxSupport[AccidentLocation] {
  override val tableName = "accident_location"
  def apply(a: ResultName[AccidentLocation])(rs: WrappedResultSet) = new AccidentLocation(
    rs.string(a.accidentNo),
    rs.int(a.nodeId),
    rs.string(a.roadRoute1),
    rs.string(a.roadName),
    rs.string(a.roadType),
    rs.string(a.roadNameInt),
    rs.string(a.roadTypeInt),
    rs.string(a.distanceLocation),
    rs.string(a.directionLocation),
    rs.string(a.nearestKmPost),
    rs.string(a.offRoadLocation)
  )
}
    