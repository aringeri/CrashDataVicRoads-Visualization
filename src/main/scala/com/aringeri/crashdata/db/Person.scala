package com.aringeri.crashdata.db
import scalikejdbc._

final case class Person(
  accidentNo: String,
  personId: String,
  vehicleId: String,
  sex: String,
  age: String,
  ageGroup: String,
  injLevel: Int,
  injLevelDesc: String,
  seatingPosition: String,
  helmetBeltWorn: Int,
  roadUserType: String,
  roadUserTypeDesc: String,
  licenceState: String,
  pedestMovement: String,
  postcode: String,
  takenHospital: String,
  ejectedCode: String)

object Person extends SQLSyntaxSupport[Person] {
  override val tableName = "person"
  def apply(p: ResultName[Person])(rs: WrappedResultSet) = new Person(
    rs.string(p.accidentNo),
    rs.string(p.personId),
    rs.string(p.vehicleId),
    rs.string(p.sex),
    rs.string(p.age),
    rs.string(p.ageGroup),
    rs.int(p.injLevel),
    rs.string(p.injLevelDesc),
    rs.string(p.seatingPosition),
    rs.int(p.helmetBeltWorn),
    rs.string(p.roadUserType),
    rs.string(p.roadUserTypeDesc),
    rs.string(p.licenceState),
    rs.string(p.pedestMovement),
    rs.string(p.postcode),
    rs.string(p.takenHospital),
    rs.string(p.ejectedCode)
  )
}
    