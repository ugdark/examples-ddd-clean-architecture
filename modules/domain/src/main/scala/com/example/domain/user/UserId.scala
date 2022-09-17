package com.example.domain.user

import com.example.domain.{EntityId, EntityIdGenerator}

case class UserId(value: String) extends AnyVal with EntityId

object UserId extends (String => UserId) with UserIdValidator {
  val MaxLength: Int = 20

  def newId(implicit idGen: EntityIdGenerator): UserId = UserId(idGen.generate)

  def apply(value: String): UserId = {
    require(value != null && value.length <= UserId.MaxLength && value.nonEmpty)
    new UserId(value)
  }
}
