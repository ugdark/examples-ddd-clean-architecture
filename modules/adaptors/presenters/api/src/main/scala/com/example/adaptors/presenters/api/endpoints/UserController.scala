package com.example.adaptors.presenters.api.endpoints

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{
  as,
  complete,
  entity,
  onSuccess,
  pathEnd,
  pathPrefix,
  post
}
import akka.http.scaladsl.server.Route
import com.example.application.usecase.user.UserUseCase
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.AutoDerivation

import scala.concurrent.Future

trait UserController extends FailFastCirceSupport with AutoDerivation {

  val routes: Route =
//    handleExceptions(ExceptionHandlers.default) {
//      handleRejections(RejectionHandlers.default) {
    pathPrefix("v1") {
      pathPrefix("users") {
        create
      }
    }
//      }
//    }

  protected val userUseCase: UserUseCase

  case class CreateRequest(name: String, password: String) {
    def toCommand: UserUseCase.Request = UserUseCase.Request(name, password)
  }

  case class CreateResponse(id: String, name: String, password: String)

  object CreateResponse {
    def fromReply(reply: UserUseCase.Response): CreateResponse =
      CreateResponse(reply.user.id.value, reply.user.name.value, reply.user.password.value)
  }

  def create: Route =
    pathEnd {
      post {
        entity(as[CreateRequest]) { request =>
          onSuccess(Future.successful(userUseCase.create(request.toCommand))) {
            case Left(e) => throw new RuntimeException(e.toString)
            case Right(response) =>
              complete(StatusCodes.Created -> CreateResponse.fromReply(response))
          }
        }
      }
    }

}
