package com.kokodayo.dodai.validations

import org.scalatest.funspec.AnyFunSpec

class DecimalRulesSpec extends AnyFunSpec {

  describe("DecimalRulesSpec") {

    it("should length") {
      assert(DecimalRules.length("test", Some(BigDecimal(3))).isRight)
      assert(DecimalRules.length("test", Some(BigDecimal(0))).isRight)
      assert(DecimalRules.length("test", None).isLeft)
    }

    it("should latitude") {
      assert(DecimalRules.latitudeOpt("test", Some(BigDecimal(45.3326))).isRight)
      assert(DecimalRules.latitudeOpt("test", Some(BigDecimal(45.3327))).isLeft)
      assert(DecimalRules.latitudeOpt("test", Some(BigDecimal(20.2531))).isRight)
      assert(DecimalRules.latitudeOpt("test", Some(BigDecimal(20.2530))).isLeft)
    }

    it("should longitude") {
      assert(DecimalRules.longitudeOpt("test", Some(BigDecimal(153.5912))).isRight)
      assert(DecimalRules.longitudeOpt("test", Some(BigDecimal(153.5913))).isLeft)
      assert(DecimalRules.longitudeOpt("test", Some(BigDecimal(122.5557))).isRight)
      assert(DecimalRules.longitudeOpt("test", Some(BigDecimal(122.5556))).isLeft)

    }

  }
}
