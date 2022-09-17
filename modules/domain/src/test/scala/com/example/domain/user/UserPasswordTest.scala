package com.example.domain.user

import com.kokodayo.dodai.test.UnitTest

class UserPasswordTest extends UnitTest {

  describe("UserPasswordTest") {
    it("暗号化確認") {
      val actual = UserRowPassword("test1234").generateHash
      actual shouldBe UserPassword(
        "937e8d5fbb48bd4949536cd65b8d35c426b80d2f830c5c308e2cdec422ae2244"
      )
    }
  }
}
