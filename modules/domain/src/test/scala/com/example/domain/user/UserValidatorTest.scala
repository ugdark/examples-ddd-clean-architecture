package com.example.domain.user

import cats.data.Chain
import cats.data.Validated.Invalid
import com.example.domain.{EntityMetaData, EntityMetaDataCreator}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class UserValidatorTest extends AnyFunSpec with Matchers {

  private val userValidator = UserValidator
  implicit private val metaDataCreator: EntityMetaDataCreator = new EntityMetaDataCreator {
    override def create: EntityMetaData = new EntityMetaData {}
  }

  describe("UserValidatorTest") {
    describe("UserId") {

      it("必須入力") {
        userValidator.validId("") shouldBe Invalid(Chain(UserInvalidError.Id))
      }

      it("長さ確認") {
        userValidator.validId(scala.util.Random.alphanumeric.take(21).mkString) shouldBe Invalid(
          Chain(UserInvalidError.Id)
        )
      }
    }

    // Idと今同じにしてるので省く
    // it("should validName") {}

    describe("Entityの検証") {

      it("2つの要素に対してエラーが出力される事") {
        userValidator.valid(
          scala.util.Random.alphanumeric.take(21).mkString,
          scala.util.Random.alphanumeric.take(21).mkString
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
