package com.kokodayo.dodai.test

import org.scalatest.funspec.AsyncFunSpec
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, BeforeAndAfterEach}

import scala.concurrent.Future

/** before afterの非同期実行タイミング確認用
  */
class TestBeforeAfterCheck
    extends AsyncFunSpec
    with BeforeAndAfter
    with BeforeAndAfterEach
    with BeforeAndAfterAll {

  before(println("before"))

  after(println("after"))

  // Asyncの場合先にafterAll呼ばれてしまう。connection使う場合注意
  override protected def beforeEach(): Unit = println("beforeEach")
  override protected def afterEach(): Unit  = println("afterEach")

  // DBのconnectionPoolとかの設定した方がよさそうね
  // async時にafterAll同期しない?
  // see: https://github.com/scalatest/scalatest/issues/953#issuecomment-1091595260
  override protected def beforeAll(): Unit = println("beforeAll")
  override protected def afterAll(): Unit  = println("afterAll")

  describe("check1") {

    it("test1") {
      Future {
        Thread.sleep(100)
        println("test1")
        succeed
      }(executionContext)
    }

    it("test2") {
      println("test2")
      succeed
    }
  }
}
