package com.example.application.usecase.user

import com.example.application.usecase.{
  DomainErrorOps,
  InfraErrorOps,
  UseCaseError,
  UseCaseSystemError,
  asUseCaseError
}
import com.example.domain.*
import com.example.domain.user.*

object UserUseCase {
  case class Request(name: String, password: String)

  case class Response(user: User)
}

trait UserUseCase {

  implicit protected val ioContextProvider: IOContextProvider

  implicit protected val entityIdGenerator: EntityIdGenerator

  implicit protected val entityMetaDataCreator: EntityMetaDataCreator

  // 本当はimplicit外したい
  implicit protected val userRepository: UserRepository
  protected val userEventPublisher: UserEventPublisher

  def create(request: UserUseCase.Request): Either[UseCaseError, UserUseCase.Response] =
    ioContextProvider.withTransaction { implicit ioc =>
      for {
        createdUser <- User.create(request.name, request.password) ifLeftThen asUseCaseError
        _           <- userRepository.store(createdUser.entity) ifFailureThen asUseCaseError
        _           <- userEventPublisher.publish(createdUser.event) ifFailureThen asUseCaseError
      } yield UserUseCase.Response(createdUser.entity)

    }

  implicit val infraErrorHandler: Throwable => UseCaseError = {
//    case e: OptimisticLockException => ConflictedError(e.id)
    e => UseCaseSystemError(e)
  }

}
