package com.example.adaptors.gateways.db

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
