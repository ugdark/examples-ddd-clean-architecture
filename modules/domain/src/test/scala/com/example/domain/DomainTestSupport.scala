package com.example.domain

import java.util.concurrent.atomic.AtomicLong

/** Domain層で定義してるimplicitのEmptyを提供する
  */
trait DomainTestSupport {

  protected object EntityMetaDataCreatorFixture extends EntityMetaDataCreator {
    private object MetaDataEmpty extends EntityMetaData

    override def create: EntityMetaData = MetaDataEmpty
  }

  // テスト用共通metaDataCreator
  implicit protected val entityMetaDataCreatorFixture: EntityMetaDataCreator =
    EntityMetaDataCreatorFixture

  private object EntityIdGeneratorFixture extends EntityIdGenerator {

    private val atomicLong = new AtomicLong(1L)

    override def generate: String = atomicLong.getAndIncrement().toString
  }

  // テスト用共通IdGenerator
  implicit protected val entityIdGeneratorFixture: EntityIdGenerator = EntityIdGeneratorFixture

  implicit protected val ioc: IOContext = IOContext.Empty

}
