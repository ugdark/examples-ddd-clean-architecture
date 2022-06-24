package com.example.domain

import user.UserId

import com.example.infrastructure.IdGenerator

import java.util.concurrent.atomic.AtomicLong

object DummyIdGenerators {

  protected trait Generator {
    protected val atomicLong = new AtomicLong(1L)
  }

  object User extends IdGenerator[UserId] with Generator {
    override def generate: UserId = UserId(atomicLong.getAndIncrement)
  }

}
