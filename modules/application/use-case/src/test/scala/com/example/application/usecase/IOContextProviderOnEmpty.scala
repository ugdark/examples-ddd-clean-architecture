package com.example.application.usecase

import com.example.domain.IOContext

trait IOContextProviderOnEmpty extends IOContextProvider {
  override def write[T](f: IOContext => T): T = f(IOContext.Empty)

  override def read[T](f: IOContext => T): T = f(IOContext.Empty)
}
