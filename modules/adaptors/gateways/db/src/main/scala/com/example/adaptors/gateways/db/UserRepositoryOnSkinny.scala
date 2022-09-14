package com.example.adaptors.gateways.db

import com.example.adaptors.gateways.db.tables.UserTable
import com.example.adaptors.gateways.db.tables.records.UserRecord
import com.example.domain.IOContext
import com.example.domain.user.{User, UserId, UserName, UserRepository}
import scalikejdbc.SQL

import scala.util.Try

object UserRepositoryOnSkinny extends UserRepository with UseDB {

  /** Entityを保存する
    *
    * @param entity
    *   Entity
    * @param ioc
    *   IOContext
    * @return
    *   Success(Entity) 成功
    */
  override def store(entity: User)(implicit ioc: IOContext): Try[User] =
    withSession(ioc) { implicit session =>
      Try {
        UserTable.create(UserRecord.fromEntity(entity))
        entity
      }
    }

  /** id指定のEntityを返す
    *
    * @param id
    *   識別子
    * @param ioc
    *   IOContext
    * @return
    *   Success(Some(Entity)) 成功 Success(None) 存在しない
    */
  override def findById(id: UserId)(implicit ioc: IOContext): Try[Option[User]] =
    withSession(ioc) { implicit session =>
      Try {
        UserTable
          .findById(id.value.toLong)
          .map(_.toEntity)
      }
    }

  /** id指定の物のEntityを削除する。
    *
    * @param id
    *   識別子
    * @param ioc
    *   IOContext
    * @return
    *   Success(true) 成功
    */
  override def deleteById(id: UserId)(implicit ioc: IOContext): Try[Boolean] =
    withSession(ioc) { implicit session =>
      Try {
        UserTable.deleteById(id.value.toLong) == 1
      }
    }

  /** 名前が存在するか
    *
    * @param name
    *   UserName
    * @return
    */
  override def verifyForDuplicateNames(name: UserName)(implicit ioc: IOContext): Boolean =
    withSession(ioc) { implicit session =>
      Try {
        val result = SQL(
          s"""
             | SELECT
             |  1
             | FROM `${UserTable.tableName}`
             | WHERE name = {name}
             |""".stripMargin
        ).bindByName(
          "name" -> name.value
        ).map(rs => rs.int(1))
          .single
          .apply()
          .getOrElse(0)
        result == 1
      }
    }.get
}
