import Dependencies._
import sbtghactions.WorkflowStep.{Sbt, Use}

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"
//ThisBuild / githubWorkflowJavaVersions := Seq("adopt@17")
//ThisBuild / githubWorkflowPublishTargetBranches := Seq(RefPredicate.Equals(Ref.Branch("develop")))
//ThisBuild / WorkflowStep.Use( sbtghactions.UseRef.Public("actions", "setup-java@v2", "17"))
//ThisBuild / githubWorkflowTargetBranches := Seq("develop")

val checkoutStep: WorkflowStep = Use(UseRef.Public("actions", "checkout", "v2"))
val javaParams: Map[String, String] = Map("distribution" -> "temurin", "java-version" -> "17")
val checkoutJavaStep: WorkflowStep = Use(UseRef.Public("actions", "setup-java", "v2"), javaParams, name = Some("Eclipse Tumurin"))
val runSbtTest: WorkflowStep = Sbt(commands = List("test"), name = Some("sbt test"))
val workflow: WorkflowJob = WorkflowJob("build-and-test-with-java-17", name = "build-and-test-with-java-17", List(checkoutJavaStep, runSbtTest), javas = List("temurin@17"))
//ThisBuild / githubWorkflowGeneratedCI := Seq(workflow)
//ThisBuild / githubWorkflowGenerate := workflow
//ThisBuild / githubWorkflowGeneratedCI := Seq(workflow)

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
