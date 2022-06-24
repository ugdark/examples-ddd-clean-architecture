import sbt.Keys._
import sbt.{Def, _}
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
  lazy val disableScalaDocSettings = Seq(
    Compile / doc / sources := Seq.empty,
    Compile / packageDoc / publishArtifact := false
  )

  object Versions {
    val scala2 = "2.13.8"
    val scala3 = "3.1.2"

    val typeSafeConfig = "1.4.2"

    val logbackClassic = "1.2.11"
    val enumeratum     = "1.7.0" // enumの拡張

    val scalaTest = "3.2.12"

    val cats = "2.7.0"
  }

  lazy val commonSettings = Seq(
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
      )
  )

  lazy val testSettings: Seq[Def.Setting[_ >: Seq[ModuleID] with Task[Seq[String]] <: Equals]] = Seq(
    libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest"       % Versions.scalaTest % Test,
        "ch.qos.logback" % "logback-classic" % Versions.logbackClassic
      ),
    Test / javaOptions ++= Seq(
        "-Duser.timezone=GMT"
      )
  )

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
