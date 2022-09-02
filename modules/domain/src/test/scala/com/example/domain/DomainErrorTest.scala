package com.example.domain

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class DomainErrorTest extends AnyFunSpec with Matchers {

  object TestError extends DomainError()

  it("挙動確認用") {
    val error = TestError
    // 大事な事はerrorを定義した行数がstackTraceに出力されてる事を確認
    // testコマンド実行時のに調整してるのでIDEで実行だとおちるかも
    // 無理そうならsuccessかignoreしとけばいい。
    val expected =
      """com.example.domain.DomainErrorTest$TestError$
        |  at com.example.domain.DomainErrorTest.TestError$lzycompute$1(DomainErrorTest.scala:8)
        |  at com.example.domain.DomainErrorTest.TestError(DomainErrorTest.scala:8)
        |  at com.example.domain.DomainErrorTest.$anonfun$new$1(DomainErrorTest.scala:11)
        |  at org.scalatest.OutcomeOf.outcomeOf(OutcomeOf.scala:85)
        |  at org.scalatest.OutcomeOf.outcomeOf$(OutcomeOf.scala:83)
        |  at org.scalatest.OutcomeOf$.outcomeOf(OutcomeOf.scala:104)
        |  at org.scalatest.Transformer.apply(Transformer.scala:22)
        |  at org.scalatest.Transformer.apply(Transformer.scala:20)
        |  at org.scalatest.funspec.AnyFunSpecLike$$anon$1.apply(AnyFunSpecLike.scala:517)
        |  at org.scalatest.TestSuite.withFixture(TestSuite.scala:196)
        |  at org.scalatest.TestSuite.withFixture$(TestSuite.scala:195)
        |  at org.scalatest.funspec.AnyFunSpec.withFixture(AnyFunSpec.scala:1631)
        |  at org.scalatest.funspec.AnyFunSpecLike.invokeWithFixture$1(AnyFunSpecLike.scala:515)
        |  at org.scalatest.funspec.AnyFunSpecLike.$anonfun$runTest$1(AnyFunSpecLike.scala:527)
        |  at org.scalatest.SuperEngine.runTestImpl(Engine.scala:306)
        |  at org.scalatest.funspec.AnyFunSpecLike.runTest(AnyFunSpecLike.scala:527)
        |  at org.scalatest.funspec.AnyFunSpecLike.runTest$(AnyFunSpecLike.scala:509)
        |  at org.scalatest.funspec.AnyFunSpec.runTest(AnyFunSpec.scala:1631)
        |  at org.scalatest.funspec.AnyFunSpecLike.$anonfun$runTests$1(AnyFunSpecLike.scala:560)
        |  at org.scalatest.SuperEngine.$anonfun$runTestsInBranch$1(Engine.scala:413)
        |  at scala.collection.immutable.List.foreach(List.scala:333)
        |  at org.scalatest.SuperEngine.traverseSubNodes$1(Engine.scala:401)
        |  at org.scalatest.SuperEngine.runTestsInBranch(Engine.scala:396)
        |  at org.scalatest.SuperEngine.runTestsImpl(Engine.scala:475)
        |  at org.scalatest.funspec.AnyFunSpecLike.runTests(AnyFunSpecLike.scala:560)
        |  at org.scalatest.funspec.AnyFunSpecLike.runTests$(AnyFunSpecLike.scala:559)
        |  at org.scalatest.funspec.AnyFunSpec.runTests(AnyFunSpec.scala:1631)
        |  at org.scalatest.Suite.run(Suite.scala:1114)
        |  at org.scalatest.Suite.run$(Suite.scala:1096)
        |  at org.scalatest.funspec.AnyFunSpec.org$scalatest$funspec$AnyFunSpecLike$$super$run(AnyFunSpec.scala:1631)
        |  at org.scalatest.funspec.AnyFunSpecLike.$anonfun$run$1(AnyFunSpecLike.scala:564)
        |  at org.scalatest.SuperEngine.runImpl(Engine.scala:535)
        |  at org.scalatest.funspec.AnyFunSpecLike.run(AnyFunSpecLike.scala:564)
        |  at org.scalatest.funspec.AnyFunSpecLike.run$(AnyFunSpecLike.scala:563)
        |  at org.scalatest.funspec.AnyFunSpec.run(AnyFunSpec.scala:1631)
        |  at org.scalatest.tools.Framework.org$scalatest$tools$Framework$$runSuite(Framework.scala:321)
        |  at org.scalatest.tools.Framework$ScalaTestTask.execute(Framework.scala:516)
        |  at sbt.ForkMain$Run.lambda$runTest$1(ForkMain.java:413)
        |  at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        |  at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130)
        |  at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:630)
        |  at java.base/java.lang.Thread.run(Thread.java:832)
        |    """.stripMargin
    error.toString shouldBe expected
  }

}
