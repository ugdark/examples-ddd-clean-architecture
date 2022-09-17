package com.example.domain.user

import org.scalatest.{BeforeAndAfterEach, Suite}

/** Domain層で定義してるimplicitのEmptyを提供する
  */
trait UserRepositorySupport extends BeforeAndAfterEach { this: Suite =>

  implicit protected val userRepositoryFixture: UserRepositoryOnMemory = UserRepositoryOnMemory

  override def beforeEach(): Unit = {
    userRepositoryFixture.clear()
    super.beforeEach()
  }

  override def afterEach(): Unit =
    try super.afterEach()
    finally
      userRepositoryFixture.clear()
}
