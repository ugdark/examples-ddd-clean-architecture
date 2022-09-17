package com.example.application.usecase

import com.example.domain.InvalidError
import com.kokodayo.dodai.test.SnapshotMatcher
import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers

class ApplicationErrorTest extends FixtureAnyFunSpec with Matchers with SnapshotMatcher {

  private case class LoginIdError(field: String) extends InvalidError {
    override val message: String = "LoginIdは4文字以上20文字以下半角英数字で必須入力です"
  }

  describe("Application層のError確認") {
    it("ValidationErrorの使い方確認") { implicit test =>
      // InValidの定義がめんどいのでとりあえずfieldにはjson.pathを記載する予定
      val validations = Seq(
        LoginIdError("user.id"),
        LoginIdError("user.name")
      )
      val validationError = ValidationError(validations)

      // 可視化用に変換する
      val inValidList = validationError.validations.map { inValid =>
        // Map("field" -> inValid.field, "message" -> inValid.message)
        Map("message" -> inValid.message)
      }
      Map("ValidationError" -> inValidList) should matchSnapshot()
    }
  }

}
