package com.kokodayo.dodai.validations

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import scala.util.control.NonFatal

object StringRules {

  // textなど許容がわからない物は安全の為に設定した
  private[this] val maxLength = 1000

  def lengthOpt(
    key: String,
    v: Option[String],
    maxLength: Int = maxLength,
    minLength: Int = 0
  ): Either[ValidationField, Option[String]] =
    if (v.isEmpty) Right(None)
    else if (v.get.isBlank) Left(ValidationField(key, s"$key is not empty"))
    else if (v.get.length > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get.length < minLength)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v)

  def length(
    key: String,
    v: Option[String],
    maxLength: Int = maxLength,
    minLength: Int = 0
  ): Either[ValidationField, String] =
    if (v.isEmpty) Left(ValidationField(key, s"$key is not null"))
    else if (v.get.isBlank) Left(ValidationField(key, s"$key is not empty"))
    else if (v.get.length > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get.length < minLength)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v.get)

  def lengthOrDefaultValue(
    key: String,
    v: Option[String],
    defaultValue: String = "",
    maxLength: Int = maxLength,
    minLength: Int = 0
  ): Either[ValidationField, String] =
    if (v.isEmpty) Right(defaultValue)
    else if (v.get.isBlank) Right(defaultValue)
    else if (v.get.length > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.get.length < minLength)
      Left(ValidationField(key, s"$key is min Length [max:$minLength]"))
    else Right(v.get)

  // emptyを許容する用
  def maxlength(
    key: String,
    v: Option[String],
    maxLength: Int = maxLength
  ): Either[ValidationField, String] =
    if (v.isEmpty) Right("")
    else if (v.get.length > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else Right(v.get)

  def maxlengthOpt(
    key: String,
    v: Option[String],
    maxLength: Int = maxLength
  ): Either[ValidationField, Option[String]] =
    if (v.isEmpty) Right(None)
    else if (v.get.length > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else Right(v)

  /** とりあえず日付
    */
  def datetimeOpt(key: String, v: Option[String]): Either[ValidationField, Option[ZonedDateTime]] =
    try
      Right(v.map(ZonedDateTime.parse(_)))
    catch {
      case NonFatal(_) => Left(ValidationField(key, s" $key is not format."))
    }

  /** 郵便番号チェック
    */
  def zipCode(key: String, v: Option[String]): Either[ValidationField, String] =
    if (v.isEmpty || Option(v.get).isEmpty) Left(ValidationField(key, s"$key is not null"))
    else if (v.get.isBlank) Left(ValidationField(key, s"$key is not empty"))
    else if (v.get.length != 7) Left(ValidationField(key, s"$key is not format [XXXXXXXX] 7桁です。"))
    else {
      try {
        v.get.toInt
        Right(v.get)
      } catch {
        case NonFatal(_) => Left(ValidationField(key, s"$key is not format [XXXXXXXX] 7桁です。"))
      }
    }

  /** 郵便番号チェック
    */
  def zipCodeOpt(key: String, v: Option[String]): Either[ValidationField, Option[String]] =
    if (v.isEmpty || Option(v.get).isEmpty) Right(None)
    else if (v.get.isBlank) Right(None)
    else if (v.get.length != 7) Left(ValidationField(key, s"$key is not format [XXXXXXXX] 7桁です。"))
    else {
      try {
        v.get.toInt
        Right(v)
      } catch {
        case NonFatal(_) => Left(ValidationField(key, s"$key is not format [XXXXXXXX] 7桁です。"))
      }
    }

  /** yyyyMMチェック
    */
  def yyyyMM(key: String, v: Option[String]): Either[ValidationField, String] =
    if (v.isEmpty) Left(ValidationField(key, s"$key is not null"))
    else if (v.get.isBlank) Left(ValidationField(key, s"$key is not empty"))
    else if (v.get.length != 6) Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
    else {
      try {
        val formatter = new SimpleDateFormat("yyyyMM")
        val result    = formatter.parse(v.get)
        formatter.setLenient(false)
        val reverse = formatter.format(result)
        if (v.get == reverse) {
          Right(v.get)
        } else {
          Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
        }
      } catch {
        case NonFatal(_) => Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
      }
    }

  /** yyyyMMチェック Emptyも許容しちゃう版
    */
  def yyyyMMOrEmpty(key: String, v: Option[String]): Either[ValidationField, String] =
    if (v.isEmpty) Left(ValidationField(key, s"$key is not null"))
    else if (v.get.isBlank) Right(v.get)
    else if (v.get.length != 6) Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
    else {
      try {
        val formatter = new SimpleDateFormat("yyyyMM")
        val result    = formatter.parse(v.get)
        formatter.setLenient(false)
        val reverse = formatter.format(result)
        if (v.get == reverse) {
          Right(v.get)
        } else {
          Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
        }
      } catch {
        case NonFatal(_) => Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
      }
    }

  /** yyyyMMチェック
    */
  def yyyyMMOpt(key: String, v: Option[String]): Either[ValidationField, Option[String]] =
    if (v.isEmpty) Right(None)
    else if (v.get.isBlank) Left(ValidationField(key, s"$key is not empty"))
    else if (v.get.length != 6) Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
    else {
      try {
        val formatter = new SimpleDateFormat("yyyyMM")
        val result    = formatter.parse(v.get)
        formatter.setLenient(false)
        val reverse = formatter.format(result)
        if (v.get == reverse) {
          Right(v)
        } else {
          Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
        }
      } catch {
        case NonFatal(_) => Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
      }
    }

  /** yyyyMMチェック (ちょっと特殊で月00を許可する) completion専用 monthが00なら築年数がyearに入る Emptyも許容しちゃう版
    */
  def yyyyM0OrEmpty(key: String, v: Option[String]): Either[ValidationField, String] =
    if (v.isEmpty) Left(ValidationField(key, s"$key is not null"))
    else if (v.get.isBlank) Right(v.get)
    else if (v.get.length != 6) Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
    else {
      try {
        Integer.parseInt(v.get)
        val (year, month) = v.get.splitAt(4) match {
          case (year, month) => (Integer.parseInt(year), Integer.parseInt(month))
        }
        def yearCheck(year: Int): Boolean   = year >= 0
        def monthCheck(month: Int): Boolean = month >= 0 && month <= 12

        if (yearCheck(year) && monthCheck(month)) {
          Right(v.get)
        } else {
          Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
        }
      } catch {
        case NonFatal(_) => Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
      }
    }

  /** yyyyMMチェック (ちょっと特殊で月00を許可する) completion専用 monthが00なら築年数がyearに入る
    */
  def yyyyM0Opt(key: String, v: Option[String]): Either[ValidationField, Option[String]] =
    if (v.isEmpty) Right(None)
    else if (v.get.isBlank) Left(ValidationField(key, s"$key is not empty"))
    else if (v.get.length != 6) Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
    else {
      try {
        Integer.parseInt(v.get)
        val (year, month) = v.get.splitAt(4) match {
          case (year, month) => (Integer.parseInt(year), Integer.parseInt(month))
        }
        def yearCheck(year: Int): Boolean   = year >= 0
        def monthCheck(month: Int): Boolean = month >= 0 && month <= 12

        if (yearCheck(year) && monthCheck(month)) {
          Right(v)
        } else {
          Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
        }
      } catch {
        case NonFatal(_) => Left(ValidationField(key, s"$key is not format [yyyyMM] 6桁です。"))
      }
    }

  def phone(key: String, v: String, maxLength: Int = maxLength): Either[ValidationField, String] = {
    val pattern = """^\d[\-\d]+\d$"""
    if (v.isEmpty) Right(v)
    else if (v.length > maxLength)
      Left(ValidationField(key, s"$key is max Length [max:$maxLength]"))
    else if (v.matches(pattern) == false)
      Left(ValidationField(key, s"$key is not format of phone numbers"))
    else Right(v)
  }
}
