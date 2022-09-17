package com.example.adaptors.gateways.db

import com.example.domain.{EntityMetaDataCreator, IOContext}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{FixtureTestSuite, Outcome}
import scalikejdbc.*

/** repositoryTest用の基底クラス scalikejdbc.scalatest.AutoRollbackを参考にIOContextに改良してる。
  */
abstract class TestRepositorySpec
    extends FixtureAnyFunSpec
    with ScalaFutures
    with Matchers
    with DBsTestSupport
    with LoanPattern { self: FixtureTestSuite =>
  scalikejdbc.config.DBs.setupAll()

  implicit protected val entityMetaDataCreator: EntityMetaDataCreator = EntityMetaDataCreatorImpl

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

  def withFixture(test: OneArgTest): Outcome =
    using(db()) { implicit db =>
      try {
        db.begin()

//        db withinTx { implicit session =>
//          fixture(ioc(session))
//        }
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

  def db(): DB = NamedDB(ConnectionPoolName.Write.name).toDB()

//  /** Prepares database for the test.
//    * @param ioc
//    *   IOContext implicitly
//    */
//  def fixture(implicit ioc: IOContext): Unit = ()

}
