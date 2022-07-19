//package com.example.adaptors.dbs
//package rds2.tables.orm
//
//import scalikejdbc.{DBSession, insert, update, _}
//import skinny.orm.SkinnyCRUDMapperWithId
//import skinny.orm.exception.OptimisticLockException
//
///**
//  * 楽観ロックを使用する。
//  * @tparam Id id
//  * @tparam Record record
//  */
//trait EntityCRUDMapper[Id, Record <: EntityRecord] extends SkinnyCRUDMapperWithId[Id, Record] {
//
//  def create(record: Record)(implicit session: DBSession = autoSession): Int = {
//    this.createWithAttributes(commonNamedValues(record): _*)
//    withSQL {
//      insert
//        .into(this)
//        .namedValues(commonNamedValues(record): _*)
//
//    }.update.apply()
//  }
//
//  def updateBy(record: Record)(implicit session: DBSession = autoSession): Int = {
//    val where = sqls
//      .eq(column.id, record.id)
//      .and
//      .eq(column.updatedAt, record.updatedAt)
//    val count = withSQL {
//      update(this)
//        .set(commonNamedValues(record): _*)
//        .where(where)
//    }.update().apply()
//
//    if (count == 0) {
//      throw OptimisticLockException(
//        s"Conflict updated_at is detected (condition: '${where.value}', ${where.parameters.mkString(",")}})"
//      )
//    }
//    count
//  }
//
//  def commonNamedValues(record: Record): Seq[(SQLSyntax, ParameterBinder)]
//
//}
