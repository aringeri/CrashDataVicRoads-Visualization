package com.aringeri.crashdata.db
import scalikejdbc._

final case class AccidentChainage(
  nodeId: String,
  routeNo: Int,
  chainageSeq: Int,
  routeLinkNo: Int,
  chainage: Int)

object AccidentChainage extends SQLSyntaxSupport[AccidentChainage] {
  override val tableName = "accident_chainage"
  def apply(a: ResultName[AccidentChainage])(rs: WrappedResultSet) = new AccidentChainage(
    rs.string(a.nodeId),
    rs.int(a.routeNo),
    rs.int(a.chainageSeq),
    rs.int(a.routeLinkNo),
    rs.int(a.chainage)
  )
}
    