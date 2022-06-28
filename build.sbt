import Dependencies.{commonSettings, disableScalaDocSettings, testSettings}

//crossScalaVersions := Seq(
//  Versions.scala2,
//  Versions.scala3
//)

val compileAndTest: String = "compile->compile;test->test"

// 責務: 業務を持たない。例：他のPJでも使えるような物
val infrastructure = project
  .in(file("modules/infrastructure"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    idePackagePrefix := Some("com.example.infrastructure")
  )


// 責務: 業務を担う
val domain = project
  .in(file("modules/domain"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    idePackagePrefix :=
      Some("com.example.domain")
  )
  .dependsOn(infrastructure % compileAndTest)

val adaptersDB = project
  .in(file("modules/adaptors/db"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    idePackagePrefix := Some("com.example.adaptors.db")
  )
  .dependsOn(domain % compileAndTest)

val adaptersAPI = project
  .in(file("modules/adaptors/api"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    idePackagePrefix := Some("com.example.adaptors.api")
  )
  .dependsOn(domain % compileAndTest)

val docs = (project in file("docs"))
  .enablePlugins(ParadoxPlugin)

val aggregatedProjects = Seq[ProjectReference](
  infrastructure,
  domain,
  adaptersDB,
  adaptersAPI
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
