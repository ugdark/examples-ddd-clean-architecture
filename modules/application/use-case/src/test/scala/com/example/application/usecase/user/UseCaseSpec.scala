package com.example.application.usecase.user

import com.example.domain.{IOContext, IOContextProvider}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class UseCaseSpec extends AnyFunSpec with Matchers {

  protected val ioContextProviderDummy: IOContextProvider = new IOContextProvider {
    override def get: IOContext                           = IOContext.Empty
    override def withTransaction[T](f: IOContext => T): T = f(IOContext.Empty)
  }

  describe("UserUseCaseTest") {
    it("should create") {}
  }
}
