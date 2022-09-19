package com.example.adaptors.gateways.db

import com.example.domain.IOContext
import scalikejdbc.DBSession

/** 基本的にadaptor層 dbsで使う想定だが、現場ではやっぱりUseCaseでも使っちゃう事があるだろうと予測してる。
  */
trait ScalikeJdbcAward {

  implicit def getDBSession(implicit ioc: IOContext): DBSession =
    ioc.asInstanceOf[ScalikeJdbcSessionHolder].dbSession

}

protected[db] trait ScalikeJdbcSessionHolder {
  val dbSession: DBSession
  val dbName: ConnectionPoolName
}
