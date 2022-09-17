package com.example.domain.user

import com.example.domain.IOContext

import scala.util.Try

trait UserEventPublisher {

  def publish[EVENT <: UserEvent](event: EVENT)(implicit ioc: IOContext): Try[EVENT]

}
