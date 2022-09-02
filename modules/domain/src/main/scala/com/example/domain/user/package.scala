package com.example.domain

import cats.data.ValidatedNec

package object user {

  // 入力例外を表す
  type ValidationResult[A] = ValidatedNec[UserInvalidError, A]

}
