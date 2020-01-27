import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}
import ReleaseTransformations._

name := "minart"

organization in ThisBuild := "eu.joaocosta"
publishTo in ThisBuild := sonatypePublishToBundle.value

val sharedSettings = Seq(
  organization := "eu.joaocosta",
  scalaVersion := "2.13.1",
  crossScalaVersions := Seq("2.12.10", "2.13.1"),
  licenses := Seq("MIT License" -> url("http://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/JD557/minart")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/JD557/minart"),
      "scm:git@github.com:JD557/minart.git"
    )
  ),
  autoAPIMappings := true
)

val publishSettings = Seq(
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
)

val noPublishSettings = Seq(
  skip in publish := true,
  publish := (()),
  publishLocal := (()),
  publishArtifact := false,
  publishTo := None
)

lazy val root = (crossProject(JVMPlatform, JSPlatform) in file("."))
  .settings(sharedSettings)
  .settings(name := "minart")
  .settings(publishSettings)
  .dependsOn(core)
  .aggregate(core)

lazy val core =
  crossProject(JVMPlatform, JSPlatform)
    .settings(sharedSettings)
    .settings(name := "minart-core")
    .settings(publishSettings)
    .jsSettings(Seq(
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.7"
      )))

lazy val examples =
  crossProject(JVMPlatform, JSPlatform)
    .dependsOn(core)
    .settings(sharedSettings)
    .settings(name := "minart-examples")
    .settings(noPublishSettings)
    .jsSettings(Seq(
      scalaJSUseMainModuleInitializer := true,
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.7"
      )))

releaseCrossBuild := true
releaseTagComment := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

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
  pushChanges)
