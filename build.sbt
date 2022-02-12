import Dependencies._

val http4sVersion = "1.0.0-M29"
val circeVersion = "0.14.1"
ThisBuild / scalaVersion := "3.0.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.sh4869"
ThisBuild / organizationName := "sh4869"


lazy val root = (project in file("."))
  .settings(
    name := "npm-watcher",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.2.9",
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.typelevel" %% "jawn-fs2" % "2.1.0",
      "org.typelevel" %% "jawn-parser" % "1.2.0",
      "org.typelevel" %% "jawn-ast" % "1.2.0",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )
