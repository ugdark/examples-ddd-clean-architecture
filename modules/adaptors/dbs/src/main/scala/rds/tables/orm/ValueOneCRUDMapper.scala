package com.example.adaptors.dbs
package rds.tables.orm

import scalikejdbc._
import skinny.orm.SkinnyNoIdCRUDMapper
import MySQLSyntaxSupport.replace

/**
  * 子のテーブルでhasOne
  *
  * @tparam Record record
  */
protected[tables] trait ValueOneCRUDMapper[Id, Record] extends SkinnyNoIdCRUDMapper[Record] {

  protected[this] val referencesFieldName: String

  protected[this] def referencesId(id: Id): Any

  /**
    * 作成、更新を担う
    */
  def store(record: Record)(implicit s: DBSession = autoSession): Int =
    withSQL {
      replace
        .into(this)
        .namedValues(commonNamedValues(record): _*)
    }.update()

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
    }.update()
  }

  /**
    * 親Keyを元に検索
    */
  def findByReferencesId(id: Id)(implicit s: DBSession = autoSession): Option[Record] = {
    extract(withSQL {
      implicit val enableAsIs: ParameterBinderFactory[Any] = ParameterBinderFactory.asisParameterBinderFactory
      selectQueryWithAssociations
        .where(sqls.eq(column.field(referencesFieldName), referencesId(id)))
    }).single()
  }

  protected[this] def commonNamedValues(record: Record): Seq[(SQLSyntax, ParameterBinder)]
}
