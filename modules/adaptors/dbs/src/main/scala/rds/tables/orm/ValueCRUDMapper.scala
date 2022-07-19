package com.example.adaptors.dbs
package rds.tables.orm

import scalikejdbc._
import skinny.orm.SkinnyNoIdCRUDMapper

/**
  * 子のテーブルは物理レコード削除になる
  * 基本deleteしてinsertする方式
  * @tparam Record record
  */
trait ValueCRUDMapper[Id, Record] extends SkinnyNoIdCRUDMapper[Record] {

  def referencesFieldName: String

  def referencesId(id: Id): Any

  def inserts(records: collection.Seq[Record])(implicit session: DBSession = autoSession): List[Int]

  /**
    * 親Keyを元にすべて削除する
    */
  def deleteByReferencesId(id: Id)(implicit session: DBSession = autoSession): Int = {
    withSQL {
      implicit val enableAsIs: ParameterBinderFactory[Any] = ParameterBinderFactory.asisParameterBinderFactory
      delete
        .from(this)
        .where
        .eq(column.field(referencesFieldName), referencesId(id))
    }.update()
  }

  /**
    * 親Keyを元に検索
    * sortを想定したが都度全消し、全追加するので登録順のままとする
    */
  def findByReferencesId(id: Id)(implicit session: DBSession = autoSession): Seq[Record] = {
    extract(withSQL {
      implicit val enableAsIs: ParameterBinderFactory[Any] = ParameterBinderFactory.asisParameterBinderFactory
      selectQueryWithAssociations
        .where(sqls.eq(column.field(referencesFieldName), referencesId(id)))
    }).list()
  }

}
