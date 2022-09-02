package com.example.domain.user

import com.example.domain.{DomainEvent, DomainEventError, EntityMetaData}

trait UserEvent extends DomainEvent {
  val userId: UserId
}

case class UserFailure(
  userId: UserId
) extends UserEvent
    with DomainEventError

case class UserCreated(
  userId: UserId,
  userName: UserName,
  metaData: EntityMetaData
) extends UserEvent

case class UserEdited(
  userId: UserId,
  userName: UserName,
  metaData: EntityMetaData
) extends UserEvent
