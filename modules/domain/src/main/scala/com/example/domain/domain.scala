package com.example.domain

/*
  package object domainで書くとscalafmtで警告でるし 直下に書くと大量に作るのもいやなので
  domain.scalaってコードにまとめてます。
 */

trait Value[T] extends Any {
  def value: T
}

/** 識別子を表す, EntityId StringにしてるがRDS側でLongで保持してたとしてもStringなら許容できるので基本String
  */
trait EntityId extends Any with Value[String]

trait Entity[ID <: EntityId] {
  val id: ID
  val metaData: EntityMetaData

  // NOTE: 型 と ID でエンティティの同一性を判断します。
  override def equals(obj: Any): Boolean =
    obj match {
      case that: Entity[_] =>
        this.getClass == that.getClass && this.id == that.id
      case _ => false
    }

  /* やっぱり値全比較も復活しておきたいので */
  def equalsValue(obj: Any): Boolean = super.equals(obj)

  override def hashCode(): Int = 31 + id.hashCode

}

/** NOTE: エンティティIDの採番方法を抽象化します。 */
trait EntityIdGenerator {
  def generate(): String
}

/** ドメインの関心事ではない物を保持する 楽観ロックや作成者情報とか色々と
  */
trait EntityMetaData

trait EntityMetaDataCreator {
  def create: EntityMetaData
}

// 入力例外を表す
trait InvalidError {
  val message: String
  val cause: Option[Throwable] = None
}

/** Domain層ですべての入力チェック担う用にするのでこちらで定義してる。
  */
case class ValidatedError(invalids: Seq[InvalidError]) extends DomainError

//trait DomainError {
//  val message: String
//  val code: String = getClass.getSimpleName.replace("$", "")
//  val cause: Option[Throwable]
//}
//
//trait RepositoryError extends DomainError
//
///** 永続化層のErrorをまとめる。 業務に依存するErrorの定義はしない
//  */
//object RepositoryError {
//
//  /** 楽観ロックErrorを格納する
//    * @param message
//    *   メッセージ
//    * @param cause
//    *   DBからのErrorを格納
//    */
//  case class OptimisticLockError(message: String, cause: Option[Throwable] = None)
//      extends RepositoryError
//
//}
//
//// 入力例外を取り扱う
//final case class InvalidError(message: String, cause: Option[Throwable] = None) extends DomainError
//
//// 存在しない場合をエラーとする場合
//final case class NotFoundError(message: String, cause: Option[Throwable] = None) extends DomainError
//
///** 想定外のAdaptor層のError格納
//  * @param message
//  *   メッセージ
//  * @param cause
//  *   Errorを格納
//  */
//case class AdaptorError(message: String, cause: Option[Throwable] = None) extends DomainError
//
///** 想定外のIOCSetting
//  */
//case class UnexpectedContextError(message: String, cause: Option[Throwable] = None)
//    extends DomainError
