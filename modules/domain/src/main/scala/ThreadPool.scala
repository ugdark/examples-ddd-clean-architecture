package com.example.domain

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.ExecutionContext

/**
  * 実運用だとActorのThreadPoolで問題なさそう。
  */

object ThreadPool {

  val default: ExecutionContext = scala.concurrent.ExecutionContext.global

  // とりあえず雑に20
  val blocking: ExecutionContext = new BlockingExecutionContext(20)

}

private class BlockingExecutionContext(threadCount: Int) extends ExecutionContext {

  private val executorService: ExecutorService =
    Executors.newFixedThreadPool(threadCount)

  override def execute(runnable: Runnable): Unit =
    executorService.execute(runnable)

  override def reportFailure(cause: Throwable): Unit = throw cause
}
