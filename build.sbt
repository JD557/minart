import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}
import ReleaseTransformations._

name := "minart"

organization in ThisBuild := "eu.joaocosta"
publishTo in ThisBuild := sonatypePublishToBundle.value

val sharedSettings = Seq(
  organization := "eu.joaocosta",
  scalaVersion := "2.13.1",
  crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1"),
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

val testSettings = Seq(
  libraryDependencies ++= Seq(
    "org.specs2" %%% "specs2-core" % "4.8.3" % Test
  ),
  scalacOptions in Test ++= Seq("-Yrangepos")
)

val jsSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.7"
  )
)

val nativeSettings = Seq(
  scalaVersion := "2.11.12",
  crossScalaVersions := Seq("2.11.12"),
  libraryDependencies ++= Seq(
    "com.regblanc" %%% "native-sdl2" % "0.1"
  ),
  libraryDependencies --= Seq(
    "org.specs2" %%% "specs2-core" % "4.8.3" % Test
  ),
  nativeLinkStubs := true
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

lazy val root = (project in file("."))
  .settings(sharedSettings)
  .settings(name := "minart")
  .settings(publishSettings)
  .dependsOn(core.jvm, core.js)
  .aggregate(core.jvm, core.js)

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
    .settings(publishSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val examples = (project in file("examples"))
    .dependsOn(
      examples_colorSquare.jvm,     examples_colorSquare.js,     /*examples_colorSquare.native,*/
      examples_pureColorSquare.jvm, examples_pureColorSquare.js, /*examples_pureColorSquare.native,*/
      examples_fire.jvm,            examples_fire.js,            /*examples_fire.native,*/
      examples_snake.jvm,           examples_snake.js/*,           examples_snake.native*/)
    .settings(sharedSettings)
    .settings(name := "minart-examples")
    .settings(noPublishSettings)
    .aggregate(
      examples_colorSquare.jvm,     examples_colorSquare.js,     /*examples_colorSquare.native,*/
      examples_pureColorSquare.jvm, examples_pureColorSquare.js, /*examples_pureColorSquare.native,*/
      examples_fire.jvm,            examples_fire.js,            /*examples_fire.native,*/
      examples_snake.jvm,           examples_snake.js/*,           examples_snake.native*/)

lazy val examples_colorSquare =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("examples/colorsquare"))
    .dependsOn(core)
    .settings(sharedSettings)
    .settings(name := "minart-examples-colorsquare")
    .settings(noPublishSettings)
    .jsSettings(jsSettings)
    .jsSettings(scalaJSUseMainModuleInitializer := true)
    .nativeSettings(nativeSettings)

lazy val examples_pureColorSquare =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("examples/purecolorsquare"))
    .dependsOn(core, pure)
    .settings(sharedSettings)
    .settings(name := "minart-examples-purecolorsquare")
    .settings(noPublishSettings)
    .jsSettings(jsSettings)
    .jsSettings(scalaJSUseMainModuleInitializer := true)
    .nativeSettings(nativeSettings)

lazy val examples_fire =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("examples/fire"))
    .dependsOn(core)
    .settings(sharedSettings)
    .settings(name := "minart-examples-fire")
    .settings(noPublishSettings)
    .jsSettings(jsSettings)
    .jsSettings(scalaJSUseMainModuleInitializer := true)
    .nativeSettings(nativeSettings)

lazy val examples_snake =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("examples/snake"))
    .dependsOn(core)
    .settings(sharedSettings)
    .settings(name := "minart-examples-snake")
    .settings(noPublishSettings)
    .jsSettings(jsSettings)
    .jsSettings(scalaJSUseMainModuleInitializer := true)
    .nativeSettings(nativeSettings)

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
