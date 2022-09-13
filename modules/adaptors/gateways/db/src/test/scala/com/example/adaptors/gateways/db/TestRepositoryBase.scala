package com.example.adaptors.gateways.db

import com.example.domain.{EntityMetaDataCreator, IOContext}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, FixtureTestSuite, Outcome}
import scalikejdbc._

/** repositoryTest用の基底クラス scalikejdbc.scalatest.AutoRollbackを参考にIOContextに改良してる。
  */
trait TestRepositoryBase
    extends FixtureAnyFunSpec
    with BeforeAndAfterEach
    with ScalaFutures
    with Matchers
    with DBSettings
    with LoanPattern { self: FixtureTestSuite =>
  scalikejdbc.config.DBs.setupAll()

  implicit protected val metaDataCreator: EntityMetaDataCreator = EntityMetaDataCreatorImpl

  override type FixtureParam = IOContext

  /** commitしてみたい時用 false(default): rollbackIfActive true: commit
    */
  protected val commitMode: Boolean = false

  /** 現在のIOCのTransactionからのDBSessionに暗黙で置き換えてる これでどこでもSQLの実行が可能になってる。
    * @return
    */
  implicit def session(implicit ioc: IOContext): DBSession =
    ioc.asInstanceOf[IOContextOnSkinny].session

  /** 現在のSessionからIOCに暗黙で置き換えてる
    * @return
    */
  implicit def ioc(implicit session: DBSession): IOContext =
    IOContextOnSkinny(session, ConnectionPoolName.Write)

  def db(): DB = NamedDB(ConnectionPoolName.Write.name).toDB()

  /** Prepares database for the test.
    * @param ioc
    *   IOContext implicitly
    */
  def fixture(implicit ioc: IOContext): Unit = {}

  def withFixture(test: OneArgTest): Outcome =
    using(db()) { implicit db =>
      try {
        db.begin()

        db withinTx { implicit session =>
          fixture(ioc(session))
        }
        withFixture(
          test.toNoArgTest(ioc(db.withinTxSession()))
        )
      } finally
        if (commitMode) {
          db.commit()
        } else {
          db.rollbackIfActive()
        }

    }

  override protected def beforeEach(): Unit = super.beforeEach()

  override protected def afterEach(): Unit = super.afterEach()
}
