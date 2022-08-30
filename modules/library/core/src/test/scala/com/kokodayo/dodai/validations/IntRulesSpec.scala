package com.kokodayo.dodai.validations

import org.scalatest.funspec.AnyFunSpec

class IntRulesSpec extends AnyFunSpec {

  describe("IntRulesSpec") {

    it("should length") {
      assert(IntRules.length("test", Some(5)).isRight)
      assert(IntRules.length("test", Some(0)).isRight)
      assert(IntRules.length("test", Some(-1)).isLeft)
    }

    it("should lengthOpt") {
      assert(IntRules.lengthOpt("test", Some(5)).isRight)
      assert(IntRules.length("test", Some(0)).isRight)
      assert(IntRules.length("test", Some(-1)).isLeft)
      assert(IntRules.length("test", None).isLeft)

    }

  }
}
