package com.example

import cats.data.ValidatedNel

package object domain {

  trait Value[T] {
    def value: T
  }

  /**
    * 識別子を表す, EntityId
    * StringにしてるがRDS側でLongで保持してたとしてもStringなら許容できるので基本String
    */
  trait EntityId extends Value[String]

  /**
    * ドメインの関心事ではない物を保持する
    * 楽観ロックや作成者情報とか色々と
    */
  trait EntityMetaData

  trait EntityMetaDataCreator {
    def create: EntityMetaData
  }

  /** NOTE: エンティティIDの採番方法を抽象化します。 */
  trait EntityIdGenerator {
    def generate(): String
  }

  trait Entity[ID <: EntityId] {
    val id: ID
    val metaData: EntityMetaData

    // NOTE: 型 と ID でエンティティの同一性を判断します。
    override def equals(obj: Any): Boolean =
      obj match {
        case that: Entity[_] => this.getClass == that.getClass && this.id == that.id
        case _               => false
      }

    /* やっぱり値全比較も復活しておきたいので */
    def equalsValue(obj: Any): Boolean = super.equals(obj)

    override def hashCode(): Int = 31 + id.hashCode

  }

  // 入力例外を表す
  type ValidationResult[A] = ValidatedNel[InvalidError, A]

  //  type EnumColumn = com.example.infrastructure.enumeratum.EnumColumn
  //
  //  type Enum[A <: EnumColumn] = com.example.infrastructure.enumeratum.Enum[A]
}
