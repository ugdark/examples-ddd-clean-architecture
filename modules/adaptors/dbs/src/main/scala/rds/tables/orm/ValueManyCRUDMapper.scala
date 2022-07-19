package com.example.adaptors.dbs
package rds.tables.orm

import scalikejdbc._
import skinny.orm.SkinnyNoIdCRUDMapper

/**
  * 子のテーブルでhasMany
  *
  * @tparam Record record
  */
protected[tables] trait ValueManyCRUDMapper[Id, Record] extends SkinnyNoIdCRUDMapper[Record] {

  protected[this] val referencesFieldName: String

  protected[this] def referencesId(id: Id): Any

  /**
    * 作成、更新を担う(基本bulkInsertで実装)
    * 参考：adapters.dbs.connect.repositories.tables.RoomForeignSystemTable
    */
  def store(records: Seq[Record])(implicit s: DBSession = autoSession): Int

  /**
    * 親Keyを元に削除する
    */
  def deleteByReferencesId(id: Id)(implicit s: DBSession = autoSession): Int = {
    withSQL {
      implicit val enableAsIs: ParameterBinderFactory[Any] = ParameterBinderFactory.asisParameterBinderFactory
      delete
        .from(this)
        .where
        .eq(column.field(referencesFieldName), referencesId(id))
    }.update.apply
  }

  /**
    * 親Keyを元に検索
    */
  def findAllByReferencesId(id: Id)(implicit s: DBSession = autoSession): List[Record] = {
    extract(withSQL {
      implicit val enableAsIs: ParameterBinderFactory[Any] = ParameterBinderFactory.asisParameterBinderFactory
      selectQueryWithAssociations
        .where(sqls.eq(column.field(referencesFieldName), referencesId(id)))
    }).list.apply
  }

}
