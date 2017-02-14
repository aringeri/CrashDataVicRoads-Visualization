package com.aringeri.crashdata.db
import scalikejdbc._

final case class Node(
  accidentNo: String,
  nodeId: Int,
  nodeType: String,
  amgX: Double,
  amgY: Double,
  lgaName: String,
  regionName: String,
  degUrbanName: String,
  lat: Double,
  long: Double,
  postcodeNo: String)

object Node extends SQLSyntaxSupport[Node] {
  override val tableName = "node"
  def apply(n: ResultName[Node])(rs: WrappedResultSet) = new Node(
    rs.string(n.accidentNo),
    rs.int(n.nodeId),
    rs.string(n.nodeType),
    rs.double(n.amgX),
    rs.double(n.amgY),
    rs.string(n.lgaName),
    rs.string(n.regionName),
    rs.string(n.degUrbanName),
    rs.double(n.lat),
    rs.double(n.long),
    rs.string(n.postcodeNo)
  )
}
    