package com.example.domain.user

import com.example.domain.{EntityMetaDataCreator, TestSupport}

object UserFixture extends TestSupport {

  def generate(
    id: String = idGenerator.generate,
    name: String =
      new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(10).mkString,
    password: String =
      new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(8).mkString
  )(implicit metaDataCreator: EntityMetaDataCreator = metaDataCreator): User =
    User(
      UserId(id),
      UserName(name),
      UserRowPassword(password).generateHash,
      metaDataCreator.create
    )
}
