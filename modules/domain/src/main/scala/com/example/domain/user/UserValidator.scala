package com.example.domain.user

import cats.implicits.{catsSyntaxTuple3Semigroupal, catsSyntaxValidatedIdBinCompat0}
import com.example.domain
import com.example.domain.{EntityMetaData, IOContext, Validator}

import scala.util.control.NonFatal

protected[user] object UserValidator {

  def valid(
    id: String,
    name: String,
    password: String,
    metaData: EntityMetaData
  )(implicit
    repositoryValidator: UserRepositoryValidator,
    ioc: IOContext
  ): domain.ValidationResult[User] = {

    // VOの制約確認
    val validatedVO = validationVO(id, name, password)

    // EntityのVO同士制約確認
    val validatedEntity = validationEntity(metaData, validatedVO)

    // 永続先の制約確認
    val validatedRepository = validationRepository(validatedEntity)

    validatedRepository
  }

  // VOの制約確認
  private def validationVO(
    id: String,
    name: String,
    password: String
  ): domain.ValidationResult[(UserId, UserName, UserPassword)] =
    (
      UserId.valid(id),
      UserName.valid(name),
      UserRowPassword.valid(password)
    ).mapN { case (id, name, password) => (id, name, password) }

  // EntityのVO同士制約確認
  private def validationEntity(
    metaData: EntityMetaData,
    validatedVO: domain.ValidationResult[(UserId, UserName, UserPassword)]
  ): domain.ValidationResult[User] =
    validatedVO.andThen { case (id, name, password) =>
      try
        User(id, name, password, metaData).validNec
      catch {
        case _: IllegalArgumentNameAndPasswordSameException =>
          UserInvalidError.NameAndPasswordSame().invalidNec
        case NonFatal(e) =>
          throw new IllegalArgumentException("user validation error", e)
      }
    }

  // EntityのVO同士制約確認
  private def validationRepository(
    validatedEntity: domain.ValidationResult[User]
  )(implicit
    repositoryValidator: UserRepositoryValidator,
    ioc: IOContext
  ): domain.ValidationResult[User] =
    validatedEntity.andThen { user =>
      if (repositoryValidator.verifyForDuplicateNames(user.name)) {
        UserInvalidError.DuplicationName().invalidNec
      } else user.validNec
    }

}

protected[user] trait UserIdValidator extends Validator[String, UserId] {
  override def valid(value: String): domain.ValidationResult[UserId] =
    try
      UserId(value).validNec
    catch {
      case NonFatal(_) =>
        UserInvalidError.Id().invalidNec
    }
}

protected[user] trait UserNameValidator extends Validator[String, UserName] {
  override def valid(value: String): domain.ValidationResult[UserName] =
    try
      UserName(value).validNec
    catch {
      case NonFatal(_) =>
        UserInvalidError.Name().invalidNec
    }
}

protected[user] trait UserRawPasswordValidator extends Validator[String, UserPassword] {
  override def valid(value: String): domain.ValidationResult[UserPassword] =
    try
      UserRowPassword(value).generateHash.validNec
    catch {
      case NonFatal(_) =>
        UserInvalidError.Password().invalidNec
    }
}
