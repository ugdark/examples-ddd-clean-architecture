package com.example.domain

trait IOContextProvider {

  def get: IOContext

  def withTransaction[T](f: IOContext => T): T

}
