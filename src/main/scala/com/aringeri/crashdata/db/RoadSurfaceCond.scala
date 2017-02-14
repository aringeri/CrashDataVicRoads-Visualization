package com.aringeri.crashdata.db
import scalikejdbc._

final case class RoadSurfaceCond(
  accidentNo: String,
  surfaceCond: Int,
  surfaceCondDesc: String,
  surfaceCondSeq: Int)

object RoadSurfaceCond extends SQLSyntaxSupport[RoadSurfaceCond] {
  override val tableName = "road_surface_cond"
  def apply(r: ResultName[RoadSurfaceCond])(rs: WrappedResultSet) = new RoadSurfaceCond(
    rs.string(r.accidentNo),
    rs.int(r.surfaceCond),
    rs.string(r.surfaceCondDesc),
    rs.int(r.surfaceCondSeq)
  )
}
    