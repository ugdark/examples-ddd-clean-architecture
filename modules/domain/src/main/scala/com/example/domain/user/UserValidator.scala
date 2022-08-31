package com.example.domain.user

import cats.data.ValidatedNec
import cats.implicits.{catsSyntaxTuple2Semigroupal, catsSyntaxValidatedIdBinCompat0}
import com.example.domain.EntityMetaDataCreator

import scala.util.control.NonFatal

/** Domainの制約を入力Errorとして取り扱うためのクラス catsのValid以上に簡易な作りがちょっとできなかったので今はcatsに依存させる
  * 永続化先の重複チェック等はこちらの責務としない方向ですすめる。別のクラスでラップして使う想定にする。
  */
object UserValidator {

  // 入力例外を表す
  type ValidationResult[A] = ValidatedNec[UserInvalidError, A]

  def valid(id: String, name: String)(implicit
    metaDataCreator: EntityMetaDataCreator
  ): ValidationResult[User] =
    (
      validId(id),
      validName(name)
    ).mapN { case (id, name) =>
      User(
        id = id,
        name = name,
        metaData = metaDataCreator.create
      )
    }

  def validId(value: String): ValidationResult[UserId] =
    try
      UserId(value).validNec
    catch {
      case NonFatal(_) =>
        UserInvalidError.Id.invalidNec
    }

  def validName(value: String): ValidationResult[UserName] =
    try
      UserName(value).validNec
    catch {
      case NonFatal(_) =>
        UserInvalidError.Name.invalidNec
    }

}
