package com.example.domain
package user

case class UserId(value: Long) extends IdentifierLong

case class UserName(value: String) {
  require(value.nonEmpty && value.length <= 20)
}

case class User(
    id: UserId,
    name: UserName,
    override val createdAt: Option[CreatedAt] = None,
    override val updatedAt: Option[UpdatedAt] = None
) extends EntityTimestamp
