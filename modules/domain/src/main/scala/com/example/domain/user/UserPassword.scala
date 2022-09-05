package com.example.domain.user

import com.example.domain.Value

case class UserPassword(value: String) extends AnyVal with Value[String]

case class UserRowPassword(value: String) extends AnyVal with Value[String] {

  def generateHash: UserPassword = {
    import java.security.MessageDigest
    val digest = MessageDigest.getInstance("SHA-256")
    val result = digest.digest(value.getBytes)
    import java.math.BigInteger
    val hash = String.format("%040x", new BigInteger(1, result))
    UserPassword(hash)
  }
}

object UserRowPassword extends (String => UserRowPassword) with UserRawPasswordValidator {

  def apply(value: String): UserRowPassword = {
    require(value != null && value.nonEmpty)
    require(value.length >= MinLength && value.length <= MaxLength)
    new UserRowPassword(value)
  }

  protected[user] val MinLength = 6
  protected[user] val MaxLength = 20
}
