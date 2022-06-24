package com.example.domain
package user

import java.time.Instant

class UserRepositoryOnMemory() extends UserRepository {

  private val storage: scala.collection.mutable.LinkedHashMap[UserId, User] =
    scala.collection.mutable.LinkedHashMap.empty

  /**
    * 検索する
    *
   * @param paging Paging Paging
    * @param ioc    IOContext
    * @return Right(Entity) 成功
    */
  override def findAll(paging: Paging)(implicit ioc: IOContext): Either[RepositoryError, Users] =
    Right(Users(storage.values.slice(paging.from, paging.to).toSeq))

  /**
    * Entityを保存する
    *
   * @param entity Entity
    * @param ioc    IOContext
    * @return Right(Entity) 成功
    */
  override def store(entity: User)(implicit ioc: IOContext): Either[RepositoryError, User] = {
    val updated = entity.copy(createdAt = Some(CreatedAt(Instant.now())), updatedAt = Option(UpdatedAt(Instant.now())))
    storage.update(updated.id, updated)
    Right(updated)
  }

  /**
    * id指定のEntityを返す
    *
   * @param id  識別子
    * @param ioc IOContext
    * @return Right(Some(Entity)) 成功
    *         Right(None) 存在しない
    */
  override def findById(id: UserId)(implicit ioc: IOContext): Either[RepositoryError, Option[User]] =
    Right(storage.get(id))

  /**
    * id指定の物のEntityを削除する。
    *
   * @param id  識別子
    * @param ioc IOContext
    * @return Right(true) 成功
    */
  override def deleteById(id: UserId)(implicit ioc: IOContext): Either[RepositoryError, Boolean] =
    Right(storage.remove(id).isDefined)

  /**
    * clearすべて消す
    *
   * @param ioc IOContext
    * @return Right(true) 成功
    */
  override def clear()(implicit ioc: IOContext): Either[RepositoryError, Boolean] = {
    storage.clear()
    Right(true)
  }

}
