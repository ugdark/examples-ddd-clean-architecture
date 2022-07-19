package com.example.adaptors.dbs
package rds.tables.records

import rds.EntityMetaDataOnRDSTimestamp

import com.example.domain.user.{User, UserId, UserName}

import java.time.Instant

protected[rds] case class UserRecord(
    id: Option[Long],
    name: String,
    createdAt: Option[Instant],
    updatedAt: Option[Instant]
) {

  /**
    *
    * @return User Entity
    * @throws NoSuchElementException if the option is empty.
    */
  def toEntity: User = {
    User(
      UserId(id.map(_.toString).get),
      UserName(name),
      metaData = EntityMetaDataOnRDSTimestamp(
        createdAt = createdAt.get,
        updatedAt = updatedAt.get
      )
    )
  }

}

protected[rds] object UserRecord {

  def fromEntity(entity: User): UserRecord = {
    val meta = entity.metaData.asInstanceOf[EntityMetaDataOnRDSTimestamp]
    UserRecord(
      Some(entity.id.value.toLong),
      entity.name.value,
      createdAt = Some(meta.createdAt.value),
      updatedAt = Some(meta.updatedAt.value)
    )
  }

}
