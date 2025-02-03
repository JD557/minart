import ReleaseTransformations._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}
import scala.scalanative.build._

name := "minart"

ThisBuild / organization := "eu.joaocosta"
ThisBuild / publishTo    := sonatypePublishToBundle.value
ThisBuild / scalaVersion := "3.3.5"
ThisBuild / licenses     := Seq("MIT License" -> url("http://opensource.org/licenses/MIT"))
ThisBuild / homepage     := Some(url("https://github.com/JD557/minart"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/JD557/minart"),
    "scm:git@github.com:JD557/minart.git"
  )
)
ThisBuild / versionScheme := Some("semver-spec")

ThisBuild / autoAPIMappings := true
ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:higherKinds",
  "-Wunused:implicits",
  "-Wunused:explicits",
  "-Wunused:imports",
  "-Wunused:locals",
  "-Wunused:params",
  "-Wunused:privates"
  // "-Xfatal-warnings"
)
ThisBuild / scalafmtOnCompile := true
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / scalafixOnCompile := true
ThisBuild / resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

Global / concurrentRestrictions += Tags.limit(
  NativeTags.Link,
  1
) // See https://github.com/scala-native/scala-native/issues/2024

val siteSettings = Seq(
  Compile / doc / scalacOptions ++= Seq("-siteroot", "docs")
)

def docSettings(projectName: String) = Seq(
  Compile / doc / scalacOptions ++= Seq(
    "-project",
    projectName,
    "-project-version",
    version.value,
    "-social-links:" +
      "github::https://github.com/JD557/Minart"
  )
)

val sharedSettings = Seq()

val testSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalameta" %%% "munit" % "1.1.0" % Test // Bootlegged
  ),
  testFrameworks += new TestFramework("munit.Framework")
)

val publishSettings = Seq(
  publishMavenStyle      := true,
  Test / publishArtifact := false,
  pomIncludeRepository   := { _ => false }
)

val jsSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "2.8.0"
  )
)

val nativeSettings = Seq(
  Compile / nativeConfig ~= {
    _.withMode(Mode.releaseFull)
      .withLinkStubs(true)
      .withLTO(LTO.thin)
  },
  Test / nativeConfig ~= {
    _.withMode(Mode.debug)
      .withLinkStubs(true)
      .withLTO(LTO.none)
      .withEmbedResources(true)
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
    .dependsOn(core, backend, image, sound)
    .aggregate(core, backend, image, sound)

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
  releaseStepCommandAndRemaining("publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
