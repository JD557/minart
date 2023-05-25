import ReleaseTransformations._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "minart"

ThisBuild / organization       := "eu.joaocosta"
ThisBuild / publishTo          := sonatypePublishToBundle.value
ThisBuild / scalaVersion       := "3.2.2"
ThisBuild / crossScalaVersions := Seq("2.12.17", "2.13.10", "3.2.2")
ThisBuild / licenses           := Seq("MIT License" -> url("http://opensource.org/licenses/MIT"))
ThisBuild / homepage           := Some(url("https://github.com/JD557/minart"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/JD557/minart"),
    "scm:git@github.com:JD557/minart.git"
  )
)
ThisBuild / versionScheme := Some("semver-spec")

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

val siteSettings = Seq(
  Compile / doc / scalacOptions ++= (
    if (scalaBinaryVersion.value.startsWith("3"))
      Seq("-siteroot", "docs")
    else Seq()
  )
)

def docSettings(projectName: String) = Seq(
  Compile / doc / scalacOptions ++= (
    if (scalaBinaryVersion.value.startsWith("3"))
      Seq(
        "-project",
        projectName,
        "-project-version",
        version.value,
        "-social-links:" +
          "github::https://github.com/JD557/Minart"
      )
    else Seq()
  )
)

val sharedSettings = Seq(
  libraryDependencies ++=
    Seq("org.scala-lang.modules" %%% "scala-collection-compat" % "2.10.0")
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
    "org.scala-lang.modules" %%% "scala-collection-compat" % "2.10.0",
    "org.scala-js"           %%% "scalajs-dom"             % "2.6.0"
  )
)

val nativeSettings = Seq(
  nativeLinkStubs      := true,
  Compile / nativeMode := "release",
  Test / nativeMode    := "debug",
  Compile / nativeLTO  := "thin",
  Test / nativeLTO     := "none",
  Test / nativeConfig ~= {
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
    .dependsOn(core, backend, pure, image, sound)
    .aggregate(core, backend, pure, image, sound)

lazy val core =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .settings(name := "minart-core")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart"))
    .settings(siteSettings)
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val backend =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .dependsOn(core)
    .settings(name := "minart-backend")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart Backend"))
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val pure =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .dependsOn(core)
    .settings(name := "minart-pure")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart Pure"))
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val image =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .dependsOn(core, backend % "test")
    .settings(name := "minart-image")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart Image"))
    .jsSettings(jsSettings)
    .nativeSettings(nativeSettings)

lazy val sound =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .dependsOn(core, backend % "test")
    .settings(name := "minart-sound")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart Sound"))
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
