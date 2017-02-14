lazy val root = (project in file(".")).
  settings(
    name.:=("CrashData"),
    mainClass in (Compile, run) := Some("com.aringeri.crashdata.service.WebServer"),
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq("com.github.tototoshi" %% "scala-csv" % "1.3.3",
      "com.typesafe.akka" %% "akka-http-experimental" % "2.4.10",
      "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.10",
      "org.scalikejdbc" %% "scalikejdbc"       % "2.4.2",
      "com.h2database"  %  "h2"                % "1.4.192",
      "ch.qos.logback"  %  "logback-classic"   % "1.1.7",
      "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
    ) 
  )

