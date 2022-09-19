package com.example.application.usecase

import com.example.domain.IOContext

trait IOContextProvider {

  def write[T](f: IOContext => T): T

  def read[T](f: IOContext => T): T

}
