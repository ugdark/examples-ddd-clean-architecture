package com.example.adaptors.dbs

sealed abstract class ConnectionPoolName(val name: String)

protected[dbs] object ConnectionPoolNames extends {
  final case object Read  extends ConnectionPoolName("examples_read")
  final case object Write extends ConnectionPoolName("examples_write")
}
