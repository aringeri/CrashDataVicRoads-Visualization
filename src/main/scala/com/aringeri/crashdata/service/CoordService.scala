package com.aringeri.crashdata.service

import com.aringeri.crashdata.db.{Accident, Node}
import com.aringeri.crashdata.json.geojson.{Feature, FeatureCollection, Geometry, Properties}
import scalikejdbc._

import scala.math._
import scala.collection.immutable.NumericRange

object CoordService {
  private val EARTH_RADIUS_KM = 6371
}

class CoordService {
  implicit val session = AutoSession

  def eventsWithin(lon: Double, lat: Double, radius: Double): FeatureCollection = {
    val features = lookupAccidents(lon, lat, radius)
    FeatureCollection(features)
  }

  private def lookupAccidents(lon: Double, lat: Double, radius: Double): Array[Feature] = {
    val lonRange = getLonRange(lon, lat, radius)
    val latRange = getLatRange(lat, radius)

    val accidents = queryAccidents(lonRange, latRange)

    accidents
      .filter(_.node.isDefined)
      .filter(acc => withinRange(acc.node.get, lon, lat, radius) )
      .map { acc =>
        Feature(
          Geometry("Point", Array(acc.node.get.long, acc.node.get.lat)),
          Properties("Accident#: " + acc.accidentNo,
            s"""
            Date: ${acc.accidentdate}<br>
            Time: ${acc.accidenttime}<br>
            # Vehicles: ${acc.noOfVehicles}<br>
            Killed: ${acc.noPersonsKilled} / ${acc.noPersons}<br>
            Description: ${acc.dcaDescription}<br>
            """
          )
        )
      }
      .toArray
  }

  private def queryAccidents(
      lonRange: NumericRange[Double],
      latRange: NumericRange[Double]): List[Accident] = {

    val (a, n) = (Accident.syntax("a"), Node.syntax("n"))

    sql"""
      select
        ${a.result.*}, ${n.result.*}
      from
        ${Accident.as(a)} left join ${Node.as(n)} on ${a.nodeId} = ${n.nodeId}
      where
        ${n.lat} between ${latRange.start} and ${latRange.end}
        and ${n.long} between ${lonRange.start} and ${lonRange.end}
        limit 1000
      """
      .map(Accident(a.resultName, n.resultName))
      .list
      .apply()
  }

  private def lookupFeatures(lon: Double, lat: Double, range: Double): Array[Feature] = {
    val latRange = getLatRange(lat, range)
    val lonRange = getLonRange(lon, lat, range)

    val n = Node.syntax("n")
    val nodes = sql"""select * from ${Node.as(n)}
      where ${n.lat} between ${latRange.start} and ${latRange.end}
       and ${n.long} between ${lonRange.start} and ${lonRange.end}
       limit 1000"""
      .map(Node(n.resultName)).list().apply()

    nodes
      .filter(withinRange(_, lon, lat, range))
      .map(n =>
        Feature(
          Geometry("Point", Array(n.long, n.lat)),
          Properties("Region: " + n.regionName)
        )
      )
      .toArray
  }

  private def withinRange(node: Node, lon1R: Double, lat1R: Double, range: Double): Boolean = {
    val lon1 = toRadians(lon1R)
    val lat1 = toRadians(lat1R)
    val lon2 = toRadians(node.long)
    val lat2 = toRadians(node.lat)

    val d = acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon1 - lon2)) *
      CoordService.EARTH_RADIUS_KM

    d <= range
  }

  private def getLatRange(lat: Double, range: Double): NumericRange[Double] = {
    val r = calcAngularRadius(range)

    val lRad = getLatRangeRadians(toRadians(lat), r)
    toDegrees(lRad.start) to toDegrees(lRad.end) by lRad.step
  }

  private def getLatRangeRadians(lat: Double, angularRadius: Double) = {
    (lat - angularRadius) to (lat + angularRadius) by 1e-5
  }

  private def getLonRange(lon: Double, lat: Double, range: Double): NumericRange[Double] = {
    val r = calcAngularRadius(range)
    val lRad = getLonRangeRadians(toRadians(lon), toRadians(lat), r)

    toDegrees(lRad.start) to toDegrees(lRad.end) by lRad.step
  }

  private def getLonRangeRadians(lon: Double, lat: Double, angularRadius: Double) = {
    val deltaLon = asin(sin(angularRadius)/cos(lat))

    (lon - deltaLon) to (lon + deltaLon) by 1e-5
  }

  private def calcAngularRadius(range: Double): Double = range/CoordService.EARTH_RADIUS_KM
}