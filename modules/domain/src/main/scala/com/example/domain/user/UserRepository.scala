package com.example.domain.user

import com.example.domain.RepositoryBase

import scala.util.Try

trait UserRepository extends RepositoryBase[Try, UserId, User]
