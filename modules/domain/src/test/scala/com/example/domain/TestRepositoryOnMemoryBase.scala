package com.example.domain

import com.example.domain

import scala.util.{Success, Try}

/** Test用のOnMemory共通クラス
  */
trait TestRepositoryOnMemoryBase[Id <: domain.EntityId, E <: domain.Entity[Id]]
    extends RepositoryBase[Try, Id, E] {

  private val storage: scala.collection.mutable.LinkedHashMap[Id, E] =
    scala.collection.mutable.LinkedHashMap.empty

  /** Entityを保存する
    *
    * @param entity
    *   Entity
    * @param ioc
    *   IOContext
    * @return
    *   Success(Entity) 成功
    */
  override def store(entity: E)(implicit ioc: IOContext = IOContext.Empty): Try[E] = {
    storage.update(entity.id, entity)
    Success(entity)
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
  override def findById(id: Id)(implicit ioc: IOContext = IOContext.Empty): Try[Option[E]] =
    Success(storage.get(id))

  /** id指定の物のEntityを削除する。
    *
    * @param id
    *   識別子
    * @param ioc
    *   IOContext
    * @return
    *   Success(true) 成功
    */
  override def deleteById(id: Id)(implicit ioc: IOContext = IOContext.Empty): Try[Boolean] =
    Success(storage.remove(id).isDefined)

  /** clearすべて消す
    *
    * @return
    *   Success(true) 成功
    */
  def clear(): Try[Boolean] = {
    storage.clear()
    Success(true)
  }

}
