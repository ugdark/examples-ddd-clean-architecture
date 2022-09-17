package com.example.adaptors.gateways.db

import com.example.domain.EntityMetaDataCreator
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterAll, Outcome, Suite}
import scalikejdbc.config.DBs
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{DB, NamedDB}

trait DBsTestSupport extends BeforeAndAfterAll { this: Suite =>

  override protected def beforeAll(): Unit = {
    DBs.setupAll()
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    try super.afterAll()
    finally
      DBs.closeAll()
  }

}

/** skinny系のAutoRollbackテスト用
  */
abstract class AutoRollbackSpec
    extends FixtureAnyFunSpec
    with ScalaFutures
    with Matchers
    with AutoRollback
    with DBsTestSupport {

  implicit protected val entityMetaDataCreator: EntityMetaDataCreator = EntityMetaDataCreatorImpl

  /** commitしてみたい時用 false(default): rollbackIfActive true: commit
    */
  protected val commitMode: Boolean = false

  override def withFixture(test: OneArgTest): Outcome =
    using(db()) { db =>
      try {
        db.begin()
        db.withinTx { implicit session =>
          fixture(session)
        }
        withFixture(test.toNoArgTest(db.withinTxSession()))
      } finally
        if (commitMode) {
          db.commit()
        } else {
          db.rollbackIfActive()
        }

    }

  override def db(): DB = NamedDB(ConnectionPoolName.Write.name).toDB()

}
