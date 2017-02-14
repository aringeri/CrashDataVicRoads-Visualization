package com.aringeri.crashdata.service

import akka.http.scaladsl.server.Directive.addByNameNullaryApply
import akka.http.scaladsl.server.{Directives, Route}
import com.aringeri.crashdata.json.geojson.MapJsonSupport

import scala.math.{max, min}

trait MapService extends Directives with MapJsonSupport {
  lazy val service = new CoordService()

  val routes: Route = 
    path("") {
      getFromResource("index.html")
    } ~
    path ("js" / "index.js") {
      getFromResource("js/index.js")
    } ~
    path("eventsWithin") {
      get {
        parameters('lon.as[Double], 'lat.as[Double], 'radius ? 0.1) {
          validateArgs { (long, lat, radius) =>
            complete(service.eventsWithin(long, lat, radius))
          }
        }
      }
    }

  def validateArgs(route:  (Double,Double,Double) => Route)(long: Double, lat: Double, radius: Double) = {
    validate(long >= -180 && long <= 180, s"Longitude ($long) must be in range [-180, 180]") {
      validate(lat >= -90 && lat <= 90, s"Latitude ($lat) must be in range [-90, 90]") {
        val limitedRad = max(0, min(1000, radius))
        route(long, lat, limitedRad)
      }
    }
  }
}