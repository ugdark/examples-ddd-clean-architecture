package com.example.domain.user

import com.example.domain.DomainTestSupport
import com.kokodayo.dodai.test.UnitTest

class UserRepositoryOnMemoryTest extends UnitTest with DomainTestSupport {

  describe("UserRepositoryの") {

    // 他のRepositoryTestでは不要
    it("ライフサイクルの確認") {
      val repository = new UserRepositoryOnMemory()
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
