package com.example.application.usecase

sealed abstract class UseCaseError(val errorCode: ErrorCode, val args: Any*) {

  protected val stackTrace: Array[StackTraceElement] = {
    val traces = Thread.currentThread().getStackTrace
    traces.drop(traces.lastIndexWhere(t => t.getClassName == getClass.getName) + 1)
  }

  override def toString: String =
    s"""${getClass.getName}($errorCode, [${args.mkString(", ")}])
       |${stackTrace.map(s => s"  at $s").mkString("\n")}
    """.stripMargin
}

object ServiceErrorCodes {
  val SystemError = "error.system"

  val validation = "error.validation"

  val NotFound = "error.notFound"

}
