package com.kokodayo.dodai.enumeratum

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.Seq

class EnumTest extends AnyFunSpec with Matchers {

  sealed abstract class EnumColumnImpl(override val id: Int, override val name: String)
      extends EnumColumn

  final private case object EnumColumn1 extends EnumColumnImpl(1, "1")
  final private case object EnumColumn2 extends EnumColumnImpl(2, "2")

  private object EnumImpl extends Enum[EnumColumnImpl] {
    val values: Seq[EnumColumnImpl] = Seq(EnumColumn1, EnumColumn2)
  }

  describe("findOrDefaultは") {
    it("存在するIntを受け取ったらそれを返す") {
      EnumImpl.of(2) shouldBe EnumColumn2
    }

    it("存在しないIntを受け取ったらデフォルト値を返す") {
      EnumImpl.orDefault(3) shouldBe EnumColumn1
    }

    it("存在するOption[Int]を受け取ったらそれを返す") {
      EnumImpl.orDefault(Option(2)) shouldBe EnumColumn2
    }

    it("存在しないOption[Int]を受け取ったらデフォルト値を返す") {
      EnumImpl.orDefault(Option(3)) shouldBe EnumColumn1
    }

    it("存在するStringを受け取ったらそれを返す") {
      EnumImpl.orDefault("EnumColumn2") shouldBe EnumColumn2
    }

    it("存在しないStringを受け取ったらデフォルト値を返す") {
      EnumImpl.orDefault("EnumColumn3") shouldBe EnumColumn1
    }

    it("存在するOption[String]を受け取ったらそれを返す") {
      EnumImpl.orDefault(Option("EnumColumn2")) shouldBe EnumColumn2
    }

    it("存在するOption[String]の大文字、小文字を同一として判断それを返す") {
      EnumImpl.orDefault(Option("enumColumn2")) shouldBe EnumColumn2
    }

    it("存在しないOption[String]を受け取ったらデフォルト値を返す") {
      EnumImpl.orDefault(Option("EnumColumn3")) shouldBe EnumColumn1
    }

    it("Noneを受け取ったらデフォルト値を返す") {
      EnumImpl.orDefault(None) == EnumColumn1
    }
  }
}
