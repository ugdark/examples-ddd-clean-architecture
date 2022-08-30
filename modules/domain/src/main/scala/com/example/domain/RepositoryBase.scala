package com.example.domain

import com.example.domain

/** Repositoryの基本の振る舞いを表す Domain的なErrorは用いず例外のみを返す仕様にする。
  *
  * @tparam TF
  *   Try or Futureを指定 基本 Future, Tryのみで
  * @tparam Id
  *   EntityId
  * @tparam E
  *   Entity
  */
trait RepositoryBase[TF[_], Id <: domain.EntityId, E <: domain.Entity[Id]] {

  /** Entityを保存する
    * @param entity
    *   Entity
    * @param ioc
    *   IOContext
    * @return
    *   Success(Entity) 成功
    */
  def store(entity: E)(implicit ioc: IOContext): TF[E]

  /** id指定のEntityを返す
    * @param id
    *   識別子
    * @param ioc
    *   IOContext
    * @return
    *   Success(Some(Entity)) 成功 Success(None) 存在しない
    */
  def findById(id: Id)(implicit ioc: IOContext): TF[Option[E]]

  /** id指定の物のEntityを削除する。
    * @param id
    *   識別子
    * @param ioc
    *   IOContext
    * @return
    *   Success(true) 成功
    */
  def deleteById(id: Id)(implicit ioc: IOContext): TF[Boolean]

}
