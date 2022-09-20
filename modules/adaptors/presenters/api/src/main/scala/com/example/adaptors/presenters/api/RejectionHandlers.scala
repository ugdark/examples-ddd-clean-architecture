package com.example.adaptors.presenters.api

import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport.*
import io.circe.generic.auto.*

//object RejectionHandlers {
//
//  final val default: RejectionHandler = RejectionHandler
//    .newBuilder()
//    .handle {
//      case NotFoundRejection(msg, _) =>
//        complete((StatusCodes.NotFound, ErrorsResponseJson(Seq(msg))))
//      case ValidationsRejection(errors) =>
//        complete((StatusCodes.BadRequest, http.response.ErrorsResponseJson(errors.map(_.message).toList)))
//    }
//    .result()
//
//}
