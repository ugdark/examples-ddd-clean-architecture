package com.example.domain

import java.util.concurrent.atomic.AtomicLong

trait TestSupport {

  private object IdGeneratorFixture extends EntityIdGenerator {

    private val atomicLong = new AtomicLong(1L)

    override def generate: String = atomicLong.getAndIncrement().toString
  }

  // テスト用共通IdGenerator
  implicit val idGenerator: EntityIdGenerator = IdGeneratorFixture

  private object MetaDataEmpty extends EntityMetaData

  object MetaDataCreatorFixture extends EntityMetaDataCreator {
    override def create: EntityMetaData = MetaDataEmpty
  }

  // テスト用共通metaDataCreator
  implicit val metaDataCreator: EntityMetaDataCreator = MetaDataCreatorFixture

}
