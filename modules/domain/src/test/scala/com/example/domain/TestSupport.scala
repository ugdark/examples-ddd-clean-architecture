package com.example.domain

import java.util.concurrent.atomic.AtomicLong

trait TestSupport {

  object MetaDataCreatorFixture extends EntityMetaDataCreator {
    override def create: EntityMetaData = MetaDataEmpty
  }

  // テスト用共通IdGenerator
  implicit val idGenerator: EntityIdGenerator = IdGeneratorFixture

  private object IdGeneratorFixture extends EntityIdGenerator {

    private val atomicLong = new AtomicLong(1L)

    override def generate: String = atomicLong.getAndIncrement().toString
  }

  private object MetaDataEmpty extends EntityMetaData

  // テスト用共通metaDataCreator
  implicit val metaDataCreator: EntityMetaDataCreator = MetaDataCreatorFixture

}
