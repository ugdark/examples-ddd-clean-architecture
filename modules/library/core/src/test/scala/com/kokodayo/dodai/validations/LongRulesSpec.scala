package com.kokodayo.dodai.validations

import org.scalatest.funspec.AnyFunSpec

class LongRulesSpec extends AnyFunSpec {

  describe("LongRulesSpec") {

    it("should length") {
      assert(LongRules.length("test", Some(5)).isRight)
      assert(LongRules.length("test", Some(0)).isRight)
      assert(LongRules.length("test", Some(-1)).isLeft)
    }

    it("should lengthOpt") {
      assert(LongRules.lengthOpt("test", Some(5)).isRight)
      assert(LongRules.length("test", Some(0)).isRight)
      assert(LongRules.length("test", Some(-1)).isLeft)
      assert(LongRules.length("test", None).isLeft)

    }

  }
}
