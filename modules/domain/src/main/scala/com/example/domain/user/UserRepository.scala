package com.example.domain.user

import com.example.domain.RepositoryBase

import scala.util.Try

trait UserRepository extends RepositoryBase[Try, UserId, User] with UserRepositoryValidator

/** repository検証用 domain層でしかvalidationを使用しない
  */
protected[user] trait UserRepositoryValidator {

  /** 名前が存在するか
    * @param name
    *   UserName
    * @return
    */
  def verifyForDuplicateNames(name: UserName): Boolean
}
