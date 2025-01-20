import Dependencies._

ThisBuild / scalaVersion := "2.13.15"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.kittsville"
ThisBuild / organizationName := "kittsville"

lazy val root = (project in file("."))
  .settings(
    name := "Advent of Code 2023",
    libraryDependencies ++= Seq(
      cats,
      munit % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
