package com.example

import cats.data.ValidatedNel

package object domain {

  // 入力例外を表す
  type ValidationResult[A] = ValidatedNel[InvalidError, A]
}
