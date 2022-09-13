package com.example.adaptors.gateways.db

import com.example.domain.EntityMetaData
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.sql.Timestamp
import java.time.temporal.ChronoUnit
import java.time.{Instant, LocalDateTime}

class EntityMetaDataCreatorImplTest extends AnyFunSpec with Matchers {

  describe("EntityMetaDataCreatorImplTest") {
    it("should create") {
      val meta: EntityMetaData = EntityMetaDataCreatorImpl.create
      meta.asInstanceOf[EntityMetaDataOnRDSTimestamp]

      println(Instant.now().truncatedTo(ChronoUnit.SECONDS))
      println(Instant.now().toString)
      println(Instant.now().getEpochSecond)
      println(Instant.now().toEpochMilli)
      println(Timestamp.from(Instant.now()))
      println(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
    }
  }
}
