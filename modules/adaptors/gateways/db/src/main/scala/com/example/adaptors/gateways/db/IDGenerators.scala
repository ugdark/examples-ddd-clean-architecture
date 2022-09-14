package com.example.adaptors.gateways.db

import com.example.domain.EntityIdGenerator
import scalikejdbc._

case class IDGeneratorError(message: String) extends Throwable(message)

object IDGenerators {

  protected[db] trait MySQLGenerator {
    protected def generateId(tableName: String): Long =
      (NamedDB(ConnectionPoolName.Write.name).toDB() localTx { implicit session =>
        SQL(s"UPDATE `$tableName` set `id` = `id` + 1")
          .update()
        SQL(s"SELECT `id` from `$tableName`")
          .map(rs => rs.long(1))
          .single()

      }).getOrElse(throw IDGeneratorError(s"unable to generate ID in the $tableName."))
  }

  object User extends EntityIdGenerator with MySQLGenerator {
    override def generate: String = generateId("user_id_numbering").toString
  }

}
