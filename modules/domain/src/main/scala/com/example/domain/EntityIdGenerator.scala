package com.example.domain

/** NOTE: エンティティIDの採番方法を抽象化します。 */
trait EntityIdGenerator {
  def generate(): String
}
