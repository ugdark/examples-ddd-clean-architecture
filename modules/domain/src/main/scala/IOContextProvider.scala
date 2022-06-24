package com.example.domain

import scala.concurrent.ExecutionContext

trait IOContextProvider {

  def get(implicit ec: ExecutionContext): IOContext

  def withTransaction[T](f: IOContext => T)(implicit ec: ExecutionContext): T

}
