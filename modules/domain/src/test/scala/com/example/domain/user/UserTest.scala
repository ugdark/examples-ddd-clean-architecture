package com.example.domain.user

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class UserTest extends AnyFunSpec with Matchers {

  describe("ユーザーを") {

    it("作成する") {
      val user  = UserFixture.generate()
      val user2 = UserFixture.generate()
      user should not be user2
    }

    it("比較する") {
      val user = UserFixture.generate()
      // idでの比較になるのでtrue
      user === user.copy(name = UserName("name")) shouldBe true
      user == user.copy(name = UserName("name")) shouldBe true
      user.equals(user.copy(name = UserName("name"))) shouldBe true
    }

    it("object比較する") {
      val user = UserFixture.generate()
      // 値も含んで比較するのでfalse
      user.equalsValue(user.copy(name = UserName("name"))) shouldBe false
    }

  }
}
