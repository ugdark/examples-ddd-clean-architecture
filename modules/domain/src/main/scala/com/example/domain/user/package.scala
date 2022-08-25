package com.example.domain

/**
  * 迷ったんだけど、AnyValを使いたいがrequireが使えないので使用しない
  */
package object user {

  case class UserId(value: String) extends EntityId {
    require(value != null && value.length <= 20 && value.nonEmpty)
  }

  object UserId {
    def newId(implicit idGen: EntityIdGenerator): UserId = UserId(idGen.generate())
  }

  case class UserName(value: String) extends Value[String] {
    require(value != null && value.length <= 200 && value.nonEmpty)
  }

}
