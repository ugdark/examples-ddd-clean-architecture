package com.example.domain

trait DomainError {

  protected val stackTrace: Array[StackTraceElement] = {
    val traces = Thread.currentThread().getStackTrace
    traces.drop(traces.lastIndexWhere(t => t.getClassName == getClass.getName) + 1)
  }

  override def toString: String =
    s"""${getClass.getName}
       |${stackTrace.map(s => s"  at $s").mkString("\n")}
    """.stripMargin
}
