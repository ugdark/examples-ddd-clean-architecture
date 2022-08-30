package com.example.domain.user

import com.example.domain.Value

case class UserName(value: String) extends Value[String] {
  require(value != null && value.length <= 200 && value.nonEmpty)
}
