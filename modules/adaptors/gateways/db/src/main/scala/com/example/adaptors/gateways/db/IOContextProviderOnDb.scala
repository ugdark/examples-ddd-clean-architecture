package com.example.adaptors.gateways.db

import com.example.application.usecase.IOContextProvider
import com.example.domain.IOContext
import scalikejdbc.{ConnectionPool, DB, DBSession, using}

/** IOContextのDB関係のProvider Skinny,SlickのSampleになればと思いあえて２つ定義してる 実践でよくありそうな例でMaster/Slaveでの実装分けしてる。
  * ClusterならRead/Writeをただ1つ定義すれば良い
  */
trait IOContextProviderOnDb extends IOContextProvider {
  override def write[T](f: IOContext => T): T =
    using(DB(ConnectionPool.borrow(ConnectionPoolName.Write.name))) { implicit db =>
      try {
        db.begin() // トランザクションの開始

        val result = db withinTx { implicit session =>
          val ioc = new IOContext with ScalikeJdbcSessionHolder {
            override val dbSession: DBSession       = session
            override val dbName: ConnectionPoolName = ConnectionPoolName.Write
          }
          f(ioc)
        }
        result match {
          case Right(_) => db.commit()           // トランザクションをコミット
          case Left(_)  => db.rollbackIfActive()
          case _        => db.rollbackIfActive() // トランザクションをコミット
        }

        result
      } catch {
        case e: Exception =>
          db.rollbackIfActive()
          throw e // 例外が throw される可能性はない
      }
    }

  override def read[T](f: IOContext => T): T =
    using(DB(ConnectionPool.borrow(ConnectionPoolName.Read.name))) { implicit db =>
      db readOnly { session =>
        val ioc = new IOContext with ScalikeJdbcSessionHolder {
          override val dbSession: DBSession       = session
          override val dbName: ConnectionPoolName = ConnectionPoolName.Read
        }
        f(ioc)
      }
    }
}
