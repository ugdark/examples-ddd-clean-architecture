package com.kokodayo.dodai.validations

import scala.util.control.NonFatal

object CustomRules {

  def or(key: String, v1: Option[Any], v2: Option[Any]): Either[ValidationField, Boolean] =
    if (v1.isEmpty && v2.isEmpty) Left(ValidationField(key, s"$key is どっちか必須"))
    else Right(true)

  def deprecated(key: String, v: Option[_]): Either[ValidationField, Boolean] =
    Either.cond(v.isEmpty, true, ValidationField(key, s"$key is deprecated"))

  // 重複確認
  def distinct(key: String, v: Seq[_]): Either[ValidationField, Boolean] =
    Either.cond(v.size == v.distinct.size, true, ValidationField(key, s"$key is distinct"))

  /** ids等を配列で渡すなど用 + 必須入力
    */
  def idsRequired(key: String, v: Option[String]): Either[ValidationField, Seq[Long]] =
    try
      v match {
        case Some(value) => Right(value.split(",").toIndexedSeq.map(_.toLong))
        case None        => Left(ValidationField(key, s" $key is a required ."))
      }
    catch {
      case NonFatal(_) =>
        Left(
          ValidationField(
            key,
            s" $key is a required input and can be passed by splitting it with ,."
          )
        )
    }

  def booleanOrDefault(v: Option[String], default: Boolean = false): Boolean =
    try
      v.get.toBoolean
    catch {
      case NonFatal(_) => default
    }

}
