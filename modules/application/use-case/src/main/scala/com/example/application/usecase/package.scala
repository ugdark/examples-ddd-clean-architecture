package com.example.application

import com.example.domain.{DomainError, ValidatedError}

import scala.util.{Failure, Success, Try}

package object usecase {

  type ErrorCode = String

  implicit class DomainErrorOps[E <: DomainError, R](domainResult: Either[E, R]) {
    def ifLeftThen(f: E => UseCaseError): Either[UseCaseError, R] =
      domainResult match {
        case Left(e)  => Left(f(e))
        case Right(r) => Right(r)
      }
  }

  implicit class InfraErrorOps[S](infraResult: Try[S]) {
    def ifFailureThen(f: Throwable => UseCaseError): Either[UseCaseError, S] =
      infraResult match {
        case Failure(e) => Left(f(e))
        case Success(s) => Right(s)
      }
  }

  def asUseCaseError[E](implicit f: E => UseCaseError): E => UseCaseError = f

  implicit val validatedErrorHandler: ValidatedError => UseCaseError = e =>
    ValidationError(e.invalids)

}
