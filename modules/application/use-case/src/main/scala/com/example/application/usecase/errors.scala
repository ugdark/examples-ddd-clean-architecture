package com.example.application.usecase

import com.example.domain.{EntityId, InvalidError, StackTraceSupport}

/** application層で発生するErrorはすべてこちらを継承する DomainErrorはすべてApplicationErrorに変換する事
  */
sealed abstract class ApplicationError(val errorCode: ErrorCode, val args: Any*)
    extends StackTraceSupport {

  override def toString: String =
    s"""${getClass.getName}($errorCode, [${args.mkString(", ")}])
       |${stackTrace.map(s => s"  at $s").mkString("\n")}
    """.stripMargin
}

/** 想定外のExceptionキャッチ用
  */
case class SystemError(cause: Throwable) extends ApplicationError(ApplicationErrorCodes.SystemError)

/** 想定外のExceptionのキャッチ用 UseCase
  */
case class UseCaseSystemError(cause: Throwable)
    extends UseCaseError(ApplicationErrorCodes.SystemError)

/** UseCase等で発生するのはこれをベースにする
  */
abstract class UseCaseError(errorCode: ErrorCode, args: Any*)
    extends ApplicationError(errorCode, args)

/*
  これ以後は汎用的な物を記載する
 */

///** 入力チェックErrorを保持する
//  */
//case class InValidError(field: String, message: String)

/** 入力チェックErrorを保持する
  */
case class ValidationError(validations: Seq[InvalidError])
    extends UseCaseError(ApplicationErrorCodes.Validation)

case class NotFoundError(id: EntityId) extends UseCaseError(ApplicationErrorCodes.NotFound)

object ApplicationErrorCodes {
  val SystemError = "error.system"

  val Validation = "error.validation"

  val NotFound = "error.notFound"

}
