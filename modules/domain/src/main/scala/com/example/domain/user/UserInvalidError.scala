package com.example.domain.user

import com.example.domain.InvalidError

sealed trait UserInvalidError

/** Userのドメインでの入力エラーの一覧
  */
object UserInvalidError {
  final private val MessagePrefix: String = "ユーザーの"

  case object Id extends UserInvalidError with InvalidError {
    override val message: String = s"${MessagePrefix}IdはUUIDの形式です。"
  }
  case object DuplicationId extends UserInvalidError with InvalidError {
    override val message: String = s"${MessagePrefix}Idが既に使用されてます。"
  }

  case object Name extends UserInvalidError with InvalidError {
    override val message: String = s"${MessagePrefix}名前は${UserName.MaxLength}文字以内です。"
  }

  case object DuplicationName extends UserInvalidError with InvalidError {
    override val message: String = s"${MessagePrefix}名前が既に使用されてます。"
  }

  case object Password extends UserInvalidError with InvalidError {
    override val message: String =
      s"${MessagePrefix}パスワードは${UserRowPassword.MinLength}以上${UserRowPassword.MaxLength}文字以内です。"
  }

  case object NameAndPasswordSame extends UserInvalidError with InvalidError {
    override val message: String =
      s"${MessagePrefix}名前とパスワードは同じにできません。"
  }

}
