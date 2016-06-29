name := "kind-projector"
organization := "org.spire-math"
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
homepage := Some(url("http://github.com/non/kind-projector"))

resolvers ++= Seq(
  "Scala 2.12.0-M5 Core" at "https://oss.sonatype.org/content/repositories/orgscala-lang-1322/",
  "Scala 2.12.0-M5 Modules" at "https://oss.sonatype.org/content/repositories/orgscala-lang-1323/"
)

scalaVersion := "2.12.0-M5"
crossScalaVersions := Seq("2.10.6", "2.11.7", "2.12.0-M4")

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

// scalac options

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Xlint",
  "-feature",
  "-language:higherKinds",
  "-deprecation",
  "-unchecked"
)

scalacOptions in console in Compile += "-Xplugin:" + (packageBin in Compile).value

scalacOptions in Test += "-Xplugin:" + (packageBin in Compile).value

scalacOptions in Test += "-Yrangepos"

// Useful for debugging:
// scalacOptions in Test ++= Seq("-Xprint:typer", "-Xprint-pos")

// release stuff
import ReleaseTransformations._

releaseCrossBuild := true
releasePublishArtifactsAction := PgpKeys.publishSigned.value
publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := Function.const(false)

publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)

pomExtra := (
  <scm>
    <url>git@github.com:non/kind-projector.git</url>
    <connection>scm:git:git@github.com:non/kind-projector.git</connection>
  </scm>
  <developers>
    <developer>
      <id>d_m</id>
      <name>Erik Osheim</name>
      <url>http://github.com/non/</url>
    </developer>
  </developers>
)

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
  pushChanges)
