package com.example.domain

import scala.concurrent.Future

/**
  * repositoryなどでIOする際に利用される
  * できるだけwithXXXYYYとか第一階層で実装される事
  */
trait IOContext {

  def withFuture[T](body: => T): Future[T] = {
    Future { body }(ThreadPool.default)
  }

}

object IOContext {

  object Empty extends IOContext
}
