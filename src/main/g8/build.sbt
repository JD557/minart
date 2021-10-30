import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "$name$"

version := "$version$"

scalaVersion := "$scala_version$"

lazy val root =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("."))
    .settings(
      Seq(
        scalaVersion := "$scala_version$",
        libraryDependencies ++= List(
          $if(pure.truthy) $
            "eu.joaocosta" %%% "minart-core" % "0.3.0",
          "eu.joaocosta"   %%% "minart-pure" % "0.3.0"
            $else$
            "eu.joaocosta" %%% "minart-core" % "0.3.0" $endif$
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
