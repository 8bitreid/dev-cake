import sbt._

object Dependencies {

  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.2.6"
  //  private val zioVersion = "2.0.0-M6-2"
  //  lazy val zio = "dev.zio" %% "zio" % zioVersion
  //
  //  private val zhttpVersion = "1.0.0.0-RC17"
  //  lazy val zhttp = "io.d11" %% "zhttp" % zhttpVersion
  //  lazy val zhttpTest = "io.d11" %% "zhttp-test" % zhttpVersion % Test

  private val tapirVersion = "0.19.0"
  lazy val tapir = "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion
  lazy val tapirAkkaHttp = "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % tapirVersion


  //  lazy val zioHttpTapir = "com.softwaremill.sttp.tapir" %% "tapir-zio" % tapirVersion
  //  lazy val http4sTapirZio = "com.softwaremill.sttp.tapir" %% "tapir-zio-http4s-server" % tapirVersion


  // Logging
  lazy val slf4j = "org.slf4j" % "slf4j-api" % "1.7.32"
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.7"

  // Test
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9"
}