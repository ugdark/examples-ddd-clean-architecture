package com.example.adaptors.dbs

import com.example.domain.IOContext
import com.example.domain.exceptions.UnexpectedContextException
import scalikejdbc.DBSession

import scala.util.Try

/**
  * 基本的にadaptor層 dbsで使う想定だが、現場ではやっぱりUseCaseでも使っちゃう事があるだろうと予測してる。
  */
trait UseDB {

  protected def withSession[A](ioc: IOContext)(f: DBSession => Try[A]): Try[A] = {
    ioc match {
      case IOContextOnSkinny(session, _) =>
        f(session)
      case _ =>
        throw UnexpectedContextException(s"Unexpected context is bound (expected: IOContextOnSkinny, actual: $ioc)")
    }
  }

}
