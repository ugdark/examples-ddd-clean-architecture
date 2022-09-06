package com.example.domain.user

import cats.data.Chain
import cats.data.Validated.Invalid
import com.example.domain.EntityMetaData
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class UserValidatorTest extends AnyFunSpec with Matchers {

  describe("UserValidatorTest") {
    describe("UserId") {
      val userIdValidator = new UserIdValidator() {}

      it("必須入力") {
        userIdValidator.valid("") shouldBe Invalid(Chain(UserInvalidError.Id))
      }

      it("長さ確認") {
        userIdValidator.valid(scala.util.Random.alphanumeric.take(21).mkString) shouldBe Invalid(
          Chain(UserInvalidError.Id)
        )
      }
    }

    describe("Entityの検証") {
      val userValidator                                             = new UserValidator() {}
      implicit val userRepositoryValidator: UserRepositoryValidator = UserRepositoryOnMemory

      it("2つの要素に対してエラーが出力される事") {
        userValidator.valid(
          scala.util.Random.alphanumeric.take(21).mkString,
          scala.util.Random.alphanumeric.take(21).mkString,
          scala.util.Random.alphanumeric.take(8).mkString,
          new EntityMetaData() {}
        ) shouldBe Invalid(
          Chain(
            UserInvalidError.Id,
            UserInvalidError.Name
          )
        )
      }

    }
  }
}
