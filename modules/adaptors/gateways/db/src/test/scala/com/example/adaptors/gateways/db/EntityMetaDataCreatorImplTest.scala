package com.example.adaptors.gateways.db

import com.example.domain.EntityMetaData
import com.kokodayo.dodai.test.UnitTest

class EntityMetaDataCreatorImplTest extends UnitTest {

  describe("EntityMetaDataCreatorImplTest") {
    it("should create") {
      val meta: EntityMetaData = EntityMetaDataCreatorImpl.create
      meta.asInstanceOf[EntityMetaDataOnRDSTimestamp]
      succeed
    }
  }
}
