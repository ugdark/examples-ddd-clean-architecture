package com.example.adaptors.gateways.db.tables.records

import com.example.adaptors.gateways.db.EntityMetaDataOnRDSTimestamp
import com.example.domain.user.{User, UserId, UserName, UserPassword}

import java.time.Instant

protected[db] case class UserRecord(
  id: Option[Long],
  name: String,
  password: String,
  createdAt: Option[Instant],
  updatedAt: Option[Instant]
) {

  /** @return
    *   User Entity
    * @throws NoSuchElementException
    *   if the option is empty.
    */
  def toEntity: User =
    User(
      UserId(id.map(_.toString).get),
      UserName(name),
      UserPassword(password),
      metaData = EntityMetaDataOnRDSTimestamp(
        createdAt = createdAt.get,
        updatedAt = updatedAt.get
      )
    )

}

protected[db] object UserRecord {

  def fromEntity(entity: User): UserRecord = {
    val meta = entity.metaData.asInstanceOf[EntityMetaDataOnRDSTimestamp]
    UserRecord(
      Some(entity.id.value.toLong),
      entity.name.value,
      entity.password.value,
      createdAt = Some(meta.createdAt.value),
      updatedAt = Some(meta.updatedAt.value)
    )
  }

}
