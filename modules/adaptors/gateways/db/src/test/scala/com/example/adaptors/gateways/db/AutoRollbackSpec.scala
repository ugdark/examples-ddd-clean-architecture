package com.example.adaptors.gateways.db

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterAll, Outcome}
import scalikejdbc.config.DBs
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{DB, NamedDB}

/** skinny系のAutoRollbackテスト用
  */
abstract class AutoRollbackSpec
    extends FixtureAnyFunSpec
    with ScalaFutures
    with Matchers
    with BeforeAndAfterAll
    with AutoRollback {

  /** commitしてみたい時用 false(default): rollbackIfActive true: commit
    */
  protected val commitMode: Boolean = false

  override def db(): DB = NamedDB(ConnectionPoolName.Write.name).toDB()

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

  override protected def beforeAll(): Unit = {
    DBs.setupAll()
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    DBs.closeAll()
    super.afterAll()
  }
}
