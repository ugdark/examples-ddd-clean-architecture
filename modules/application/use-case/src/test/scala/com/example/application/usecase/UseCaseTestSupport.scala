package com.example.application.usecase

import com.example.domain.{DomainTestSupport, IOContext, IOContextProvider}

/** UseCase層で定義してるimplicitのEmptyを提供する
  */
trait UseCaseTestSupport extends DomainTestSupport {

  protected val ioContextProviderFixture: IOContextProvider = new IOContextProvider {
    override def get: IOContext                           = IOContext.Empty
    override def withTransaction[T](f: IOContext => T): T = f(IOContext.Empty)
  }
}
