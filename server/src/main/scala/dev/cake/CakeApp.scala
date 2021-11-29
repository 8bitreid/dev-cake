package dev.cake

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.StrictLogging
import dev.cake.CakeApp.actorSystem.dispatcher
import sttp.tapir._
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter

import scala.concurrent.Future


object CakeApp extends App with StrictLogging {

  // the endpoint: single fixed path input ("hello"), single query parameter
  // corresponds to: GET /hello?name=...
  val helloWorld: PublicEndpoint[String, Unit, String, Any] =
  endpoint.get.in("hello").in(query[String]("name")).out(stringBody)

  // converting an endpoint to a route (providing server-side logic); extension method comes from imported packages
  val helloWorldRoute: Route =
    AkkaHttpServerInterpreter().toRoute(helloWorld.serverLogicSuccess(name => Future.successful(s"Hello, $name!")))

  // starting the server
  implicit val actorSystem: ActorSystem = ActorSystem()

  def server: Future[Http.ServerBinding] = Http().newServerAt("localhost", 8080).bindFlow(helloWorldRoute)

  server.map(server => {
    logger.info(s"now running on localhost:${server.localAddress.getPort}/")
  })
}