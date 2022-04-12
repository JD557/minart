import ReleaseTransformations._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "minart"

ThisBuild / organization := "eu.joaocosta"
ThisBuild / publishTo    := sonatypePublishToBundle.value
ThisBuild / scalaVersion := "3.1.2"
ThisBuild / licenses     := Seq("MIT License" -> url("http://opensource.org/licenses/MIT"))
ThisBuild / homepage     := Some(url("https://github.com/JD557/minart"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/JD557/minart"),
    "scm:git@github.com:JD557/minart.git"
  )
)
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

val sharedSettings = Seq(
  organization       := "eu.joaocosta",
  scalaVersion       := "3.1.2",
  crossScalaVersions := Seq("2.11.12", "2.12.15", "2.13.8", "3.1.2"),
  licenses           := Seq("MIT License" -> url("http://opensource.org/licenses/MIT")),
  homepage           := Some(url("https://github.com/JD557/minart")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/JD557/minart"),
      "scm:git@github.com:JD557/minart.git"
    )
  ),
  autoAPIMappings := true,
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-language:higherKinds",
    "-unchecked"
  ),
  scalafmtOnCompile := true,
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
  scalafixOnCompile := true,
  libraryDependencies ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n <= 12 =>
        Seq("org.scala-lang.modules" %%% "scala-collection-compat" % "2.7.0")
      case _ => Nil
    }
  }
)

val testSettings = Seq(
  libraryDependencies ++= Seq(
    "com.eed3si9n.verify" %%% "verify" % "1.0.0" % Test
  ),
  testFrameworks += new TestFramework("verify.runner.Framework"),
  Test / scalacOptions ++= Seq("-Yrangepos")
)

val noTestSettings = Seq(
  test        := (()),
  Test / test := (())
)

val publishSettings = Seq(
  publishMavenStyle      := true,
  Test / publishArtifact := false,
  pomIncludeRepository   := { _ => false }
)

val noPublishSettings = Seq(
  publish / skip  := true,
  publish         := (()),
  publishLocal    := (()),
  publishArtifact := false,
  publishTo       := None
)

val jsSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "2.1.0"
  )
)

val nativeSettings = Seq(
  nativeLinkStubs      := true,
  Compile / nativeMode := "release",
  Test / nativeMode    := "debug",
  nativeLTO            := "thin"
)

lazy val root =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("."))
    .settings(sharedSettings)
    .settings(name := "minart")
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)
    .dependsOn(core, pure)
    .aggregate(core, pure)

lazy val core =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .settings(sharedSettings)
    .settings(name := "minart-core")
    .settings(testSettings)
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val pure =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .dependsOn(core)
    .settings(sharedSettings)
    .settings(name := "minart-pure")
    .settings(testSettings)
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val examples = (project in file("examples"))
  .settings(sharedSettings)
  .settings(name := "minart-examples")
  .settings(noTestSettings)
  .settings(noPublishSettings)
  .aggregate(
    Seq(
      `examples-blitting`.componentProjects,
      `examples-colorSquare`.componentProjects,
      `examples-fire`.componentProjects,
      `examples-mousePointer`.componentProjects,
      `examples-pureColorSquare`.componentProjects,
      `examples-settings`.componentProjects,
      `examples-snake`.componentProjects
    ).flatten.map(_.project): _*
  )

def example(project: sbtcrossproject.CrossProject.Builder, exampleName: String) = {
  project
    .in(file(s"examples/${exampleName}"))
    .dependsOn(core)
    .settings(sharedSettings)
    .settings(name := s"minart-examples-${exampleName}")
    .settings(noTestSettings)
    .settings(noPublishSettings)
    .jsSettings(jsSettings)
    .jsSettings(scalaJSUseMainModuleInitializer := true)
    .nativeSettings(nativeSettings)
}

lazy val `examples-blitting` =
  example(crossProject(JVMPlatform, JSPlatform, NativePlatform), "blitting")

lazy val `examples-colorSquare` =
  example(crossProject(JVMPlatform, JSPlatform, NativePlatform), "colorsquare")

lazy val `examples-fire` =
  example(crossProject(JVMPlatform, JSPlatform, NativePlatform), "fire")

lazy val `examples-mousePointer` =
  example(crossProject(JVMPlatform, JSPlatform, NativePlatform), "mousepointer")

lazy val `examples-pureColorSquare` =
  example(crossProject(JVMPlatform, JSPlatform, NativePlatform), "purecolorsquare").dependsOn(pure)

lazy val `examples-settings` =
  example(crossProject(JVMPlatform, JSPlatform, NativePlatform), "settings")

lazy val `examples-snake` =
  example(crossProject(JVMPlatform, JSPlatform, NativePlatform), "snake")

releaseCrossBuild    := true
releaseTagComment    := s"Release ${(ThisBuild / version).value}"
releaseCommitMessage := s"Set version to ${(ThisBuild / version).value}"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
