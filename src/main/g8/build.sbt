import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "$name$"

version := "$version$"

ThisBuild / scalaVersion := "$scala_version$"

lazy val root =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("."))
    .settings(
      Seq(
        libraryDependencies ++= List(
          "eu.joaocosta" %%% "minart" % "0.4.0"
        )
      )
    )
    .jsSettings(
      Seq(
        scalaJSUseMainModuleInitializer := true
      )
    )
    .nativeSettings(
      Seq(
        nativeLinkStubs := true,
        nativeMode      := "release",
        nativeLTO       := "thin",
        nativeGC        := "immix"
      )
    )
    .settings(name := "$name$ Root")
