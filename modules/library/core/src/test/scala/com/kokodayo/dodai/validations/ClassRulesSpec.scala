package com.kokodayo.dodai.validations

import org.scalatest.funspec.AnyFunSpec

class ClassRulesSpec extends AnyFunSpec {

  private val classRules = ClassRules

  private case class Dummy(id: Option[Int], name: Option[String])

  describe("allOptionalParametersIsEmptyInCaseClassは") {
    it("元のClassがない場合はTrue") {
      assert(classRules.allOptionalParametersIsEmpty(None))
    }

    it("元のClassがFieldが全部Noneの場合はTrue") {
      val dummy = Dummy(id = None, name = None)
      assert(classRules.allOptionalParametersIsEmpty(dummy))
    }

    it("元のClassがFieldが一つ以上ある場合はFalse") {
      val dummy = Dummy(id = Some(1), name = None)
      assert(!classRules.allOptionalParametersIsEmpty(dummy))
    }
  }
}
