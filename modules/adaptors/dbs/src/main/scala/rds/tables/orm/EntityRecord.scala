package com.example.adaptors.dbs
package rds.tables.orm

import java.time.ZonedDateTime

trait EntityRecord {
  val id: Long
  val updatedAt: ZonedDateTime
}
