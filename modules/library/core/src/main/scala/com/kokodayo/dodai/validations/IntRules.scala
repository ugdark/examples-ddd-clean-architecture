package com.kokodayo.dodai.validations

object IntRules {

  def lengthOpt(
    key: String,
    v: Option[Int],
    maxLength: Int = Int.MaxValue,
    minLength: Int = 0
  ): Either[ValidationField, Option[Int]] =
    if (v.isEmpty) Right(None)
    else if (v.get > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get < minLength)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v)

  def length(
    key: String,
    v: Option[Int],
    maxLength: Int = Int.MaxValue,
    minLength: Int = 0
  ): Either[ValidationField, Int] =
    if (v.isEmpty) Left(ValidationField(key, s"$key is not null"))
    else if (v.get > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get < minLength)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v.get)

  // maxLengthがないのはIntは0以下を想定する設計を考慮必要で常にminLengthの設定が必要です。

  def lengthOrDefault(
    v: Option[Int],
    default: Int,
    maxLength: Int = Int.MaxValue,
    minLength: Int = 0
  ): Int =
    if (v.isEmpty) default
    else if (v.get > maxLength)
      default
    else if (v.get < minLength)
      default
    else v.get

}
