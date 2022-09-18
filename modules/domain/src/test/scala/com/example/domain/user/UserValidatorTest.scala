package com.example.domain.user

import cats.data.Chain
import cats.data.Validated.Invalid
import com.example.domain.{EntityMetaData, IOContext}
import com.kokodayo.dodai.test.UnitTest

class UserValidatorTest extends UnitTest {

  describe("UserValidatorTest") {
    describe("UserId") {
      val userIdValidator = new UserIdValidator() {}

      it("必須入力") {
        userIdValidator.valid("") shouldBe Invalid(Chain(UserInvalidError.Id()))
      }

      it("長さ確認") {
        userIdValidator.valid(scala.util.Random.alphanumeric.take(21).mkString) shouldBe Invalid(
          Chain(UserInvalidError.Id())
        )
      }
    }

    describe("Entityの検証") {

      implicit val userRepositoryValidator: UserRepositoryValidator = UserRepositoryOnMemory

      implicit val ioc: IOContext = IOContext.Empty

      it("2つの要素に対してエラーが出力される事") {
        UserValidator.valid(
          scala.util.Random.alphanumeric.take(21).mkString,
          scala.util.Random.alphanumeric.take(21).mkString,
          scala.util.Random.alphanumeric.take(8).mkString,
          new EntityMetaData() {}
        ) shouldBe Invalid(
          Chain(
            UserInvalidError.Id(),
            UserInvalidError.Name()
          )
        )
      }

    }
  }
}
