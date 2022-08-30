package com.example.domain

/** 識別子を表す, EntityId StringにしてるがRDS側でLongで保持してたとしてもStringなら許容できるので基本String
  */
trait EntityId extends Value[String]
