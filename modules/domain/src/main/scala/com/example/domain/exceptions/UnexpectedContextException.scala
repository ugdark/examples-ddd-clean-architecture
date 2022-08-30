package com.example.domain.exceptions

// IOContextの設定例外用
final case class UnexpectedContextException(message: String, cause: Throwable = null)
    extends Throwable(message, cause)
