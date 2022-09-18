package com.example.domain.user

import com.example.domain.InvalidError

sealed abstract class UserInvalidError extends InvalidError

/** Userのドメインでの入力エラーの一覧
  */
object UserInvalidError {
  final private val MessagePrefix: String = "ユーザーの"

  case class Id(
    field: String = "user.id",
    message: String = s"${MessagePrefix}IdはUUIDの形式です。"
  ) extends UserInvalidError

  case class DuplicationId(
    field: String = "user.id",
    message: String = s"${MessagePrefix}Idが既に使用されてます。"
  ) extends UserInvalidError

  case class Name(
    field: String = "user.name",
    message: String = s"${MessagePrefix}名前は${UserName.MaxLength}文字以内です。"
  ) extends UserInvalidError

  case class DuplicationName(
    field: String = "user.name",
    message: String = s"${MessagePrefix}名前が既に使用されてます。"
  ) extends UserInvalidError

  case class Password(
    field: String = "user.password",
    message: String =
      s"${MessagePrefix}パスワードは${UserRowPassword.MinLength}以上${UserRowPassword.MaxLength}文字以内です。"
  ) extends UserInvalidError

  case class NameAndPasswordSame(
    field: String = "user",
    message: String = s"${MessagePrefix}名前とパスワードは同じにできません。"
  ) extends UserInvalidError

}
