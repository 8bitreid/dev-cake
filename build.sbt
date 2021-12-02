import Dependencies._
import sbtghactions.WorkflowStep.{Sbt, Use}

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

val runSbtTest: WorkflowStep = Sbt(commands = List("test"), name = Some("sbt test"))
val javaParams: Map[String, String] = Map("distribution" -> "temurin", "java-version" -> "17")
val checkoutJavaStep: WorkflowStep = Use(UseRef.Public("actions", "setup-java", "v2"), javaParams, name = Some("Eclipse Tumurin"))


val workflow: WorkflowJob = WorkflowJob(
  id = "buildAndTest",
  name = "Build and Test",
  steps = List(WorkflowStep.Checkout, checkoutJavaStep, WorkflowStep.SetupScala, runSbtTest),
  javas = List()
)
ThisBuild / githubWorkflowGenerate := workflow
ThisBuild / githubWorkflowGeneratedCI := Seq(workflow)


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

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
