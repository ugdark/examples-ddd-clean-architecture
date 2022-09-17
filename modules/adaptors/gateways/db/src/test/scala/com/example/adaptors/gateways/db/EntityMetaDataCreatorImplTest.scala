package com.example.adaptors.gateways.db

import com.example.domain.EntityMetaData
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class EntityMetaDataCreatorImplTest extends AnyFunSpec with Matchers {

  describe("EntityMetaDataCreatorImplTest") {
    it("should create") {
      val meta: EntityMetaData = EntityMetaDataCreatorImpl.create
      meta.asInstanceOf[EntityMetaDataOnRDSTimestamp]

      succeed
    }
  }
}
