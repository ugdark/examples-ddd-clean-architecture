package com.example.adaptors.gateways.db

import com.example.domain.user.UserFixture

import scala.util.Success

class UserRepositoryOnSkinnyTest extends TestRepositoryBase {

  private val userRepository = UserRepositoryOnSkinny

  describe("UserRepositoryOnSkinnyTest") {
    it("保存、更新、削除のライフサイクル確認") { implicit ioc =>
      val newEntity = UserFixture.generate()

      val stored = userRepository.store(newEntity)
      stored shouldBe Success(newEntity)

      val find = userRepository.findById(newEntity.id)
      find shouldBe Success(Some(newEntity))

    }
  }

}
