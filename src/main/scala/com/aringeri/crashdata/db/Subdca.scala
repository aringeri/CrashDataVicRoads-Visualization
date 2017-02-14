package com.aringeri.crashdata.db
import scalikejdbc._

final case class Subdca(
  accidentNo: String,
  subDcaCode: String,
  subDcaSeq: Int,
  subDcaCodeDesc: String)

object Subdca extends SQLSyntaxSupport[Subdca] {
  override val tableName = "subdca"
  def apply(s: ResultName[Subdca])(rs: WrappedResultSet) = new Subdca(
    rs.string(s.accidentNo),
    rs.string(s.subDcaCode),
    rs.int(s.subDcaSeq),
    rs.string(s.subDcaCodeDesc)
  )
}
    