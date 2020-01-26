import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}
import ReleaseTransformations._

name := "minart"

organization := "eu.joaocosta"

val sharedSettings = Seq(
  organization := "eu.joaocosta",
  scalaVersion := "2.13.1",
  crossScalaVersions := Seq("2.12.10", "2.13.1")
)

lazy val core =
  crossProject(JVMPlatform, JSPlatform)
    .settings(sharedSettings)
    .settings(name := "minart-core")
    .jsSettings(Seq(
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.7"
      )))

lazy val examples =
  crossProject(JVMPlatform, JSPlatform)
    .dependsOn(core)
    .settings(sharedSettings)
    .settings(name := "minart-examples")
    .settings(
      skip in publish := true,
      publish := (()),
      publishLocal := (()),
      publishArtifact := false,
      publishTo := None
    )
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
