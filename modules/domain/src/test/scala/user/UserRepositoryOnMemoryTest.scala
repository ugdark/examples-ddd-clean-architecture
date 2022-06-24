package com.example.domain
package user

import cats.data.EitherT
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserRepositoryOnMemoryTest extends AnyFunSpec with ScalaFutures with Matchers {

  private final def fromEitherTF[L, R](either: Either[L, R]): EitherT[Future, L, R] =
    EitherT.fromEither(either)

  describe("UserRepositoryの") {

    // EitherT[Future]はいらないけど、UseCase層を想定してくるんでみてます。
    // 他のRepositoryTestでは不要
    it("ライフサイクルの確認") {
      val repository = new UserRepositoryOnMemory()
      for {
        _          <- fromEitherTF(repository.clear())
        create     <- fromEitherTF(repository.store(UserFixture.create()))
        findCreate <- fromEitherTF(repository.findById(create.id))
        update     <- fromEitherTF(repository.store(create.copy(name = UserName("hoge"))))
        findUpdate <- fromEitherTF(repository.findById(create.id))
        delete     <- fromEitherTF(repository.deleteById(create.id))
        findDelete <- fromEitherTF(repository.findById(create.id))
      } yield {
        findCreate shouldBe Some(create)
        findUpdate shouldBe Some(update.copy(updatedAt = findUpdate.get.updatedAt))
        delete shouldBe true
        findDelete shouldBe None
      }

    }
  }

}
