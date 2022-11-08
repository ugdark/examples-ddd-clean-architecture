package com.example.adaptors.presenters.api

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.kokodayo.dodai.test.SnapshotMatcher
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.AutoDerivation
import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers

trait ControllerUnitSpec
    extends FixtureAnyFunSpec
    with ScalatestRouteTest
    with FailFastCirceSupport
    with AutoDerivation
    with Matchers
    with SnapshotMatcher
