package com.kokodayo.dodai.validations

object LongRules {

  /** Tableではint(11) NOT NULL AUTO_INCREMENTの設定が多く これはInt.MaxValueと同値(2147483647)になります。
    */
  private[this] val maxLength: Long = Int.MaxValue.toLong

  def lengthOpt(
    key: String,
    v: Option[Long],
    maxLength: Long = maxLength,
    minLength: Long = 0
  ): Either[ValidationField, Option[Long]] =
    if (v.isEmpty) Right(None)
    else if (v.get > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get < minLength)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v)

  def length(
    key: String,
    v: Option[Long],
    maxLength: Long = maxLength,
    minLength: Long = 0
  ): Either[ValidationField, Long] =
    if (v.isEmpty) Left(ValidationField(key, s"$key is not null"))
    else if (v.get > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get < minLength)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v.get)
}
