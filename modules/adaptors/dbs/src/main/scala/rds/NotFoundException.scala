package com.example.adaptors.dbs
package rds

final case class NotFoundException(message: String, cause: Throwable = null) extends Throwable(message, cause)
