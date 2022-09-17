package com.example.adaptors.gateways.db.tables

import com.example.adaptors.gateways.db.ConnectionPoolName
import com.example.adaptors.gateways.db.tables.orm.CustomCRUDMapperWithIdAndTimestamp
import com.example.adaptors.gateways.db.tables.records.UserRecord
import scalikejdbc.{DBSession, WrappedResultSet, autoConstruct}
import skinny.orm.Alias

import java.time.Instant
import scala.util.chaining.scalaUtilChainingOps

protected[tables] class UserTable extends CustomCRUDMapperWithIdAndTimestamp[Long, UserRecord] {

  override val tableName = "user"

  override def useAutoIncrementPrimaryKey: Boolean = false

  override def idToRawValue(id: Long): Any = id

  override def rawValueToId(value: Any): Long = value.toString.toLong

  override def defaultAlias: Alias[UserRecord] = createAlias(tableName)

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[UserRecord]): UserRecord =
    autoConstruct(rs, n)

  def create(record: UserRecord)(implicit session: DBSession = autoSession): Long =
    createWithAttributes(commonNamedValues(record): _*)

  def updatedById(record: UserRecord)(implicit session: DBSession = autoSession): Long =
    updateByIdAndTimestamp(record.id, record.updatedAt pipe toJoda)
      .withAttributes(commonNamedValues(record): _*)

  def commonNamedValues(record: UserRecord): Seq[(String, Any)] =
    Seq(
      "id"        -> record.id,
      "name"      -> record.name,
      "password"  -> record.password,
      "createdAt" -> record.createdAt,
      "updatedAt" -> record.updatedAt
    )

  protected def toJoda(instant: Instant): org.joda.time.DateTime =
    new org.joda.time.DateTime(instant.toEpochMilli)

}

protected[db] object UserTable extends UserTable {

  override def connectionPoolName: Any = ConnectionPoolName.Write.name
}
