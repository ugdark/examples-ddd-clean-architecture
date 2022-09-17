import sbt.Keys._
import sbt._
import scalafix.sbt.ScalafixPlugin.autoImport.{scalafixOnCompile, scalafixSemanticdb}

import java.time.format.DateTimeFormatter
import java.time.{ZoneId, ZonedDateTime}

object Dependencies {

  val timestamp: String =
    ZonedDateTime
      .now(ZoneId.of("Asia/Tokyo"))
      .format(DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss"))

  // Disable ScalaDoc
  val disableScalaDocSettings: SettingsDefinition =
    Seq(Compile / doc / sources := Seq.empty, Compile / packageDoc / publishArtifact := false)
  val commonSettings: SettingsDefinition = Seq(
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
    // scalafixで追加 -->
    semanticdbEnabled := true,
    scalafixOnCompile := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    // scalafixで追加 <--
    libraryDependencies ++= Seq(Modules.CatsCore, Modules.typeSafe.config, Modules.Enumeratum),
    Test / javaOptions ++= Seq(
      "-Duser.timezone=UTC"
    ),
    Test / parallelExecution := true, // 明示的にtrueにしてる
    Test / fork              := true  // Test / javaOptions が効かないのでtrue
  )
  val testSettings: SettingsDefinition = Seq(
    libraryDependencies ++= Seq(Modules.ScalaTest % Test, Modules.LogbackClassic)
  )

  object Modules {
    // Core
    val googleDiff: ModuleID =
      "com.googlecode.java-diff-utils" % "diffutils" % "1.3.0"

    // RDS関係
//    val skinny      = "4.0.0"
//    val scalikeJDBC = "4.0.0"

    val mysql: ModuleID         = "mysql"                 % "mysql-connector-java" % "8.0.30"
    val skinnyOrm: ModuleID     = "org.skinny-framework" %% "skinny-orm"           % "4.0.0"
    val AkkaHttpCirce: ModuleID = "de.heikoseeberger"    %% "akka-http-circe"      % "1.39.2"
    val CatsCore: ModuleID      = "org.typelevel"        %% "cats-core"            % "2.8.0"
    val Enumeratum: ModuleID    = "com.beachape"         %% "enumeratum"           % "1.7.0"
    val ScalaTest: ModuleID     = "org.scalatest"        %% "scalatest"            % "3.2.12"
    val LogbackClassic: ModuleID =
      "ch.qos.logback" % "logback-classic" % "1.4.0"
    val hashids: ModuleID = "org.hashids" % "hashids" % "1.0.3"

    object scalikejdbc {
      val mapperGeneratorCore: ModuleID =
        "org.scalikejdbc" %% "scalikejdbc-mapper-generator-core" % "4.0.0"
      val test: ModuleID = "org.scalikejdbc" %% "scalikejdbc-test" % "4.0.0"
    }

    object typeSafe {
      val config: ModuleID   = "com.typesafe"       % "config"    % "1.4.2"
      val AkkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % "10.2.10"
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
