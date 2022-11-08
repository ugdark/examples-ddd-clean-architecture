package com.example.adaptors.controllers.apiServier

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentType, HttpEntity, MediaTypes, StatusCodes}
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import org.apache.poi.ss.usermodel.{Workbook, WorkbookFactory}

import java.io.ByteArrayOutputStream
import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.*
import scala.util.{Failure, Success, Try, Using}

object Main extends InjectModules {

  implicit override protected val system: ActorSystem[Nothing] =
    ActorSystem.create(Behaviors.empty, "api-server")

  private def startHttpServer(routes: Route)(implicit system: ActorSystem[?]): Unit = {
    implicit val ec: ExecutionContextExecutor = system.executionContext
    val futureBinding = Http()
      .newServerAt(
        config.getString("http.host"),
        config.getInt("http.port")
      )
      .bind(routes)
      .map(_.addToCoordinatedShutdown(hardTerminationDeadline = 10.seconds))

    futureBinding
      .onComplete {
        case Success(binding) =>
          val address = binding.localAddress
          system.log.info(
            "Server online at http://{}:{}/ \n",
            address.getHostString,
            address.getPort
          )
        case Failure(ex) =>
          system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
          system.terminate()
      }
  }

  def main(args: Array[String]): Unit = {

    val health: Route = pathPrefix("health") {
      complete(StatusCodes.OK -> "Success!!")
    }

    val check: Route = pathPrefix("check") {

      def writeToByteArray(workbook: Workbook): Try[Array[Byte]] =
        Using(new ByteArrayOutputStream()) { bos =>
          workbook.write(bos)
          bos.toByteArray
        }

      val workbook: Workbook =
        WorkbookFactory.create(getClass.getResourceAsStream("/excels/students/password.xlsx"))
      val arrayByte: Array[Byte] = writeToByteArray(workbook).get
      complete(
        HttpEntity(
          ContentType(
            MediaTypes.`application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`
          ),
          arrayByte
        )
      )
    }

    val rootRoutes: Route =
      concat(
        health,
        check,
        userController.routes
      )

    startHttpServer(rootRoutes)(system)
  }

}
