import Dependencies._

val compileAndTest: String = "compile->compile;test->test"

// 責務: domain側で使うというか自分用ライブラリ群
lazy val core = project
  .in(file("modules/library/core"))
  .settings(CommonSettings, TestSettings)
  .settings(libraryDependencies ++= Seq(Modules.GoogleDiff, Modules.TypeSafe.Config))
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
  .settings(CommonSettings, TestSettings)
  .settings(
    libraryDependencies ++= Seq(
      Modules.TypeSafe.Config,
      "org.apache.poi" % "poi"       % "5.2.3",
      "org.apache.poi" % "poi-ooxml" % "5.2.3"
    )
  )
  .dependsOn(core % compileAndTest)
// Application Business Rules (Use Cases)
// ApplicationがAppなどと被る事もありまたUseCaseを書いていきたいのでuse-caseと定義してます。
// 責務: ドメイン層を使って何をするかを実現する層
/* 実装するクラス
- ユースケース(アプリケーションサービス)クラス
- CQRSの概念からだけど、QueryProcessorを配置
- プレゼンテーション層との入出力を定義
 */
lazy val useCase = project
  .in(file("modules/application/use-case"))
  .settings(CommonSettings, TestSettings)
  .settings(name := "use-case", libraryDependencies ++= Seq(Modules.TypeSafe.Config))
  .dependsOn(domain % compileAndTest)
// CQRSの概念からだけど、Domainに依存しないDAO 検索のみを担う
// 永続化時の検証時に使いたいとかあるけど悩む
lazy val query = project
  .in(file("modules/application/query"))
// Interface Adapters
// 責務: 入力、永続化、表示を担当するオブジェクトの層
/* 実装するクラス
- implクラス
 */
lazy val db = project
  .in(file("modules/adaptors/gateways/db"))
  .settings(CommonSettings, TestSettings)
  .settings(
    libraryDependencies ++= Seq(
      Modules.Mysql,
      Modules.SkinnyOrm,
      Modules.Scalikejdbc.MapperGeneratorCore,
      Modules.Scalikejdbc.Test % Test
    )
  )
  .dependsOn(useCase % compileAndTest)

val api = project
  .in(file("modules/adaptors/presenters/api"))
  .settings(CommonSettings, TestSettings)
  .settings(
    libraryDependencies ++= Seq(
      Modules.TypeSafe.AkkaHttp,
      Modules.AkkaHttpCirce,
      Modules.Hashids
    ) ++ Modules.CirceLibs ++ Modules.TypeSafe.AkkaLibs
  )
  .dependsOn(useCase % compileAndTest)

val docs = (project in file("docs"))
  .enablePlugins(ParadoxPlugin)

// 実際にインスタンスを持つ外部にServiceとして提供する
lazy val `api-server` = project
  .in(file("modules/adaptors/controllers/api-server"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    dockerBaseImage    := "adoptopenjdk/openjdk15:slim",
    dockerEntrypoint   := Seq("/opt/docker/bin/api-server"),
    dockerUpdateLatest := true,
    dockerExposedPorts += 4649
  )
  .settings(CommonSettings, TestSettings)
  .dependsOn(db, api)

val aggregatedProjects = Seq[ProjectReference](core, domain, useCase, db, api, `api-server`)

val root = (project in file("."))
  .settings(DisableScalaDocSettings)
  .settings(
    name := "examples-ddd-clean-architecture",
    // scalafmtAll, scalafmtSbt scalafixまとめて全部
    commands += Command.command("format") { st =>
      Command.process("scalafmtSbt; scalafmtAll; scalafixAll", st)
    }
  )
  .aggregate(aggregatedProjects: _*)
