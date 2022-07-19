package com.example.adaptors.dbs
package rds

import scalikejdbc.config.DBs

trait DBSettings {
  DBSettings.initialize()
}

object DBSettings {

  private var isInitialized = false

  def initialize(): Unit =
    synchronized {
      if (isInitialized) return
      DBs.setupAll()
      isInitialized = true
    }

}
