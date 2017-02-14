package com.aringeri.crashdata.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scalikejdbc._

import scala.io.StdIn

object WebServer extends App with MapService {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher

  // initialize JDBC driver & connection pool
  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:postgresql:maptest", "alex", "")

  val hostname = "localhost"
  val port = if (args.length > 0) args(0).toInt else 8080

  val bindingFuture = Http().bindAndHandle(routes, hostname, port)

  println("Server online at http://" + hostname + ":" + port + "\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind)
    .onComplete(_ => system.terminate)
}