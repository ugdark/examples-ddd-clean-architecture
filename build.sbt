import Dependencies._

// 責務: domain側で使うというか自分用ライブラリ群
lazy val core = project
  .in(file("modules/library/core"))
  .settings(commonSettings, testSettings)
  .settings(libraryDependencies ++= Seq(Modules.googleDiff, Modules.typeSafe.config))
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
  .settings(commonSettings, testSettings)
  .settings(libraryDependencies ++= Seq(Modules.typeSafe.config))
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
  .settings(commonSettings, testSettings)
  .settings(name := "use-case", libraryDependencies ++= Seq(Modules.typeSafe.config))
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
  .settings(commonSettings, testSettings)
  .settings(
    libraryDependencies ++= Seq(
      Modules.mysql,
      Modules.skinnyOrm,
      Modules.scalikejdbc.mapperGeneratorCore,
      Modules.scalikejdbc.test % Test
    )
  )
  .dependsOn(useCase % compileAndTest)
// 実際にインスタンスを持つ外部にServiceとして提供する
// apiServiceとかにしたいかもweb分かりづらい
lazy val web = project
  .in(file("modules/adaptors/controllers/web"))
  .settings(commonSettings, testSettings)
  .dependsOn(db % compileAndTest, api % compileAndTest)
val compileAndTest: String = "compile->compile;test->test"
val api = project
  .in(file("modules/adaptors/presenters/api"))
  .settings(commonSettings, testSettings)
  .settings(
    libraryDependencies ++= Seq(
      Modules.typeSafe.AkkaHttp,
      Modules.AkkaHttpCirce,
      Modules.hashids
    )
  )
  .dependsOn(useCase % compileAndTest)
val docs = (project in file("docs"))
  .enablePlugins(ParadoxPlugin)

val aggregatedProjects = Seq[ProjectReference](core, domain, useCase, db, api, web)

val root = (project in file("."))
  .settings(disableScalaDocSettings)
  .settings(
    name := "examples-ddd-clean-architecture",
    // scalafmtAll, scalafmtSbt scalafixまとめて全部
    commands += Command.command("format") { st =>
      Command.process("scalafmtSbt; scalafmtAll; scalafixAll", st)
    }
  )
  .aggregate(aggregatedProjects: _*)
