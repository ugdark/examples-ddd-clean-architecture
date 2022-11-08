package com.example.adaptors.presenters

import io.circe.{Encoder, Json, JsonObject, KeyEncoder}

package object api {

  /* import io.circe.syntax.* と同義 */
  implicit final class EncoderOps[A](private val value: A) extends AnyVal {
    def asJson(implicit encoder: Encoder[A]): Json = encoder(value)
    def asJsonObject(implicit encoder: Encoder.AsObject[A]): JsonObject =
      encoder.encodeObject(value)
  }
  implicit final class KeyOps[K](private val value: K) extends AnyVal {
    def :=[A: Encoder](a: A)(implicit keyEncoder: KeyEncoder[K]): (String, Json) =
      (keyEncoder(value), a.asJson)
  }

}
