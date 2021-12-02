import Dependencies._
import ReidCustomCiSettings.workflow

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val `server` = (project in file("server"))
  .settings(
    name := "dev-cake-server",
    libraryDependencies ++= Seq(
      akkaHttp,
      akkaStreams,
      akkaActorTyped,
      tapir,
      tapirAkkaHttp,
      tapirSwagger,
      logback,
      scalaLogging,
      scalaTest
    )
  )


lazy val root = (project in file("."))
  .settings(
    name := "dev-cake",
    scalacOptions += "-Ypartial-unification"
  )
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .aggregate(`server`)

// github actions
ThisBuild / githubWorkflowGenerate := workflow
ThisBuild / githubWorkflowGeneratedCI := Seq(workflow)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
