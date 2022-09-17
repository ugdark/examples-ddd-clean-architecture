package com.example.domain.user

import com.example.domain.*
import com.kokodayo.dodai.test.UnitTest

class UserTest extends UnitTest {

  describe("ユーザーを") {

    it("作成する") {
      val user  = UserFixture.generate()
      val user2 = UserFixture.generate()
      user should not be user2
    }

    it("検証作成する") {
      implicit val idGenerator: EntityIdGenerator = new EntityIdGenerator() {
        override def generate: String = "123"
      }
      implicit val metaDataCreator: EntityMetaDataCreator = new EntityMetaDataCreator() {
        override def create: EntityMetaData = EntityMetaData.Empty
      }

      implicit val userRepositoryValidator: UserRepositoryValidator = UserRepositoryOnMemory
      implicit val ioc: IOContext                                   = IOContext.Empty

      val user = User.create(
        name = "test",
        password = "password"
      )
      user shouldBe Right(
        DomainResult(
          UserCreated(
            userId = UserId("123"),
            userName = UserName("test"),
            userPassword = UserRowPassword("password").generateHash,
            metaData = EntityMetaData.Empty
          ),
          User(
            id = UserId("123"),
            name = UserName("test"),
            password = UserRowPassword("password").generateHash,
            metaData = EntityMetaData.Empty
          )
        )
      )
    }

  }
}
