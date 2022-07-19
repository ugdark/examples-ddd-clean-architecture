package com.example.adaptors.dbs

import com.example.domain.{IOContext, IOContextProvider}
import scalikejdbc._

/**
  * IOContextのDB関係のProvider
  * Skinny,SlickのSampleになればと思いあえて２つ定義してる
  */
object IOContextOnDBsProvider {

  /**
    * 実践でよくありそうな例でMaster/Slaveでの実装分けしてる。
    * ClusterならRead/Writeをただ1つ定義すれば良い
    */
  object OnSkinny {
    class Read() extends IOContextProvider {
      override def get: IOContext =
        IOContextOnSkinny(ReadOnlyNamedAutoSession(ConnectionPoolNames.Read.name), ConnectionPoolNames.Read)

      override def withTransaction[T](f: IOContext => T): T = throw new Exception("can not use.")
    }

    class Write() extends IOContextProvider {
      override def get: IOContext =
        IOContextOnSkinny(NamedAutoSession(ConnectionPoolNames.Write.name), ConnectionPoolNames.Write)

      override def withTransaction[T](f: IOContext => T): T = {
        using(DB(ConnectionPool.borrow(ConnectionPoolNames.Write.name))) { implicit db =>
          try {
            db.begin() // トランザクションの開始

            val result = db withinTx { implicit session =>
              f(IOContextOnSkinny(session, ConnectionPoolNames.Write))
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

}
