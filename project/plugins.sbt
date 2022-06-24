// commonSetting all packageを省略できるっぽい
addSbtPlugin("org.jetbrains.scala" % "sbt-ide-settings" % "1.1.1")

// ドキュメント作成用
addSbtPlugin("com.lightbend.paradox" % "sbt-paradox" % "0.9.2")

// scalaのコードフォーマッター
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.2")

// scalafix 未使用import削除ツール
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.34")
