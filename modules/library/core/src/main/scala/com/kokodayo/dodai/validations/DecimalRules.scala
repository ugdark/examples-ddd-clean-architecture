package com.kokodayo.dodai.validations

object DecimalRules {

  /** 参考したサイト https://www.gsi.go.jp/KOKUJYOHO/center.htm
    */
  def latitudeOpt(key: String, v: Option[BigDecimal]): Either[ValidationField, Option[BigDecimal]] =
    lengthOpt(key, v, BigDecimal(45.3326), BigDecimal(20.2531))

  /** 参考したサイト https://www.gsi.go.jp/KOKUJYOHO/center.htm
    */
  def longitudeOpt(
    key: String,
    v: Option[BigDecimal]
  ): Either[ValidationField, Option[BigDecimal]] =
    lengthOpt(key, v, BigDecimal(153.5912), BigDecimal(122.5557))

  def length(
    key: String,
    v: Option[BigDecimal],
    maxLength: BigDecimal = Int.MaxValue,
    minLength: BigDecimal = 0
  ): Either[ValidationField, BigDecimal] =
    if (v.isEmpty) Left(ValidationField(key, s"$key is not null"))
    else if (v.get.compareTo(maxLength) > 0)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get.compareTo(minLength) < 0)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v.get)

  def lengthOpt(
    key: String,
    v: Option[BigDecimal],
    maxLength: BigDecimal = Int.MaxValue,
    minLength: BigDecimal = 0
  ): Either[ValidationField, Option[BigDecimal]] =
    if (v.isEmpty) Right(None)
    else if (v.get.compareTo(maxLength) > 0)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get.compareTo(minLength) < 0)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v)
}
