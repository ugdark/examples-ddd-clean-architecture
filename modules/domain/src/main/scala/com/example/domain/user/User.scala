package com.example.domain.user

import cats.data.Validated
import com.example.domain._

import scala.util.Try

sealed class IllegalArgumentNameAndPasswordSameException()
    extends IllegalArgumentException("requirement name and Password same. ")

case class User(
  id: UserId,
  name: UserName,
  password: UserPassword,
  metaData: EntityMetaData
) extends Entity[UserId] {

  if (Try(UserRowPassword(name.value).generateHash == password).getOrElse(false))
    throw new IllegalArgumentNameAndPasswordSameException()

}

object User {

  private val userValidator = UserValidator

  def create(
    name: String,
    password: String
  )(implicit
    idGenerator: EntityIdGenerator,
    metaDataCreator: EntityMetaDataCreator,
    repositoryValidator: UserRepositoryValidator,
    ioc: IOContext
  ): Either[ValidatedError, DomainResult[UserEvent, User]] = {

    val newId = UserId.newId

    val validated = userValidator.valid(
      newId.value,
      name,
      password,
      metaDataCreator.create
    )

    validated match {
      case Validated.Valid(user) =>
        val event = UserCreated(
          userId = user.id,
          userName = user.name,
          userPassword = user.password,
          metaData = user.metaData
        )
        Right(DomainResult(event, user))
      case Validated.Invalid(e) =>
        Left(ValidatedError(e.toChain.toList))
    }

  }
}
