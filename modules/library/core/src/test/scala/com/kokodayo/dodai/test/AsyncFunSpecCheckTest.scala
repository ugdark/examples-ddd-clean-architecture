package com.kokodayo.dodai.test

import scala.collection.mutable
import scala.concurrent.Future

/** AsyncFunSpecの理解に向けて
  * https://www.scalatest.org/scaladoc/3.2.13/org/scalatest/funspec/AsyncFunSpec.html
  */

import org.scalatest.*

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext

// Defining actor messages
sealed abstract class StringOp
case object Clear                extends StringOp
case class Append(value: String) extends StringOp
case object GetValue

class AsyncStringBuilderActor { // Simulating an actor
  final private val sb = new mutable.StringBuilder
  def !(op: StringOp): Unit =
    synchronized {
      op match {
        case Append(value) => sb.append(value)
        case Clear         => sb.clear()
      }
    }
  def ?(implicit c: ExecutionContext): Future[String] =
    Future {
      synchronized(sb.toString)
    }
}

class AsyncStringBufferActor {
  final private val buf = ListBuffer.empty[String]
  def !(op: StringOp): Unit =
    synchronized {
      op match {
        case Append(value) => buf += value
        case Clear         => buf.clear()
      }
    }
  def ?(implicit c: ExecutionContext): Future[List[String]] =
    Future {
      synchronized(buf.toList)
    }
}

trait AsyncBuilder extends BeforeAndAfterEach { this: Suite =>

  final val builderActor = new AsyncStringBuilderActor

  override def beforeEach(): Unit = {
    // println("each start")
    builderActor ! Append("ScalaTest is ")
    super.beforeEach() // To be stackable, must call super.beforeEach
  }

  override def afterEach(): Unit =
    try super.afterEach() // To be stackable, must call super.afterEach
    finally
      builderActor ! Clear
  // println("each end")
}

trait AsyncBuffer extends BeforeAndAfterEach { this: Suite =>

  final val bufferActor = new AsyncStringBufferActor

  override def afterEach(): Unit =
    try super.afterEach() // To be stackable, must call super.afterEach
    finally bufferActor ! Clear
}

trait AsyncALL extends BeforeAndAfterAll { this: Suite =>

  final val allBuilderActor = new AsyncStringBuilderActor

  override protected def beforeAll(): Unit = {
    // println("beforeAll start")
    allBuilderActor ! Append("ScalaTestAll is ")
    super.beforeAll()
  }

  override protected def afterAll(): Unit =
    try super.afterAll()
    finally
      allBuilderActor ! Clear
  // println("afterAll end")
}
class AsyncFunSpecCheckTest
    extends funspec.AsyncFunSpec
    with AsyncBuilder
    with AsyncBuffer
    with AsyncALL {

  describe("Testing") {

    it("should be easy") {
      builderActor ! Append("easy!")
      val futureString                               = builderActor.?
      val futureList                                 = bufferActor.?
      val futurePair: Future[(String, List[String])] = futureString zip futureList
      futurePair map { case (str, lst) =>
        assert(str == "ScalaTest is easy!")
        assert(lst.isEmpty)
        bufferActor ! Append("sweet")
        succeed
      }
    }

    it("should be fun") {
      builderActor ! Append("fun!")
      val futureString                               = builderActor.?
      val futureList                                 = bufferActor.?
      val futurePair: Future[(String, List[String])] = futureString zip futureList
      futurePair map { case (str, lst) =>
        assert(str == "ScalaTest is fun!")
        assert(lst.isEmpty)
        bufferActor ! Append("awesome")
        succeed
      }
    }
  }
}

/* 完全にafterAllが最後になってるのを確認
beforeAll start
each start
each end
each start
each end
afterAll end
 */
