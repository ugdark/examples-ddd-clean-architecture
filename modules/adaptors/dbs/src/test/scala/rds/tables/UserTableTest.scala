package com.example.adaptors.dbs
package rds.tables

import rds.AutoRollbackSpec
import rds.tables.records.UserRecord

import com.example.domain.EntityMetaDataCreator
import com.example.domain.user.UserFixture
import skinny.orm.exception.OptimisticLockException

import java.time.Instant

class UserTableTest extends AutoRollbackSpec {

  private implicit val metaDataCreator: EntityMetaDataCreator = EntityMetaDataCreatorImpl

  describe("ユーザーテーブル") {

    it("作成する") { implicit session =>
      val record       = UserRecord.fromEntity(UserFixture.generate())
      val before: Long = UserTable.count()

      /* insert into user (id, name) values (1, 'dFLpfapCZA'); */
      UserTable.create(record)
      val after: Long = UserTable.count()
      assert(before + 1 == after)

    }

    it("更新する") { implicit session =>
      val record = UserRecord.fromEntity(UserFixture.generate())
      UserTable.create(record)
      val created = UserTable.findById(record.id.get).get

      Thread.sleep(10) // 早すぎるのでtimestampズレを出すため

      /* update user set id = 2, name = 'test' , updated_at = 2022-07-05T13:10:24.321077+09:00[Asia/Tokyo] where id = 2 and updated_at = '2022-07-05 04:10:24.0'; */
      val result = UserTable
        .updatedById(
          created.copy(
            name = "test"
          )
        )
      assert(result >= 1)
      val updated = UserTable.findById(record.id.get)

      assert(updated.get.name == "test")
      assert(updated.get.updatedAt.get.isAfter(created.updatedAt.get))
    }

    it("楽観ロックを確認する") { implicit session =>
      val record = UserRecord.fromEntity(UserFixture.generate())
      UserTable.create(record)
      val created = UserTable.findById(record.id.get).get

      intercept[OptimisticLockException] {
        UserTable
          .updatedById(
            created.copy(
              updatedAt = created.updatedAt.map(_.minusSeconds(100))
            )
          )
      }
    }

    it("検索する") { implicit session =>
      val record = UserRecord.fromEntity(UserFixture.generate())
      UserTable.create(record)

      /* select user.id as i_on_user, user.name as n_on_user, user.created_at as ca_on_user, user.updated_at as ua_on_user from user where user.id = 4; */
      val find = UserTable.findById(record.id.get).get

      assert(find.name == record.name)
      assert(find.createdAt.get.isBefore(Instant.now()))
      assert(find.updatedAt.get.isBefore(Instant.now()))
    }

    it("削除する") { implicit session =>
      val record = UserRecord.fromEntity(UserFixture.generate())
      UserTable.create(record)

      /* delete from user where id = 1; */
      val result = UserTable.deleteById(record.id.get)
      assert(result == 1)

      val find = UserTable.findById(record.id.get)
      assert(find.isEmpty)
    }

  }

}
