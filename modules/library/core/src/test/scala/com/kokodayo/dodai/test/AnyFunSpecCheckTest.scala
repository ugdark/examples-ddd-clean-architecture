package com.kokodayo.dodai.test

import org.scalatest.*
import org.scalatest.funspec.AnyFunSpec

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/** AnyFunSpecの理解に向けて
  * https://www.scalatest.org/scaladoc/3.2.13/org/scalatest/funspec/AnyFunSpec.html
  */
trait Builder extends BeforeAndAfterEach { this: Suite =>

  val builder = new mutable.StringBuilder

  override def beforeEach(): Unit = {
    // println("each start")
    builder.append("ScalaTest is ")
    super.beforeEach() // To be stackable, must call super.beforeEach
  }

  override def afterEach(): Unit =
    try super.afterEach() // To be stackable, must call super.afterEach
    finally
      builder.clear()
  // println("each end")
}

trait Buffer extends BeforeAndAfterEach { this: Suite =>

  val buffer = new ListBuffer[String]

  override def afterEach(): Unit =
    try super.afterEach() // To be stackable, must call super.afterEach
    finally buffer.clear()
}

trait ALL extends BeforeAndAfterAll { this: Suite =>

  val allBuilder = new mutable.StringBuilder

  override protected def beforeAll(): Unit = {
    // println("beforeAll start")
    allBuilder.append("ScalaTestAll is ")
    super.beforeAll()
  }

  override protected def afterAll(): Unit =
    try super.afterAll()
    finally
      // println(allBuilder)
      allBuilder.clear()
  // println("afterAll end")
}

class AnyFunSpecCheckTest extends AnyFunSpec with Builder with Buffer with ALL {

  describe("Testing") {
    it("should be easy") {
      builder.append("easy!")
      allBuilder.append("easy!")
      assert(builder.toString === "ScalaTest is easy!")
      assert(buffer.isEmpty)
      buffer += "sweet"
    }

    it("should be fun") {
      builder.append("fun!")
      allBuilder.append("fun!")
      assert(builder.toString === "ScalaTest is fun!")
      assert(buffer.isEmpty)
      buffer += "clear"
    }
  }
}

/* 完全にafterAllが最後になってるのを確認
beforeAll start
each start
each end
each start
each end
ScalaTestAll is easy!fun!
afterAll end
 */
