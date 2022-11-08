package com.kokodayo.dodai.test

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId}

// test側がちゃんとUTC時間でtestされてるかを確認
// javaOptions in Test += "-Dconfig.file=conf/application_test.conf"
// をしてもfork trueにしないと効果ないらしいので
class UTCCheckTest extends AnyFunSpec with Matchers {

  it("UTC時間でテストされてる事") {
    ZoneId.systemDefault() shouldBe ZoneId.of("GMT")
    LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    // 秒以下は削りたいので
    formatter.format(LocalDateTime.now()) shouldBe formatter.format(
      LocalDateTime.now(ZoneId.of("UTC"))
    )
  }

}
