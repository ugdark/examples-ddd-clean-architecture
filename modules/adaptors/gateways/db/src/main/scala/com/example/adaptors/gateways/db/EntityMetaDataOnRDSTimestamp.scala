package com.example.adaptors.gateways.db

import com.example.domain.{CreatedAt, EntityMetaData, UpdatedAt}

import java.time.Instant

case class EntityMetaDataOnRDSTimestamp(
  createdAt: CreatedAt,
  updatedAt: UpdatedAt
) extends EntityMetaData

object EntityMetaDataOnRDSTimestamp {

  def apply(createdAt: Instant, updatedAt: Instant): EntityMetaDataOnRDSTimestamp =
    EntityMetaDataOnRDSTimestamp(CreatedAt(createdAt), UpdatedAt(updatedAt))
}
