package com.kokodayo.dodai.validations

import org.scalatest.funspec.AnyFunSpec

class StringRulesSpec extends AnyFunSpec {

  describe("StringRulesSpec") {

    it("should length") {
      assert(StringRules.length("test", Some("a")).isRight)
      assert(StringRules.length("test", Some("")).isLeft)
      assert(StringRules.length("test", Some("    ")).isLeft)
    }

    it("should lengthOpt") {
      assert(StringRules.lengthOpt("test", Some("a")).isRight)
      assert(StringRules.lengthOpt("test", Some("")).isLeft)
      assert(StringRules.lengthOpt("test", Some("    ")).isLeft)
      assert(StringRules.lengthOpt("test", None).isRight)

    }

    it("should maxlength") {
      assert(StringRules.maxlength("test", Some("a")).isRight)
      assert(StringRules.maxlength("test", Some("")).isRight)
      // trimするべきか
      assert(StringRules.maxlength("test", Some("    ")).isRight)
      assert(StringRules.maxlength("test", None).isRight)

    }

    describe("zipCodeに") {
      Seq(Some("0123456"), Some("9999999")).foreach(zipCode =>
        it(s"`$zipCode`を渡すと成功する") {
          assert(StringRules.zipCode("test", zipCode).isRight)
        }
      )
      Seq(Some("a123456"), Some("1234567890"), Some(" "), Some(""), Some(null), None)
        .foreach(zipCode =>
          it(s"`$zipCode`を渡すと失敗する") {
            assert(StringRules.zipCode("test", zipCode).isLeft)
          }
        )
    }

    describe("zipCodeOptに") {
      Seq(Some("0123456"), Some("9999999"), Some(" "), Some(""), Some(null), None)
        .foreach(zipCode =>
          it(s"`$zipCode`を渡すと成功する") {
            assert(StringRules.zipCodeOpt("test", zipCode).isRight)
          }
        )
      Seq(Some("a123456"), Some("1234567890")).foreach(zipCode =>
        it(s"`$zipCode`を渡すと失敗する") {
          assert(StringRules.zipCodeOpt("test", zipCode).isLeft)
        }
      )
    }

    describe("yyyyMMに") {
      Seq("196001", "201901", "201912").foreach(yyyyMM =>
        it(s"`$yyyyMM`を渡すと成功する") {
          assert(StringRules.yyyyMM("test", Some(yyyyMM)).isRight)
        }
      )

      Seq("平成4年8月", "200113", "200101あ", "202990", "-01912").foreach(yyyyMM =>
        it(s"`$yyyyMM`を渡すと失敗する") {
          assert(StringRules.yyyyMM("test", Some(yyyyMM)).isLeft)
        }
      )
    }

    describe("yyyyM0に") {
      Seq("196001", "201901", "201912", "201900", "000000").foreach(yyyyMM =>
        it(s"`$yyyyMM`を渡すと成功する") {
          assert(StringRules.yyyyM0OrEmpty("test", Some(yyyyMM)).isRight)
        }
      )

      Seq("平成4年8月", "200113", "200101あ", "202990", "-01912").foreach(yyyyMM =>
        it(s"`$yyyyMM`を渡すと失敗する") {
          assert(StringRules.yyyyM0OrEmpty("test", Some(yyyyMM)).isLeft)
        }
      )
    }

    describe("電話番号やFAX番号のバリデーション") {
      Seq("03-5339-0551", "090-0900-0900", "0577363351", "").foreach(number =>
        it(s"`$number`を渡すと成功する") {
          assert(StringRules.phone("test", number, 20).isRight)
        }
      )
      Seq("0000-0000-0000-0000-0000", "1111111111111111111111111", "090 0900 0900").foreach(
        number =>
          it(s"`$number`を渡すと失敗する") {
            assert(StringRules.phone("test", number, 20).isLeft)
          }
      )
    }
  }
}
