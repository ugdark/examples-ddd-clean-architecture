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

  /**
    * Enumを表す
    * TODO: enumeratumはmacro使ってるので剥がす
    * @tparam A EnumColumn継承
    */
  trait Enum[A <: EnumColumn] extends enumeratum.Enum[A] {

    override val values: IndexedSeq[A]

    def withId(id: Int): A =
      withIdOption(id).getOrElse(throw new NoSuchElementException(buildNotFoundMessage(id)))

    def withIdOption(id: Int): Option[A] = values.find(_.id == id)

    private def buildNotFoundMessage(id: Int): String = {
      s"$id is not a member of Enum ($existingEntriesString)"
    }

    private lazy val existingEntriesString =
      values.map(_.id).mkString(", ")

  }

  trait EnumWithDefault[A <: EnumColumn] extends Enum[A] {
    val defaultValue: A
  }

  /**
    * entryNameはカスタムする場合はname同様にoverrideして利用してください。
    * entryNameの用途はAPIで数字でやりとりをせずにentryNameでやる事を目的としています。
    * プログラム内部で使う事を想定していません。
    *
   * nameは数字を表示する際にわざわざ利用側で変換テーブルを持たなくていいように設計してる。
    * また日本語の意味がわかりやすいのもあり実装してる。
    * apiですべてのenumは公開する設計にする。利用側でcacheして使って貰えるといい。
    */
  trait EnumColumn extends enumeratum.EnumEntry {
    // DB側のEnumの数字用途、またはAPIで直接送られる
    val id: Int
    // 表示側で使う用途
    val name: String
  }

}
