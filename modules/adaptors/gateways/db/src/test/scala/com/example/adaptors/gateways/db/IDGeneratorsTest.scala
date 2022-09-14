package com.example.adaptors.gateways.db

import org.scalatest.time.{Millis, Seconds, Span}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class IDGeneratorsTest extends AutoRollbackSpec {

  implicit val defaultPatience: PatienceConfig =
    PatienceConfig(timeout = Span(5000, Seconds), interval = Span(5000, Millis))

  describe("IDGeneratorの確認") {

    it("IDを生成する") { () =>
      val first  = IDGenerators.User.generate.toLong
      val second = IDGenerators.User.generate.toLong
      first should be < second
    }

    it("IDを連続生成して重複がない事") { () =>
      val resultFs: Seq[Future[String]] = (1 to 100).map { _ =>
        Future(IDGenerators.User.generate)
      }

      val values   = Future.sequence(resultFs).futureValue
      val distinct = values.distinct

      values.size shouldBe distinct.size

    }
  }
}
