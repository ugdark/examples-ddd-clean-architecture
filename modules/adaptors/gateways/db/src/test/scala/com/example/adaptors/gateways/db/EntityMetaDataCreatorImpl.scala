package com.example.adaptors.gateways.db

import com.example.domain.{CreatedAt, EntityMetaData, EntityMetaDataCreator, UpdatedAt}

import java.time.Instant
import java.time.temporal.ChronoUnit

object EntityMetaDataCreatorImpl extends EntityMetaDataCreator {
  override def create: EntityMetaData = {
    val now = Instant.now().truncatedTo(ChronoUnit.SECONDS)
    EntityMetaDataOnRDSTimestamp(
      CreatedAt(now),
      UpdatedAt(now)
    )
  }
}
