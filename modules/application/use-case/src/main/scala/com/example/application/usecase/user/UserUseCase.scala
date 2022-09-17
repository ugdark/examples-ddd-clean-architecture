//package com.example.application.usecase.user
//
//import com.example.application.usecase.UseCaseError
//import com.example.domain.user._
//import com.example.domain._
//
//import scala.language.postfixOps
//
//trait UserUseCase {
//
//  implicit protected val ioContextProvider: IOContextProvider
//
//  implicit protected val idGenerator: EntityIdGenerator
//
//  implicit protected val entityMetaDataCreator: EntityMetaDataCreator
//
//  implicit protected val userRepository: UserRepository
//
//  //    validator: UserValidator,
////    repositoryValidator: UserRepositoryValidator,
////    ioc: IOContext
//
//  //  protected object IdGeneratorFixture extends EntityIdGenerator {
////
////    private val atomicLong = new AtomicLong(1L)
////
////    override def generate: String = atomicLong.getAndIncrement().toString
////  }
//
//  //
//  //  implicit protected val idGenerator: EntityIdGenerator = EntityIdGe
////  metaDataCreator: EntityMetaDataCreator,
////  validator: UserValidator,
////  repositoryValidator: UserRepositoryValidator,
////  ioc: IOContext
//
//  case class Request(name: String, password: String)
//
//  case class Response(user: User)
//
//  def create(request: Request): Either[UseCaseError, Response] =
//    ioContextProvider.withTransaction { implicit ioc =>
//      val b: Either[UseCaseError, DomainResult[UserEvent, User]] =
//        User.create(request.name, request.password) ifLeftThen
//
////      for {
////        domainResult <- User.create(request.name, request.password) ifLeftThen asServiceError
////      } yield Response(domainResult.entity)
//
//      Right(
//        Response(
//          User(
//            UserId("1"),
//            UserName("a"),
//            UserRowPassword("a").generateHash,
//            entityMetaDataCreator.create
//          )
//        )
//      )
//    }
//
//  implicit class DomainErrorOps[E <: DomainError, R](domainResult: Either[E, R]) {
//    def ifLeftThen(f: E => UseCaseError): Either[UseCaseError, R] =
//      domainResult match {
//        case Left(e)  => Left(f(e))
//        case Right(r) => Right(r)
//      }
//  }
//
//  implicit val infraErrorHandler: Throwable => UseCaseError
//
//  def asServiceError[E](implicit f: E => UseCaseError): E => UseCaseError = f
//
//}
