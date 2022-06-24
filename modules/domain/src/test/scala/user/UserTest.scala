package com.example.domain
package user

import org.scalatest.funspec.AnyFunSpec

class UserTest extends AnyFunSpec {

  describe("ユーザーを") {

    it("作成する") {
      UserFixture.create()
      succeed
    }

    it("制約チェック") {
      intercept[IllegalArgumentException] {
        UserId(-1)
      }
    }
  }
}
