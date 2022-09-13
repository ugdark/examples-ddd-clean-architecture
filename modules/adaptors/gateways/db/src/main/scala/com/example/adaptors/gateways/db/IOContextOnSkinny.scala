package com.example.adaptors.gateways.db

import com.example.domain.IOContext
import scalikejdbc.DBSession

protected[db] case class IOContextOnSkinny(session: DBSession, dbName: ConnectionPoolName)
    extends IOContext
