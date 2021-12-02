import sbtghactions.GenerativePlugin.autoImport.{UseRef, WorkflowJob, WorkflowStep}
import sbtghactions.WorkflowStep.{Sbt, Use}

object ReidsCustomCiSettings {
  //  val checkoutStep: WorkflowStep = Use(UseRef.Public("actions", "checkout", "v2"))
  //  val javaParams: Map[String, String] = Map("distribution" -> "temurin", "java-version" -> "17")
  //  val checkoutJavaStep: WorkflowStep = Use(UseRef.Public("actions", "setup-java", "v2"), javaParams, name = Some("Eclipse Tumurin"))
  val checkoutStep: WorkflowStep = Use(UseRef.Public("actions", "checkout", "V2"))
  val javaParams: Map[String, String] = Map("distribution" -> "temurin", "java-version" -> "17")
  val checkoutJavaStep: WorkflowStep = Use(UseRef.Public("actions", "setup-java", "v2"), javaParams, name = Some("Eclipse Tumurin"))
  val runSbtTest: WorkflowStep = Sbt(commands = List("test"), name = Some("sbt test"))

  val workflow: WorkflowJob = WorkflowJob(
    id = "buildAndTest",
    name = "Build and Test",
    steps = List(WorkflowStep.Checkout, WorkflowStep.SetupScala, runSbtTest),
    javas = List("openjdk@1.17.0")
  )
}