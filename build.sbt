import Dependencies._

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"
val http4sVersion = "1.0.0-M21"

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = (project in file("."))
  .settings(
    name := "npm-watcher",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.1.0",
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion,
      "io.circe" %% "circe-generic" % "0.14.0-M3",
      "io.circe" %% "circe-literal" % "0.14.0-M3",
      "io.circe" %% "circe-parser" % "0.14.0-M3",
      "org.typelevel" %% "jawn-fs2" % "2.0.0",
      "org.typelevel" %% "jawn-parser" % "1.0.0",
      "org.typelevel" %% "jawn-ast" % "1.0.0",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )
