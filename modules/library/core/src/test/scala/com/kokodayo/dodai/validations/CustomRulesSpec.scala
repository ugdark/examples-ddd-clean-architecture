package com.kokodayo.dodai.validations

import org.scalatest.funspec.AnyFunSpec

class CustomRulesSpec extends AnyFunSpec {

  describe("CustomRulesSpec") {

    it("どちらか必須か確認") {
      assert(CustomRules.or("test", None, None).isLeft)
      assert(CustomRules.or("test", Some(false), None).isRight)
      assert(CustomRules.or("test", None, Some(false)).isRight)

    }

    it("重複確認") {
      assert(
        CustomRules
          .distinct("test", Seq("itandi-124", Seq("itandi-125"), Seq("itandi-126")))
          .isRight
      )
      assert(
        CustomRules.distinct("test", Seq("itandi-124", Seq("itandi-125"), Seq("itandi-125"))).isLeft
      )
    }

    it("ids用の確認") {
      assert(CustomRules.idsRequired("test", Some("10")).toOption.get == Seq(10))
      assert(CustomRules.idsRequired("test", Some("1,2,3")).toOption.get == Seq(1, 2, 3))
      assert(CustomRules.idsRequired("test", Some("1-2-3")).isLeft)
      assert(CustomRules.idsRequired("test", Some("")).isLeft)
      assert(CustomRules.idsRequired("test", None).isLeft)

    }

    it("booleanOrDefaultの確認") {
      assert(CustomRules.booleanOrDefault(Some("true")))
      assert(CustomRules.booleanOrDefault(Some("True")))
      assert(!CustomRules.booleanOrDefault(Some("false")))
      assert(!CustomRules.booleanOrDefault(Some("1")))
      assert(!CustomRules.booleanOrDefault(Some("")))
      assert(!CustomRules.booleanOrDefault(None))
    }

  }
}
