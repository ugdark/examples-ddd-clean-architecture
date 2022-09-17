package com.example.adaptors.gateways.db.tables.orm

import scalikejdbc.*

protected[orm] object MySQLSyntaxSupport {
  implicit class RichInsertSQLBuilder(private val self: InsertSQLBuilder) extends AnyVal {
    def onDuplicateKeyUpdate(columnAndValues: (SQLSyntax, Any)*): InsertSQLBuilder = {
      val csv = columnAndValues.map { case (c, v) => sqls"$c = $v" }
      onDuplicateKeyUpdate(sqls.csv(csv: _*))
    }

    def onDuplicateKeyUpdate(update: SQLSyntax): InsertSQLBuilder =
      self.append(sqls"on duplicate key update $update")
  }

  val replace: QueryDSL.replace.type = QueryDSL.replace

  object QueryDSL {
    object replace {
      def into(support: SQLSyntaxSupport[_]): InsertSQLBuilder = InsertSQLBuilder(
        sqls"replace into ${support.table}"
      )
    }
  }

}
