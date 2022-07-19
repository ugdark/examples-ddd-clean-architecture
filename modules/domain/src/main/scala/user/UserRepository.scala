package com.example.domain
package user

import scala.util.Try

trait UserRepository extends RepositoryBase[Try, UserId, User]
