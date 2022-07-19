package com.example.domain
package user

case class User(
    id: UserId,
    name: UserName,
    metaData: EntityMetaData
) extends Entity[UserId]
