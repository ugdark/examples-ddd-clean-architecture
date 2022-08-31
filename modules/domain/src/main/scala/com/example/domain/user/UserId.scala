package com.example.domain.user

import com.example.domain.{EntityId, EntityIdGenerator}

case class UserId(value: String) extends EntityId {
  require(value != null && value.length <= UserId.MaxLength && value.nonEmpty)
}

object UserId extends (String => UserId) {
  val MaxLength: Int = 20

  def newId(implicit idGen: EntityIdGenerator): UserId = UserId(idGen.generate())
}
