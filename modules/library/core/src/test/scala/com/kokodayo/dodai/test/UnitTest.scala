package com.kokodayo.dodai.test

import org.scalatest.funspec.{AnyFunSpec, FixtureAnyFunSpec}
import org.scalatest.matchers.should.Matchers

/** 自分のUnitTestの基底クラス。
  */
abstract class UnitTest extends AnyFunSpec with Matchers

abstract class UnitSnapShotTest extends FixtureAnyFunSpec with Matchers with SnapshotMatcher
