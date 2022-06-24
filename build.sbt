import Dependencies.{Versions, commonSettings, testSettings}

//crossScalaVersions := Seq(
//  Versions.scala2,
//  Versions.scala3
//)

val compileAndTest: String = "compile->compile;test->test"

// 責務: 業務を持たない。例：他のPJでも使えるような物
lazy val infrastructure = project
  .in(file("modules/infrastructure"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    idePackagePrefix := Some("com.example.infrastructure")
  )

// 責務: 業務を担う
lazy val domain = project
  .in(file("modules/domain"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    idePackagePrefix := Some("com.example.domain")
  )
  .dependsOn(infrastructure % compileAndTest)

lazy val adaptersDB = project
  .in(file("modules/adaptors/db"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    idePackagePrefix := Some("com.example.adaptors.db")
  )
  .dependsOn(domain % compileAndTest)

lazy val adaptersAPI = project
  .in(file("modules/adaptors/api"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    idePackagePrefix := Some("com.example.adaptors.api")
  )
  .dependsOn(domain % compileAndTest)


lazy val docs = (project in file("docs"))
  .enablePlugins(ParadoxPlugin)

lazy val aggregatedProjects = Seq[ProjectReference](
  infrastructure,
  domain,
  adaptersDB,
  adaptersAPI
)

lazy val root = (project in file("."))
  .settings(
    name := "examples-ddd-clean-architecture",
    Global / excludeLintKeys += idePackagePrefix
  )
  .aggregate(aggregatedProjects: _*)

//lazy val example = project
//  .in(file("example"))
//  .settings(
//    scalaVersion := "2.13.6",
//    libraryDependencies ++= Seq(
//      "org.scala-lang" % "scala-reflect" % scalaVersion.value
//    )
//  )
//
