package com.aringeri.crashdata.db
import scalikejdbc._

final case class NodeIdComplexIntId(
  accidentNo: String,
  nodeId: Int,
  complexIntNo: Int)

object NodeIdComplexIntId extends SQLSyntaxSupport[NodeIdComplexIntId] {
  override val tableName = "node_id_complex_int_id"
  def apply(n: ResultName[NodeIdComplexIntId])(rs: WrappedResultSet) = new NodeIdComplexIntId(
    rs.string(n.accidentNo),
    rs.int(n.nodeId),
    rs.int(n.complexIntNo)
  )
}
    