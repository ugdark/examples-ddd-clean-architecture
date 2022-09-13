package com.example.adaptors.gateways.db

final case class NotFoundException(message: String, cause: Throwable = null)
    extends Throwable(message, cause)
