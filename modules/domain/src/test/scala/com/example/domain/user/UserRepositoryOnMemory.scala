package com.example.domain.user

import com.example.domain.{IOContext, TestRepositoryOnMemoryBase}

// UserRepositoryのテスト用
case class UserRepositoryOnMemory()
    extends UserRepository
    with TestRepositoryOnMemoryBase[UserId, User] {

  override def verifyForDuplicateNames(name: UserName)(implicit
    ioc: IOContext = IOContext.Empty
  ): Boolean = false
}

object UserRepositoryOnMemory extends UserRepositoryOnMemory
