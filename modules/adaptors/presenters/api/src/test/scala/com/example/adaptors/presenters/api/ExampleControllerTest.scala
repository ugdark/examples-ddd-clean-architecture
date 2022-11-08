package com.example.adaptors.presenters.api

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Route

class ExampleControllerTest extends ControllerUnitSpec {

  private case class SampleResponse(
    name: String,
    message: String
  )

  private case class SampleRequest(
    name: String
  )

  private case class SampleController() {
    import akka.http.scaladsl.model.StatusCodes
    import akka.http.scaladsl.server.Directives.*
    import akka.http.scaladsl.server.Route

    val routes: Route =
      pathPrefix("health") {
        concat(
          get {
            complete(StatusCodes.OK -> SampleResponse("tester", "hello"))
          },
          post {
            entity(as[SampleRequest]) { json =>
              complete(StatusCodes.Created -> SampleResponse(json.name, "hello"))
            }
          }
        )
      }

  }

  private val sampleController = SampleController()

  describe("ControllerTestのdemo") {

    it("get sample") { implicit test =>
      Get("/health") ~> sampleController.routes ~> check {
        val response = responseAs[SampleResponse]
        response.asJson.spaces2 should matchSnapshotNoEscape()
      }
    }

    it("post sample") { _ =>
      val req =
        HttpEntity(ContentTypes.`application/json`, SampleRequest("create").asJson.noSpaces)
      Post("/health", req) ~> sampleController.routes ~> check {
        status shouldBe StatusCodes.Created
      }
    }

    it("error sample") { _ =>
      val req = HttpEntity(ContentTypes.`application/json`, "")
      Post("/health", req) ~> Route.seal(sampleController.routes) ~> check {
        status shouldBe StatusCodes.BadRequest
      }
    }

  }

}
