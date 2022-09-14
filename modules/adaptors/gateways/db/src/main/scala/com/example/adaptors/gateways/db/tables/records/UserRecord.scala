package com.example.adaptors.gateways.db.tables.records

import com.example.adaptors.gateways.db.EntityMetaDataOnRDSTimestamp
import com.example.domain.user.{User, UserId, UserName, UserPassword}

import java.time.Instant

protected[db] case class UserRecord(
  id: Long,
  name: String,
  password: String,
  createdAt: Instant,
  updatedAt: Instant
) {

  /** @return
    *   User Entity
    * @throws NoSuchElementException
    *   if the option is empty.
    */
  def toEntity: User =
    User(
      UserId(id.toString),
      UserName(name),
      UserPassword(password),
      metaData = EntityMetaDataOnRDSTimestamp(
        createdAt = createdAt,
        updatedAt = updatedAt
      )
    )

}

protected[db] object UserRecord {

  def fromEntity(entity: User): UserRecord = {
    val meta = entity.metaData.asInstanceOf[EntityMetaDataOnRDSTimestamp]
    UserRecord(
      entity.id.value.toLong,
      entity.name.value,
      entity.password.value,
      createdAt = meta.createdAt.value,
      updatedAt = meta.updatedAt.value
    )
  }

}
