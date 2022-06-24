package com.example.domain

import org.scalatest.funspec.AnyFunSpec

class EnumSpec extends AnyFunSpec {

  sealed abstract class TestType(override val id: Int, override val name: String) extends EnumColumn

  object TestType extends EnumWithDefault[TestType] {
    final case object Mansion   extends TestType(10, "マンション")
    final case object Apartment extends TestType(20, "アパート")
    final case object House     extends TestType(30, "一戸建て")

    override val values: IndexedSeq[TestType] = findValues

    override val defaultValue: TestType = House

  }

  describe("動作確認") {

    it("entryNameでの検索") {
      assert(TestType.withName("Mansion") == TestType.Mansion)
    }

    it("idでの検索") {
      assert(TestType.withId(10) == TestType.Mansion)
    }

  }
}
