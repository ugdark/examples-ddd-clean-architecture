import Dependencies._

//crossScalaVersions := Seq(
//  Versions.scala2,
//  Versions.scala3
//)

val compileAndTest: String = "compile->compile;test->test"

// 責務: domain側で使うというか自分用ライブラリ群
lazy val core = project
  .in(file("library/core"))
  .settings(Core.settings)

// Enterprise Business Rules (Entities)
// 責務: ドメイン知識を定義する層,他の層に依存しない事
/* 実装するクラス
- エンティティ
- 値オブジェクト
- ドメインオブジェクト
- リポジトリのインターフェース
- ドメインサービス
- ファクトリ
 */
lazy val domain = project
  .in(file("modules/domain"))
  .settings(Domain.settings)
  .dependsOn(core % compileAndTest)

// Application Business Rules (Use Cases)
// ApplicationがAppなどと被る事もありまたUseCaseを書いていきたいのでuse-caseと定義してます。
// 責務: ドメイン層を使って何をするかを実現する層
/* 実装するクラス
- ユースケース(アプリケーションサービス)クラス
- プレゼンテーション層との入出力を定義
 */
lazy val useCase = project
  .in(file("modules/use-case"))
  .settings(UseCase.settings)
  .dependsOn(domain % compileAndTest)

// Interface Adapters
// 責務: 入力、永続化、表示を担当するオブジェクトの層
/* 実装するクラス
- implクラス
 */

lazy val adaptersDBs = project
  .in(file("modules/adaptors/dbs"))
  .settings(DBs.settings)
  .dependsOn(useCase % compileAndTest)

//val adaptersAPIs = project
//  .in(file("modules/adaptors/apis"))
//  .settings(APIs.settings)
//  .dependsOn(domain % compileAndTest)

val docs = (project in file("docs"))
  .enablePlugins(ParadoxPlugin)

val aggregatedProjects = Seq[ProjectReference](
//  infrastructure,
  core,
  domain,
  useCase,
  adaptersDBs
//  adaptersAPIs
)

val root = (project in file("."))
  .settings(disableScalaDocSettings)
  .settings(
    name := "examples-ddd-clean-architecture",
    Global / excludeLintKeys += idePackagePrefix,
    // scalafmtAll, scalafmtSbt scalafixまとめて全部
    commands += Command.command("format") { st =>
        Command.process("scalafmtSbt; scalafmtAll; scalafixAll", st)
      }
  )
  .aggregate(aggregatedProjects: _*)

//val example = project
//  .in(file("example"))
//  .settings(
//    scalaVersion := "2.13.6",
//    libraryDependencies ++= Seq(
//      "org.scala-lang" % "scala-reflect" % scalaVersion.value
//    )
//  )
//
