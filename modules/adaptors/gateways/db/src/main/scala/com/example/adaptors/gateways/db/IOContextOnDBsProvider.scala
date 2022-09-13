package com.example.adaptors.gateways.db

import com.example.domain.{IOContext, IOContextProvider}
import scalikejdbc.{ConnectionPool, DB, NamedAutoSession, ReadOnlyNamedAutoSession, using}

/** IOContextのDB関係のProvider Skinny,SlickのSampleになればと思いあえて２つ定義してる
  */
object IOContextOnDBsProvider {

  /** 実践でよくありそうな例でMaster/Slaveでの実装分けしてる。 ClusterならRead/Writeをただ1つ定義すれば良い
    */
  object OnSkinny {
    class Read() extends IOContextProvider {
      override def get: IOContext =
        IOContextOnSkinny(
          ReadOnlyNamedAutoSession(ConnectionPoolName.Read.name),
          ConnectionPoolName.Read
        )

      override def withTransaction[T](f: IOContext => T): T = throw new Exception("can not use.")
    }

    class Write() extends IOContextProvider {
      override def get: IOContext =
        IOContextOnSkinny(
          NamedAutoSession(ConnectionPoolName.Write.name),
          ConnectionPoolName.Write
        )

      override def withTransaction[T](f: IOContext => T): T =
        using(DB(ConnectionPool.borrow(ConnectionPoolName.Write.name))) { implicit db =>
          try {
            db.begin() // トランザクションの開始

            val result = db withinTx { implicit session =>
              f(IOContextOnSkinny(session, ConnectionPoolName.Write))
            }
            result match {
              case Right(_) => db.commit() // トランザクションをコミット
              case Left(_)  => db.rollbackIfActive()
              case _        => db.commit() // トランザクションをコミット
            }

            result
          } catch {
            case e: Exception =>
              db.rollbackIfActive()
              throw e // 例外が throw される可能性はない
          }
        }
    }
  }

}
