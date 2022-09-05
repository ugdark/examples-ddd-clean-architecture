package com.example.domain.user

import cats.implicits.{catsSyntaxTuple3Semigroupal, catsSyntaxValidatedIdBinCompat0}
import com.example.domain
import com.example.domain.{EntityMetaData, Validator}

import scala.util.control.NonFatal

protected[user] trait UserValidator {
  def valid(
    id: String,
    name: String,
    password: String,
    metaData: EntityMetaData
  ): domain.ValidationResult[User] =
    (
      UserId.valid(id),
      UserName.valid(name),
      UserRowPassword.valid(password)
    ).mapN { case (id, name, password) =>
      User(
        id = id,
        name = name,
        password = password,
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

protected[user] trait UserRawPasswordValidator extends Validator[String, UserPassword] {
  override def valid(value: String): domain.ValidationResult[UserPassword] =
    try
      UserRowPassword(value).generateHash.validNec
    catch {
      case NonFatal(_) =>
        UserInvalidError.Password.invalidNec
    }
}
