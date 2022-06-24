package com.example.domain

import java.time.Instant

trait Timestamp {

  val value: Instant
}

/**
  * 永続先の作成日時を表す
  * @param value Instant
  */
case class CreatedAt(value: Instant) extends Timestamp

/**
  * 永続先の更新日時を表す
  * @param value Instant
  */
case class UpdatedAt(value: Instant) extends Timestamp
