package com.example.adaptors.dbs

import com.example.domain.IOContext
import scalikejdbc.DBSession

case class IOContextOnSkinny(session: DBSession, dbName: ConnectionPoolName) extends IOContext
