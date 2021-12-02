package dev.cake.service

import dev.cake.fixtures.Fixtures.testName
import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext

class LogicSpec extends AnyFunSpec with Matchers {
  implicit val ec = ExecutionContext.global
  describe("service logic") {
    val service =  new Logic()
    describe("greeting") {
      it("should return a friendly greeting given a name") {
        val expected = s"hello, bennett!"
        val greeting = service.greeting(testName).futureValue
        assert(greeting == expected)
      }
    }
  }
}
