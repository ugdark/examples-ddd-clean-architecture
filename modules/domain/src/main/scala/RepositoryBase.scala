package com.example.domain

/**
  * Repositoryの基本の振る舞いを表す
  * @tparam Id Entity[Id]
  * @tparam Entity Entity
  * @tparam Entities Entityの配列を表す
  */
trait RepositoryBase[Id <: Identifier[_], Entity <: EntityTimestamp, Entities] {

  /**
    * 検索する
    * @param paging Paging Paging
    * @param ioc IOContext
    * @return Right(Entity) 成功
    */
  def findAll(paging: Paging = Paging.All)(implicit ioc: IOContext = IOContext.Empty): Either[RepositoryError, Entities]

  /**
    * Entityを保存する
    * @param entity Entity
    * @param ioc IOContext
    * @return Right(Entity) 成功
    */
  def store(entity: Entity)(implicit ioc: IOContext = IOContext.Empty): Either[RepositoryError, Entity]

  /**
    * id指定のEntityを返す
    * @param id 識別子
    * @param ioc IOContext
    * @return Right(Some(Entity)) 成功
    *         Right(None) 存在しない
    */
  def findById(id: Id)(implicit ioc: IOContext = IOContext.Empty): Either[RepositoryError, Option[Entity]]

  /**
    * id指定の物のEntityを削除する。
    * @param id 識別子
    * @param ioc IOContext
    * @return Right(true) 成功
    */
  def deleteById(id: Id)(implicit ioc: IOContext = IOContext.Empty): Either[RepositoryError, Boolean]

  /**
    * clearすべて消す
    * @param ioc IOContext
    * @return Right(true) 成功
    */
  def clear()(implicit ioc: IOContext = IOContext.Empty): Either[RepositoryError, Boolean]

}
