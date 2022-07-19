package com.example.adaptors.dbs
package rds

import rds.tables.UserTable
import rds.tables.records.UserRecord

import com.example.domain.IOContext
import com.example.domain.user.{User, UserId, UserRepository}

import scala.util.Try

object UserRepositoryOnSkinny extends UserRepository with UseDB {
//  /**
//   * 検索する
//   *
//   * @param paging Paging Paging
//   * @param ioc    IOContext
//   * @return Right(Entity) 成功
//   */
//  override def findAll(paging: Paging)(implicit ioc: IOContext): Either[RepositoryError, Users] = {
//    withSession(ioc) { implicit session =>
//      Right(conditionsToWhere(conditions) match {
//        case Some(where) =>
//          switchTable(ioc)
//            .findAllByWithLimitOffset(where, conditions.limit, conditions.offset)
//        case None =>
//          switchTable(ioc)
//            .findAllWithLimitOffset(conditions.limit, conditions.offset)
//      })
//    }
//  }
//
//  /**
//   * Entityを保存する
//   *
//   * @param entity Entity
//   * @param ioc    IOContext
//   * @return Right(Entity) 成功
//   */
//  override def store(entity: User)(implicit ioc: IOContext): Either[RepositoryError, User] = ???
//
//  /**
//   * id指定のEntityを返す
//   *
//   * @param id  識別子
//   * @param ioc IOContext
//   * @return Right(Some(Entity)) 成功
//   *         Right(None) 存在しない
//   */
//  override def findById(id: UserId)(implicit ioc: IOContext): Either[RepositoryError, Option[User]] = ???
//
//  /**
//   * id指定の物のEntityを削除する。
//   *
//   * @param id  識別子
//   * @param ioc IOContext
//   * @return Right(true) 成功
//   */
//  override def deleteById(id: UserId)(implicit ioc: IOContext): Either[RepositoryError, Boolean] = ???
//
//  /**
//   * clearすべて消す
//   *
//   * @param ioc IOContext
//   * @return Right(true) 成功
//   */
//  override def clear()(implicit ioc: IOContext): Either[RepositoryError, Boolean] = ???

  /**
    * Entityを保存する
    *
   * @param entity Entity
    * @param ioc    IOContext
    * @return Success(Entity) 成功
    */
  override def store(entity: User)(implicit ioc: IOContext): Try[User] = {
    withSession(ioc) { implicit session =>
      Try {
        UserTable.create(UserRecord.fromEntity(entity))
        UserTable
          .findById(entity.id.value.toLong)
          .map(_.toEntity)
          .getOrElse(throw NotFoundException(s"not found account id [${entity.id.value}]"))
      }
    }
  }

  /**
    * id指定のEntityを返す
    *
   * @param id  識別子
    * @param ioc IOContext
    * @return Success(Some(Entity)) 成功
    *         Success(None) 存在しない
    */
  override def findById(id: UserId)(implicit ioc: IOContext): Try[Option[User]] = ???

  /**
    * id指定の物のEntityを削除する。
    *
   * @param id  識別子
    * @param ioc IOContext
    * @return Success(true) 成功
    */
  override def deleteById(id: UserId)(implicit ioc: IOContext): Try[Boolean] = ???
}
