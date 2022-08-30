package com.example.domain

trait DomainError {
  val message: String
  val code: String = getClass.getSimpleName.replace("$", "")
  val cause: Option[Throwable]
}

trait RepositoryError extends DomainError

/** 永続化層のErrorをまとめる。 業務に依存するErrorの定義はしない
  */
object RepositoryError {

  /** 楽観ロックErrorを格納する
    * @param message
    *   メッセージ
    * @param cause
    *   DBからのErrorを格納
    */
  case class OptimisticLockError(message: String, cause: Option[Throwable] = None)
      extends RepositoryError

}

// 入力例外を取り扱う
final case class InvalidError(message: String, cause: Option[Throwable] = None) extends DomainError

// 存在しない場合をエラーとする場合
final case class NotFoundError(message: String, cause: Option[Throwable] = None) extends DomainError

/** 想定外のAdaptor層のError格納
  * @param message
  *   メッセージ
  * @param cause
  *   Errorを格納
  */
case class AdaptorError(message: String, cause: Option[Throwable] = None) extends DomainError

/** 想定外のIOCSetting
  */
case class UnexpectedContextError(message: String, cause: Option[Throwable] = None)
    extends DomainError
