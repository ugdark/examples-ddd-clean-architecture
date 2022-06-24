package com.example.domain
package user

import cats.implicits.{catsSyntaxTuple2Semigroupal, catsSyntaxValidatedId}

import scala.util.control.NonFatal

/**
  * Domainの制約を入力Errorとして取り扱うためのクラス
  */
object UserValidator {

  def valid(
      _id: Long,
      _name: String
  ): ValidationResult[User] = {
    (
      validId(_id),
      validName(_name)
    ).mapN {
      case (id, name) =>
        User(
          id = id,
          name = name
        )
    }
  }

  def validId(value: Long): ValidationResult[UserId] = {
    try {
      UserId(value).valid
    } catch {
      case NonFatal(ex) =>
        InvalidError(s"Invalid system user id [$value]", Some(ex)).invalidNel
    }
  }

  def validName(value: String): ValidationResult[UserName] = {
    try {
      UserName(value).valid
    } catch {
      case NonFatal(ex) =>
        InvalidError(s"Invalid system user name [$value]", Some(ex)).invalidNel
    }
  }

}
