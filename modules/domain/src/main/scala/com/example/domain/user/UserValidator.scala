package com.example.domain.user

import cats.implicits.{catsSyntaxTuple2Semigroupal, catsSyntaxValidatedIdBinCompat0}
import com.example.domain
import com.example.domain.{EntityMetaData, Validator}

import scala.util.control.NonFatal

protected[user] trait UserValidator {
  def valid(
    id: String,
    name: String,
    metaData: EntityMetaData
  ): domain.ValidationResult[User] =
    (
      UserId.valid(id),
      UserName.valid(name)
    ).mapN { case (id, name) =>
      User(
        id = id,
        name = name,
        metaData = metaData
      )
    }
}

protected[user] trait UserIdValidator extends Validator[String, UserId] {
  override def valid(value: String): domain.ValidationResult[UserId] =
    try
      UserId(value).validNec
    catch {
      case NonFatal(_) =>
        UserInvalidError.Id.invalidNec
    }
}

protected[user] trait UserNameValidator extends Validator[String, UserName] {
  override def valid(value: String): domain.ValidationResult[UserName] =
    try
      UserName(value).validNec
    catch {
      case NonFatal(_) =>
        UserInvalidError.Name.invalidNec
    }
}
