package com.example.application.usecase.user

import com.example.application.usecase.UseCaseSpec
import com.example.domain.user.{
  UserEvent,
  UserEventPublisher,
  UserRepository,
  UserRepositorySupport
}
import com.example.domain.{EntityIdGenerator, EntityMetaDataCreator, IOContext, IOContextProvider}

import scala.util.{Success, Try}

class UserUseCaseTest extends UseCaseSpec with UserRepositorySupport {

  protected val userUseCaseFixture: UserUseCase = new UserUseCase {
    override protected val ioContextProvider: IOContextProvider          = ioContextProviderFixture
    implicit override protected val entityIdGenerator: EntityIdGenerator = entityIdGeneratorFixture
    implicit override protected val entityMetaDataCreator: EntityMetaDataCreator =
      entityMetaDataCreatorFixture
    implicit override protected val userRepository: UserRepository = userRepositoryFixture
    override val userEventPublisher: UserEventPublisher = new UserEventPublisher {
      override def publish[EVENT <: UserEvent](
        event: EVENT
      )(implicit ioc: IOContext): Try[EVENT] =
        // TODO: Loggingで別のファイルに出力とかとりあえずしたい
        Success(event)
    }
  }

  describe("UserUseCase") {
    it("正常系確認") {
      val request = UserUseCase.Request("test taro", "PASSword123")
      val result  = userUseCaseFixture.create(request = request)
      result.isRight shouldBe true

    }
  }
}
