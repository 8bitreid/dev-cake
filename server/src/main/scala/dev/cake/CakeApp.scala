package dev.cake

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.StrictLogging
import dev.cake.service.Logic
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import sttp.tapir.{PublicEndpoint, endpoint, query, stringBody}
import scala.concurrent.{ExecutionContextExecutor, Future}


object CakeApp extends App with StrictLogging {

  // starting the server
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val serviceLogic = new Logic()

  // the endpoint: single fixed path input ("hello"), single query parameter
  // corresponds to: GET /hello?name=...
  val helloWorld: PublicEndpoint[String, Unit, String, Any] = endpoint
    .get
    .in("hello")
    .in(query[String]("name"))
    .out(stringBody)

  // converting an endpoint to a route (providing server-side logic); extension method comes from imported packages
  val helloWorldRoute: Route =
    AkkaHttpServerInterpreter().toRoute(
      helloWorld.serverLogicSuccess(name =>
        serviceLogic.greeting(name))
    )

  def server: Future[Http.ServerBinding] =
    Http()
      .newServerAt("localhost", 8080)
      .bindFlow(helloWorldRoute)

  server.map(server => {
    logger.info(s"now running on http://localhost:${server.localAddress.getPort}/")
  })
}