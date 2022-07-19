//package com.example.adaptors.dbs
//package rds2
//
//import com.example.domain.IOContext
//import org.scalatest.{BeforeAndAfterEach, Outcome, SuiteMixin, TestSuite, fixture}
//import scalikejdbc.{DBSession, _}
//
///**
//  * AutoRollbackの移行期間の間は`TRUNCATE`を追加し対応する
//  * ConnectPoolName, IOContextOn~~は今後消したいと思ってるがまずは
//  * AutoRollbackの処理を共通して書けるように作成
//  * BeforeAndAfterEachは一旦定義してますが、`TRUNCATE`が不要になったら消したい
//  * コメント：
//  *  QueryProcessor系はSELECT系なのでそもそもTransactionを渡さない用に設計してるでcommitが今の所必要になります
//  *  直接DBのcommitが必要な場合は下記の用にbeforeEachなどで別のトランザクションを生成して実行してください
//  *
//  * db() autoCommit { implicit session => }
//  *
//  */
//trait BaseAutoRollbackSupport extends LoanPattern with BeforeAndAfterEach with SuiteMixin { self: Suite =>
//
//  scalikejdbc.config.DBs.setupAll()
//
//  type FixtureParam = IOContext
//
//  /**
//    * commitしてみたい時用
//    * false(default): rollbackIfActive
//    * true: commit
//    */
//  def commitMode(): Boolean = false
//
//  /**
//    * Creates a [[scalikejdbc.DB]] instance.
//    * @return DB instance
//    */
//  def db(): DB
//
//  /**
//    * 現在のIOCのTransactionからのDBSessionに暗黙で置き換えてる
//    * これでどこでもSQLの実行が可能になってる。
//    * @return
//    */
//  implicit def session(implicit ioc: IOContext): DBSession
//
//  /**
//    * 現在のSessionからIOCに暗黙で置き換えてる
//    * @return
//    */
//  implicit def ioc(implicit session: DBSession): IOContext
//
//  /**
//    * Prepares database for the test.
//    * @param ioc IOContext implicitly
//    */
//  def fixture(implicit ioc: IOContext): Unit = {}
//
//  /**
//    * Provides transactional block
//    * @param test one arg test
//    */
//  override def withFixture(test: OneArgTest): Outcome = {
//    using(db()) { implicit db =>
//      try {
//        db.begin() // トランザクションの開始
//
//        db withinTx { implicit session =>
//          fixture(ioc(session))
//        }
//        withFixture(
//          test.toNoArgTest(ioc(db.withinTxSession()))
//        )
//      } finally {
//        if (commitMode()) {
//          db.commit()
//        } else {
//          db.rollbackIfActive()
//        }
//
//      }
//    }
//
//  }
//
//}
