package com.aringeri.crashdata.db
import scalikejdbc._

final case class AtmosphericCond(
  accidentNo: String,
  atmosphCond: Int,
  atmosphCondSeq: Int,
  atmosphCondDesc: String)

object AtmosphericCond extends SQLSyntaxSupport[AtmosphericCond] {
  override val tableName = "atmospheric_cond"
  def apply(a: ResultName[AtmosphericCond])(rs: WrappedResultSet) = new AtmosphericCond(
    rs.string(a.accidentNo),
    rs.int(a.atmosphCond),
    rs.int(a.atmosphCondSeq),
    rs.string(a.atmosphCondDesc)
  )
}
    