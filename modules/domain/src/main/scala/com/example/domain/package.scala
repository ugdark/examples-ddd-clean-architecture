package com.example

import cats.data.ValidatedNec

package object domain {

  // 入力例外を表す
  type ValidationResult[A] = ValidatedNec[InvalidError, A]
}
