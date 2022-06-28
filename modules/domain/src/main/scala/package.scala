package com.example

import cats.data.ValidatedNel

package object domain {

  // 入力例外を表す
  type ValidationResult[A] = ValidatedNel[InvalidError, A]

  /**
    * 識別子を表す
    * @tparam T 型
    */
  trait Identifier[T] {
    val value: T
  }

  /**
    * Longの識別子を表す
    */
  trait IdentifierLong extends Identifier[Long] {
    override val value: Long
  }

  /**
    * Entityの永続化層で取り扱う。
    */
  trait EntityTimestamp {
    val createdAt: Option[CreatedAt]
    val updatedAt: Option[UpdatedAt]
  }

  type EnumColumn = com.example.infrastructure.enumeratum.EnumColumn

  type Enum[A <: EnumColumn] = com.example.infrastructure.enumeratum.Enum[A]
}
