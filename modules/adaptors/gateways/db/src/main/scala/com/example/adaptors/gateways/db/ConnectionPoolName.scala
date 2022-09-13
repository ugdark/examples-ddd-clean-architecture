package com.example.adaptors.gateways.db

sealed trait ConnectionPoolName {
  val name: String
}

protected[db] object ConnectionPoolName {
  final case object Read extends ConnectionPoolName {
    override val name: String = "examples_read"
  }
  final case object Write extends ConnectionPoolName {
    override val name: String = "examples_write"
  }
}
