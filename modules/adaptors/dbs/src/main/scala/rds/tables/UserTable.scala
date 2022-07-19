package com.example.adaptors.dbs
package rds.tables

import rds.tables.orm.CustomCRUDMapperWithIdAndTimestamp
import rds.tables.records.UserRecord

import scalikejdbc.{autoConstruct, DBSession, WrappedResultSet}
import skinny.orm.Alias

import java.time.Instant

sealed class UserTable extends CustomCRUDMapperWithIdAndTimestamp[Long, UserRecord] {

  override val tableName = "user"

  override def idToRawValue(id: Long): Any = id

  override def rawValueToId(value: Any): Long = value.toString.toLong

  override def defaultAlias: Alias[UserRecord] = createAlias(tableName)

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[UserRecord]): UserRecord =
    autoConstruct(rs, n)

  def commonNamedValues(record: UserRecord): Seq[(String, Any)] = {
    Seq(
      "id"   -> record.id,
      "name" -> record.name
    )
  }

  def create(record: UserRecord)(implicit session: DBSession = autoSession): Long =
    createWithAttributes(commonNamedValues(record): _*)

  def updatedById(record: UserRecord)(implicit session: DBSession = autoSession): Long =
    updateByIdAndTimestamp(record.id.get, record.updatedAt.map(toJoda))
      .withAttributes(commonNamedValues(record): _*)

  protected def toJoda(instant: Instant): org.joda.time.DateTime = {
    new org.joda.time.DateTime(instant.toEpochMilli)

  }

}

protected[rds] object UserTable extends UserTable {

  override def connectionPoolName: Any = ConnectionPoolNames.Write.name
}
