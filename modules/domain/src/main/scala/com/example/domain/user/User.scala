package com.example.domain.user

import com.example.domain._

case class User(
  id: UserId,
  name: UserName,
  metaData: EntityMetaData
) extends Entity[UserId]

object User {

//  def create(name: String)(implicit
//    idGenerator: EntityIdGenerator,
//    metaDataCreator: EntityMetaDataCreator,
//    validator: UserValidator,
//    ioc: IOContext
//  ): DomainResult[UserEvent, User] = {
//
//    val newId = UserId.newId
//
//    val validated = validator.valid(
//      newId.value,
//      name,
//      metaDataCreator.create
//    )
//
//  }

  //  def valid(id: String, name: String)(implicit
  //    metaDataCreator: EntityMetaDataCreator
  //  ): ValidationResult[User] =
  //    (
  //      validId(id),
  //      validName(name)
  //    ).mapN { case (id, name) =>
  //      User(
  //        id = id,
  //        name = name,
  //        metaData = metaDataCreator.create
  //      )
  //    }
  //
}
