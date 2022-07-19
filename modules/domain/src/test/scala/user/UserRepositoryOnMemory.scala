package com.example.domain
package user

// UserRepositoryのテスト用
class UserRepositoryOnMemory() extends UserRepository with TestRepositoryOnMemoryBase[UserId, User]
