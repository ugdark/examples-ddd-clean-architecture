package com.kokodayo.dodai.test

import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers

class SnapShotMatcherTest extends FixtureAnyFunSpec with Matchers with SnapshotMatcher {

  private case class School(name: String)

  private case class Club(name: String)

  private case class Fuga(name: String)

  private case class Hoge(fuga: Fuga)

  private case class User(
    name: String = "nishiyama",
    message: String = "hoge",
    school: School = School("本郷小学校"),
    club: Club = Club("野球部"),
    hoge: Hoge = Hoge(Fuga("moge"))
  )

  describe("スナップショットを取る") {

    describe("動作確認の為の階層") {

      it("classのsnapShotを取る") { implicit test =>
        val user = User()
        user should matchSnapshot()
      }

      it("optionのsnapShotを取る") { implicit test =>
        // prettyPrint case opt: Some[?] 追加
        // これいれないと Some(yamada)などダブルクオートがなくなるんで
        val user: Map[String, Any] =
          Map(
            "user" -> Map(
              "id"    -> 1,
              "name"  -> Some("yamada"),
              "child" -> Map("id" -> 2, "name" -> None)
            )
          )
        user should matchSnapshot()
      }

      it("mapのsnapShotを取る") { implicit test =>
        val user: Map[String, Any] =
          Map(
            "user" -> Map(
              "id"    -> 1,
              "name"  -> "yamada",
              "child" -> Map("id" -> 2, "name" -> "hana")
            )
          )
        user should matchSnapshot()
      }

      it("jsonのsnapShotを取る") { implicit test =>
        // response.asJson.spaces2 相当
        val json = """{
                     |  "name" : "tester",
                     |  "message" : "hello"
                     |}""".stripMargin
        json should matchSnapshotNoEscape()
      }

    }
  }
}
