package com.example.domain
package user

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class UserRepositoryOnMemoryTest extends AnyFunSpec with ScalaFutures with Matchers {

  describe("UserRepositoryの") {

    // 他のRepositoryTestでは不要
    it("ライフサイクルの確認") {
      implicit val ioc: IOContext = IOContext.Empty
      val repository              = new UserRepositoryOnMemory()
      for {
        _          <- repository.clear()
        create     <- repository.store(UserFixture.generate())
        findCreate <- repository.findById(create.id)
        update     <- repository.store(create.copy(name = UserName("hoge")))
        findUpdate <- repository.findById(create.id)
        delete     <- repository.deleteById(create.id)
        findDelete <- repository.findById(create.id)
      } yield {
        findCreate shouldBe Some(create)
        findUpdate shouldBe Some(update)
        delete shouldBe true
        findDelete shouldBe None
      }

    }
  }

}
