import sbt.{AutoPlugin, ThisBuild, plugins}
import sbtghactions.GenerativeKeys._
import sbtghactions.GenerativePlugin.autoImport.{UseRef, WorkflowJob, WorkflowStep}
import sbtghactions.WorkflowStep.{Sbt, Use}

object GitHubActionsSbt extends AutoPlugin {
  override def requires = plugins.JvmPlugin

  override def trigger = allRequirements


  ThisBuild / githubWorkflowJavaVersions := Seq("adopt@17")
  ThisBuild / githubWorkflowTargetBranches := Seq("develop")
  //  ThisBuild / githubWorkflowPublishTargetBranches := Seq("main")

//  val checkoutStep: WorkflowStep = Use(UseRef.Public("actions", "checkout", "v2"))
//  val javaParams: Map[String, String] = Map("distribution" -> "temurin", "java-version" -> "17")
//  val checkoutJavaStep: WorkflowStep = Use(UseRef.Public("actions", "setup-java", "v2"), javaParams, name = Some("Eclipse Tumurin"))
  val runSbtTest: WorkflowStep = Sbt(commands = List("test"), name = Some("sbt test"))
  val workflow: WorkflowJob = WorkflowJob(
    id = "buildAndTest",
    name = "Build and Test",
    steps = List(WorkflowStep.CheckoutFull, WorkflowStep.SetupScala, runSbtTest),
    javas = List("temurin@17"),

  )
  ThisBuild / githubWorkflowGenerate := workflow
  ThisBuild / githubWorkflowGeneratedCI := Seq(workflow)
}