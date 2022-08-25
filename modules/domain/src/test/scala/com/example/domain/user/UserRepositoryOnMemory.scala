package com.example.domain.user

import com.example.domain.TestRepositoryOnMemoryBase

// UserRepositoryのテスト用
class UserRepositoryOnMemory() extends UserRepository with TestRepositoryOnMemoryBase[UserId, User]
