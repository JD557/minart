import ReleaseTransformations._
import scala.scalanative.build._

lazy val scala3Version = "3.3.7"

name := "minart"

organization := "eu.joaocosta"
publishTo    := {
  val centralSnapshots = "https://central.sonatype.com/repository/maven-snapshots/"
  if (isSnapshot.value) Some("central-snapshots" at centralSnapshots)
  else localStaging.value
}
scalaVersion := scala3Version
licenses     := Seq("MIT License" -> uri("http://opensource.org/licenses/MIT"))
homepage     := Some(uri("https://github.com/JD557/minart"))
scmInfo      := Some(
  ScmInfo(
    uri("https://github.com/JD557/minart"),
    "scm:git@github.com:JD557/minart.git"
  )
)
versionScheme := Some("semver-spec")

autoAPIMappings := true
scalacOptions ++= Seq(
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
scalafmtOnCompile := true
semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision
scalafixOnCompile := true
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

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
    "org.scalameta" %% "munit" % "1.3.2" % Test
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
    "org.scala-js" %% "scalajs-dom" % "2.8.1"
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

lazy val core =
  (projectMatrix in (file("core")))
    .settings(name := "minart-core")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart"))
    .settings(siteSettings)
    .jvmPlatform(scalaVersions = Seq(scala3Version))
    .jsPlatform(scalaVersions = Seq(scala3Version), settings = jsSettings)
    .nativePlatform(scalaVersions = Seq(scala3Version), settings = nativeSettings)

lazy val backend =
  (projectMatrix in (file("backend")))
    .dependsOn(core)
    .settings(name := "minart-backend")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart Backend"))
    .jvmPlatform(scalaVersions = Seq(scala3Version))
    .jsPlatform(scalaVersions = Seq(scala3Version), settings = jsSettings)
    .nativePlatform(scalaVersions = Seq(scala3Version), settings = nativeSettings)

lazy val image =
  (projectMatrix in (file("image")))
    .dependsOn(core, backend % "test")
    .settings(name := "minart-image")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart Image"))
    .jvmPlatform(scalaVersions = Seq(scala3Version))
    .jsPlatform(scalaVersions = Seq(scala3Version), settings = jsSettings)
    .nativePlatform(scalaVersions = Seq(scala3Version), settings = nativeSettings)

lazy val sound =
  (projectMatrix in (file("sound")))
    .dependsOn(core, backend % "test")
    .settings(name := "minart-sound")
    .settings(sharedSettings)
    .settings(testSettings)
    .settings(publishSettings)
    .settings(docSettings("Minart Sound"))
    .jvmPlatform(scalaVersions = Seq(scala3Version))
    .jsPlatform(scalaVersions = Seq(scala3Version), settings = jsSettings)
    .nativePlatform(scalaVersions = Seq(scala3Version), settings = nativeSettings)

releaseCrossBuild    := true
releaseTagComment    := s"Release ${(version).value}"
releaseCommitMessage := s"Set version to ${(version).value}"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("publishSigned"),
  releaseStepCommand("sonaRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
