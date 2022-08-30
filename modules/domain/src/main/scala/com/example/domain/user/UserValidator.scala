package com.example.domain.user

import cats.implicits.{catsSyntaxTuple2Semigroupal, catsSyntaxValidatedId}
import com.example.domain.{EntityMetaData, EntityMetaDataCreator, InvalidError, ValidationResult}

import scala.util.control.NonFatal

/** Domainの制約を入力Errorとして取り扱うためのクラス catsのValid以上に簡易な作りがちょっとできなかったので今はcatsに依存させる
  */
object UserValidator {

  def valid(_id: String, _name: String)(implicit
    metaDataCreator: EntityMetaDataCreator = new EntityMetaDataCreator {
      override def create: EntityMetaData = new EntityMetaData {}
    }
  ): ValidationResult[User] =
    (validId(_id), validName(_name)).mapN { case (id, name) =>
      User(id = id, name = name, metaData = metaDataCreator.create)
    }

  def validId(value: String): ValidationResult[UserId] =
    try
      UserId(value).valid
    catch {
      case NonFatal(ex) =>
        InvalidError(s"Invalid system user id [$value]", Some(ex)).invalidNel
    }

  def validName(value: String): ValidationResult[UserName] =
    try {
      val maxLength = 20
      require(value.nonEmpty && value.length <= maxLength)
      UserName(value).valid
    } catch {
      case NonFatal(ex) =>
        InvalidError(s"Invalid system user name [$value]", Some(ex)).invalidNel
    }

}
