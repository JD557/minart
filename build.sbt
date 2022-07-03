import ReleaseTransformations._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "minart"

ThisBuild / organization       := "eu.joaocosta"
ThisBuild / publishTo          := sonatypePublishToBundle.value
ThisBuild / scalaVersion       := "3.1.2"
ThisBuild / crossScalaVersions := Seq("2.11.12", "2.12.16", "2.13.8", "3.1.2")
ThisBuild / licenses           := Seq("MIT License" -> url("http://opensource.org/licenses/MIT"))
ThisBuild / homepage           := Some(url("https://github.com/JD557/minart"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/JD557/minart"),
    "scm:git@github.com:JD557/minart.git"
  )
)

ThisBuild / autoAPIMappings := true
ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:higherKinds",
  "-unchecked"
)
ThisBuild / scalafmtOnCompile                              := true
ThisBuild / semanticdbEnabled                              := true
ThisBuild / semanticdbVersion                              := scalafixSemanticdb.revision
ThisBuild / scalafixOnCompile                              := true
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

val sharedSettings = Seq(
  libraryDependencies ++=
    Seq("org.scala-lang.modules" %%% "scala-collection-compat" % "2.7.0")
)

val testSettings = Seq(
  libraryDependencies ++= Seq(
    "com.eed3si9n.verify" %%% "verify" % "1.0.0" % Test
  ),
  testFrameworks += new TestFramework("verify.runner.Framework"),
  Test / scalacOptions ++= Seq("-Yrangepos")
)

val publishSettings = Seq(
  publishMavenStyle      := true,
  Test / publishArtifact := false,
  pomIncludeRepository   := { _ => false }
)

val jsSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %%% "scala-collection-compat" % "2.7.0",
    "org.scala-js"           %%% "scalajs-dom"             % "2.2.0"
  )
)

val nativeSettings = Seq(
  nativeLinkStubs      := true,
  Compile / nativeMode := "release",
  Test / nativeMode    := "debug",
  nativeLTO            := "thin",
  nativeConfig ~= {
    _.withEmbedResources(true)
  }
)

lazy val root =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("."))
    .settings(name := "minart")
    .settings(sharedSettings)
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)
    .dependsOn(core, backend, pure, image)
    .aggregate(core, backend, pure, image)

lazy val core =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .settings(name := "minart-core")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val backend =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .dependsOn(core)
    .settings(name := "minart-backend")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val pure =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .dependsOn(core)
    .settings(name := "minart-pure")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val image =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .dependsOn(core)
    .dependsOn(backend % "test")
    .settings(name := "minart-image")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

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
