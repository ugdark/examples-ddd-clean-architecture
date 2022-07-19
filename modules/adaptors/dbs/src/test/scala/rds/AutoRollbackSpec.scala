package com.example.adaptors.dbs
package rds

import com.example.domain.{CreatedAt, EntityMetaData, EntityMetaDataCreator, UpdatedAt}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{DB, NamedDB}

import java.time.Instant

/**
  * skinny系のAutoRollbackテスト用
  */
abstract class AutoRollbackSpec
    extends FixtureAnyFunSpec
    with ScalaFutures
    with Matchers
    with DBSettings
    with AutoRollback {

  object EntityMetaDataCreatorImpl extends EntityMetaDataCreator {
    override def create: EntityMetaData =
      EntityMetaDataOnRDSTimestamp(
        CreatedAt(Instant.now()),
        UpdatedAt(Instant.now())
      )
  }

  override def db(): DB = NamedDB(ConnectionPoolNames.Write.name).toDB()

}
