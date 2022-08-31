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

}
