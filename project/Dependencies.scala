import sbt.Keys._
import sbt._
import scalafix.sbt.ScalafixPlugin.autoImport.{scalafixOnCompile, scalafixSemanticdb}

import java.time.format.DateTimeFormatter
import java.time.{ZoneId, ZonedDateTime}

object Dependencies {

  val Timestamp: String =
    ZonedDateTime
      .now(ZoneId.of("Asia/Tokyo"))
      .format(DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss"))

  // Disable ScalaDoc
  val DisableScalaDocSettings: SettingsDefinition =
    Seq(Compile / doc / sources := Seq.empty, Compile / packageDoc / publishArtifact := false)
  val CommonSettings: SettingsDefinition = Seq(
    organization := "com.kokodayo",
    scalaVersion := "2.13.8",
    version      := "1.0.0",
    scalacOptions := Seq(
      // https://xuwei-k.hatenablog.com/entry/2020/07/10/173028
      "-Xsource:3",
      "-target:jvm-1.8",
      "-deprecation", // @deprecated なAPIが使われている箇所を警告します
      "-feature",     //  language feature の import が必要な箇所を警告します
      "-unchecked",
      "-Xlint" // 警告もりもり
      // "-Xfatal-warnings" // Xlintのwarningをerrorとしちゃう testを書いてる時とか面倒なんで
    ),
    resolvers ++= Seq.apply(
      // akka 2.6.20 を使う為。 2.6.8だとscala-parser-combinatorsの依存が解決できてない
      // see https://akka.io/blog/news/2022/03/21/akka-2.6.19-released
      "mvnrepository" at "https://mvnrepository.com/artifact/"
    ),
    // scalafixで追加 -->
    semanticdbEnabled := true,
    scalafixOnCompile := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    // scalafixで追加 <--
    libraryDependencies ++= Seq(Modules.CatsCore, Modules.TypeSafe.Config, Modules.Enumeratum),
    Test / javaOptions ++= Seq(
      "-Duser.timezone=UTC"
    ),
    Test / parallelExecution := true, // 明示的にtrueにしてる
    Test / fork              := true  // Test / javaOptions が効かないのでtrue
  )
  val TestSettings: SettingsDefinition = Seq(
    libraryDependencies ++= Seq(Modules.ScalaTest % Test, Modules.LogbackClassic)
  )

  object Modules {
    // Core
    val GoogleDiff: ModuleID =
      "com.googlecode.java-diff-utils" % "diffutils" % "1.3.0"

    // RDS関係
//    val skinny      = "4.0.0"
//    val scalikeJDBC = "4.0.0"

    val Mysql: ModuleID      = "mysql"                 % "mysql-connector-java" % "8.0.30"
    val SkinnyOrm: ModuleID  = "org.skinny-framework" %% "skinny-orm"           % "4.0.0"
    val CatsCore: ModuleID   = "org.typelevel"        %% "cats-core"            % "2.8.0"
    val Enumeratum: ModuleID = "com.beachape"         %% "enumeratum"           % "1.7.0"
    val ScalaTest: ModuleID  = "org.scalatest"        %% "scalatest"            % "3.2.12"

    val LogbackClassic: ModuleID = "ch.qos.logback" % "logback-classic" % "1.4.0"

    val Hashids: ModuleID = "org.hashids" % "hashids" % "1.0.3"

    object Scalikejdbc {
      val MapperGeneratorCore: ModuleID =
        "org.scalikejdbc" %% "scalikejdbc-mapper-generator-core" % "4.0.0"
      val Test: ModuleID = "org.scalikejdbc" %% "scalikejdbc-test" % "4.0.0"
    }

    private val CirceVersion = "0.14.2"
    val CirceLibs: Seq[ModuleID] = Seq(
      "circe-core",
      "circe-generic",
      "circe-parser"
    ).map("io.circe" %% _ % CirceVersion)

    val AkkaHttpCirce: ModuleID = "de.heikoseeberger" %% "akka-http-circe" % "1.39.2"

    object TypeSafe {
      val Config: ModuleID   = "com.typesafe"       % "config"    % "1.4.2"
      val AkkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % "10.2.10"

      private val AkkaVersion = "2.6.20"
      val AkkaLibs: Seq[ModuleID] = Seq(
        "akka-actor-typed",
        "akka-stream"
      ).map("com.typesafe.akka" %% _ % AkkaVersion) ++ Seq(
        "com.typesafe" %% "ssl-config-core" % "0.6.1"
      )

    }
  }

  // サンプル
  //  libraryDependencies ++= {
  //    CrossVersion.partialVersion(scalaVersion.value) match {
  //      case Some((2, 13)) => Seq(
  //        "org.scala-lang" % "scala-reflect" % scalaVersion.value
  //      )
  //      case _ => Seq.empty
  //    }
  //  }

}
