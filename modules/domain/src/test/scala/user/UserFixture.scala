package com.example.domain
package user

object UserFixture {

  def create(
      id: Long = DummyIdGenerators.User.generate.value,
      name: String = new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(10).mkString
  ): User = {
    User(UserId(id), UserName(name))
  }
}
