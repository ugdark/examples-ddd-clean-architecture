package com.example.domain.user

import com.example.domain.{Entity, EntityMetaData}

case class User(id: UserId, name: UserName, metaData: EntityMetaData) extends Entity[UserId]
