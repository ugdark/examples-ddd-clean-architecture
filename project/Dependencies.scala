import sbt.Keys._
import sbt._
import sbtide.Keys.idePackagePrefix
import scalafix.sbt.ScalafixPlugin.autoImport.{scalafixOnCompile, scalafixSemanticdb}
//import sbtide.Keys.idePackagePrefix

import java.time.format.DateTimeFormatter
import java.time.{ZoneId, ZonedDateTime}

object Dependencies {

  val timestamp: String =
    ZonedDateTime
      .now(ZoneId.of("Asia/Tokyo"))
      .format(DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss"))

  // Disable ScalaDoc
  val disableScalaDocSettings: SettingsDefinition = Seq(
    Compile / doc / sources := Seq.empty,
    Compile / packageDoc / publishArtifact := false
  )

  object Dependencies {
    // Core
    val googleDiff: ModuleID = "com.googlecode.java-diff-utils" % "diffutils" % "1.3.0"

    // RDS関係
//    val skinny      = "4.0.0"
//    val scalikeJDBC = "4.0.0"

    val mysql: ModuleID     = "mysql"                 % "mysql-connector-java" % "8.0.29"
    val skinnyOrm: ModuleID = "org.skinny-framework" %% "skinny-orm"           % "4.0.0"

    object scalikejdbc {
      val mapperGeneratorCore: ModuleID = "org.scalikejdbc" %% "scalikejdbc-mapper-generator-core" % "4.0.0"
      val test: ModuleID                = "org.scalikejdbc" %% "scalikejdbc-test"                  % "4.0.0"
    }

    object typeSafe {
      val config: ModuleID = "com.typesafe" % "config" % "1.4.2"
    }

  }
  object Versions {
    val scala2 = "2.13.8"
    val scala3 = "3.1.2"

    // Front関係
    val akkaHttp      = "10.2.9"
    val akkaHttpCirce = "1.39.2"

    // RDS関係
    val skinny      = "4.0.0"
    val mysql       = "8.0.29"
    val scalikeJDBC = "4.0.0"

    val typeSafeConfig = "1.4.2"

    val logbackClassic = "1.2.11"
    val enumeratum     = "1.7.0" // enumの拡張

    val scalaTest = "3.2.12"

    val cats = "2.7.0"
  }

  val commonSettings: SettingsDefinition = Seq(
    organization := "com.kokodayo",
    scalaVersion := Versions.scala2,
    version := "1.0.0",
    idePackagePrefix := Some("com.example"),
    scalacOptions := Seq(
        "-deprecation",
        "-feature",
        "-Ywarn-unused",
        "-Xlint:unused"
      ),
    // scalafixで追加 -->
    semanticdbEnabled := true,
    scalafixOnCompile := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    // scalafixで追加 <--
    libraryDependencies ++= Seq(
        "org.typelevel" %% "cats-core"  % Versions.cats,
        "com.typesafe"   % "config"     % Versions.typeSafeConfig,
        "com.beachape"  %% "enumeratum" % Versions.enumeratum
      ),
    Test / javaOptions ++= Seq(
        "-Duser.timezone=GMT"
      )
  )

  val testSettings: SettingsDefinition = Seq(
    libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest"       % Versions.scalaTest % Test,
        "ch.qos.logback" % "logback-classic" % Versions.logbackClassic
      ),
    Test / javaOptions ++= Seq(
        "-Duser.timezone=GMT"
      )
  )

  object Core {
    val settings: SettingsDefinition = commonSettings ++ testSettings ++ Seq(
        idePackagePrefix := Some("com.kokodayo.dodai"),
        libraryDependencies ++= Seq(
            Dependencies.googleDiff,
            Dependencies.typeSafe.config
          )
      )
  }

  object Domain {
    val settings: SettingsDefinition = commonSettings ++ testSettings ++ Seq(
        idePackagePrefix := Some("com.example.domain")
      )
  }

  object UseCase {
    val settings: SettingsDefinition = commonSettings ++ testSettings ++ Seq(
        idePackagePrefix := Some("com.example.usecase")
      )
  }

  object DBs {
    val settings: SettingsDefinition = commonSettings ++ testSettings ++ Seq(
        idePackagePrefix := Some("com.example.adaptors.dbs"),
        libraryDependencies ++= Seq(
            Dependencies.mysql,
            Dependencies.skinnyOrm,
            Dependencies.scalikejdbc.mapperGeneratorCore,
            Dependencies.scalikejdbc.test % Test
          )
      )
  }

  object APIs {
    val settings: SettingsDefinition = commonSettings ++ testSettings ++ Seq(
        idePackagePrefix := Some("com.example.adaptors.apis"),
        libraryDependencies ++= Seq(
            "com.typesafe.akka" %% "akka-http"       % Versions.akkaHttp,
            "de.heikoseeberger" %% "akka-http-circe" % Versions.akkaHttpCirce
          )
      )
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
