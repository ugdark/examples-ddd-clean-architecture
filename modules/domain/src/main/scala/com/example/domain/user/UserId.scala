package com.example.domain.user

import com.example.domain.{EntityId, EntityIdGenerator}

case class UserId(value: String) extends EntityId {
  require(value != null && value.length <= 20 && value.nonEmpty)
}

object UserId extends (String => UserId) {
  def newId(implicit idGen: EntityIdGenerator): UserId = UserId(idGen.generate())
}
