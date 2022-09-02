package com.example.adaptors.presenters.examples

import org.hashids.Hashids
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

/** 使い方忘れそうなんで
  * @see
  *   https://qiita.com/ryo_hama/items/644a35c38070fa152a0b
  */
class HashidsTest extends AnyFunSpec with Matchers {

  describe("Hashidsの使い方確認") {
    it("暗号化して復号化") {
      val hashids: Hashids    = new Hashids("お塩", 16)
      val source              = 123
      val encode: String      = hashids.encode(source)
      val decode: Array[Long] = hashids.decode(encode)
      encode shouldBe "vX0Aw1pE3x4YldNR"
      decode shouldBe Seq(source)
    }
  }
}
