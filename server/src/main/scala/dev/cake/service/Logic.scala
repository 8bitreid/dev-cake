package dev.cake.service

import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future}

class Logic()(implicit ec: ExecutionContext) extends StrictLogging {

  def greeting(name: String): Future[String] = {
    Future {
      if (name == "bennett") {
        logger.error("not this guy again")
        s"oh, it's you again... Hi, $name"
      }
      else {
        logger.info(s"getting input from one, $name")
        s"hello, $name!"
      }
    }
  }
}
