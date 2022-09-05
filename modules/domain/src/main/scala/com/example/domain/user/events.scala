package com.example.domain.user

import com.example.domain.{DomainEvent, EntityMetaData}

trait UserEvent extends DomainEvent {
  val userId: UserId
}

case class UserCreated(
  userId: UserId,
  userName: UserName,
  userPassword: UserPassword,
  metaData: EntityMetaData
) extends UserEvent

case class UserEdited(
  userId: UserId,
  userName: UserName,
  metaData: EntityMetaData
) extends UserEvent
