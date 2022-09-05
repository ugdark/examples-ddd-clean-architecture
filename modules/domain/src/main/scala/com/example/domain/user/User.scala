package com.example.domain.user

import cats.data.Validated
import com.example.domain._

case class User(
  id: UserId,
  name: UserName,
  password: UserPassword,
  metaData: EntityMetaData
) extends Entity[UserId]

object User {

  def create(
    name: String,
    password: String
  )(implicit
    idGenerator: EntityIdGenerator,
    metaDataCreator: EntityMetaDataCreator,
    validator: UserValidator
  ): Either[ValidatedError, DomainResult[UserEvent, User]] = {

    val newId = UserId.newId

    val validated = validator.valid(
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
