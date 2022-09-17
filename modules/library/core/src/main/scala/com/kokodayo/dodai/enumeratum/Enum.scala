package com.kokodayo.dodai.enumeratum

import scala.collection.Seq

trait Enum[A <: EnumColumn] {

  /** 初期値はvaluesのheadになるので変更が必要なら {{{override lazy val defaultValue: A = Enum}}} してください
    * 全域関数になっていないことに注意
    */
  lazy val defaultValue: A = values.head
  val values: Seq[A]

  def of(id: Int): A =
    this.find(id).getOrElse {
      throw new IllegalArgumentException(s"`$id` is not $getSimpleName value.")
    }
  def of(keyword: String): A =
    this.find(keyword).getOrElse {
      throw new IllegalArgumentException(s"`$keyword` is not $getSimpleName text.")
    }

  private def getSimpleName: String = getClass.getSimpleName.replace("$", "")

  def find(keyword: String): Option[A] =
    values.find(_.keyword.toLowerCase == keyword.toLowerCase)

  def orDefault[T](keyOpt: Option[T]): A =
    keyOpt match {
      case Some(value: String) => orDefault(value)
      case Some(value: Int)    => orDefault(value)
      case _                   => defaultValue
    }

  def orDefault(id: Int): A = find(id).getOrElse(defaultValue)

  def find(id: Int): Option[A] = values.find(_.id == id)

  def orDefault(keyword: String): A = find(keyword).getOrElse(defaultValue)

}
