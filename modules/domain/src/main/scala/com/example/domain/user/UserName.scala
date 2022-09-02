package com.example.domain.user

import com.example.domain.Value

case class UserName(value: String) extends AnyVal with Value[String]

object UserName extends (String => UserName) {

  def apply(value: String): UserName = {
    require(value != null && value.length <= UserName.MaxLength && value.nonEmpty)
    new UserName(value)
  }
  val MaxLength = 20
}
