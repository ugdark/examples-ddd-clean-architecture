// ドキュメント作成用
addSbtPlugin("com.lightbend.paradox" % "sbt-paradox" % "0.9.2")

// scalaのコードフォーマッター
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")

// scalafix 未使用import削除ツール
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.34")

// docker化
addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.9")
