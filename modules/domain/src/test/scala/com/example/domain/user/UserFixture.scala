package com.example.domain.user

import com.example.domain.{DomainTestSupport, EntityMetaDataCreator}

object UserFixture extends DomainTestSupport {

  def generate(
    id: String = entityIdGeneratorFixture.generate,
    name: String =
      new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(10).mkString,
    password: String =
      new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(8).mkString
  )(implicit metaDataCreator: EntityMetaDataCreator = entityMetaDataCreatorFixture): User =
    User(
      UserId(id),
      UserName(name),
      UserRowPassword(password).generateHash,
      metaDataCreator.create
    )
}
