package com.example.adaptors.controllers.apiServier

import akka.actor.typed.ActorSystem
import com.example.adaptors.gateways.db.{
  IDGenerators,
  IOContextProviderOnDb,
  UserRepositoryOnSkinny
}
import com.example.adaptors.presenters.api.endpoints.UserController
import com.example.application.usecase.user.UserUseCase
import com.example.domain.user.{UserEvent, UserEventPublisher, UserRepository}
import com.example.domain.{EntityIdGenerator, EntityMetaData, EntityMetaDataCreator, IOContext}
import com.typesafe.config.{Config, ConfigFactory}

import scala.util.{Success, Try}

trait InjectModules {

  implicit protected val system: ActorSystem[Nothing]

  protected lazy val config: Config = ConfigFactory.load()

  protected lazy val userCreateUseCase: UserUseCase = new UserUseCase with IOContextProviderOnDb {
    implicit override protected val entityIdGenerator: EntityIdGenerator = IDGenerators.User
    implicit override protected val entityMetaDataCreator: EntityMetaDataCreator =
      new EntityMetaDataCreator {
        override def create: EntityMetaData = EntityMetaData.Empty
      }
    implicit override protected val userRepository: UserRepository = UserRepositoryOnSkinny
    override protected val userEventPublisher: UserEventPublisher = new UserEventPublisher {
      override def publish[EVENT <: UserEvent](event: EVENT)(implicit ioc: IOContext): Try[EVENT] =
        Success(event)
    }
  }

  protected lazy val userController: UserController = new UserController {
    override protected val userUseCase: UserUseCase = userCreateUseCase
  }

}
