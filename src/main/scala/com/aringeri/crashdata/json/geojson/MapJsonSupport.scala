package com.aringeri.crashdata.json.geojson

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

final case class FeatureCollection(features: Array[Feature], `type`: String = "FeatureCollection")
final case class Feature(geometry: Geometry, properties: Properties, `type`: String = "Feature")
final case class Geometry(`type`: String, coordinates: Array[Double])
final case class Properties(name: String, description: String = "")

trait MapJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val propertiesFormat = jsonFormat2(Properties)
  implicit val geometryFormat = jsonFormat2(Geometry)
  implicit val featureFormat = jsonFormat3(Feature)
  implicit val featureCollectionFormat = jsonFormat2(FeatureCollection)
}