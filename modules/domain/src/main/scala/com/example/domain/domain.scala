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
}

/** NOTE: エンティティIDの採番方法を抽象化します。 */
trait EntityIdGenerator {
  def generate: String
}

/** ドメインの関心事ではない物を保持する 楽観ロックや作成者情報とか色々と
  */
trait EntityMetaData

object EntityMetaData {
  val Empty: EntityMetaData = new EntityMetaData {}
}
trait EntityMetaDataCreator {
  def create: EntityMetaData
}

trait DomainEvent

trait DomainError extends StackTraceSupport

// ------------------
/*
  Validation関係(入力チェック)
 */
// ------------------

trait Validator[T, VO] {

  def valid(value: T): ValidationResult[VO]
}

//trait ValueValidation[T] extends Any with Value[T] with Validation

// 入力例外を表す
trait InvalidError {
  val message: String
//  val cause: Option[Throwable] = None
}

/** Domain層ですべての入力チェック担う用にするのでこちらで定義してる。
  */
case class ValidatedError(invalids: Seq[InvalidError]) extends DomainError

case class DomainResult[+EVENT <: DomainEvent, +ENTITY <: Entity[_ <: EntityId]](
  event: EVENT,
  entity: ENTITY
)
