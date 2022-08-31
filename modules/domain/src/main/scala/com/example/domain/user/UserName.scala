package com.example.domain.user

import com.example.domain.Value

case class UserName(value: String) extends Value[String] {
  require(value != null && value.length <= UserName.MaxLength && value.nonEmpty)
}

object UserName extends (String => UserName) {
  val MaxLength = 20
}
