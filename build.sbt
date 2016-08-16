import sbt._
import sbt.Keys._
import ludington.sbt.PublishSettings

lazy val commonSettings = Seq(
  scalaVersion := "2.10.5",
  version in ThisBuild := "1.0.0-SNAPSHOT",
  organization := "com.sksamuel.avro4s",
  scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8"),
  javacOptions ++= Seq("-source", "1.7", "-target", "1.7")
)

lazy val root = (project in file(".")).
  settings(ScriptedPlugin.scriptedSettings).
  settings(commonSettings: _*).
  settings(
    name := "sbt-avro4s",
    sbtPlugin := true,
    licenses += ("MIT", url("https://opensource.org/licenses/MIT")),
    libraryDependencies ++= Seq(
      "com.sksamuel.avro4s" %% "avro4s-generator" % "1.5.6-SNAPSHOT"
    ),
    publishMavenStyle := true,

    publishArtifact in Test := false,
    parallelExecution in Test := false,
    scriptedLaunchOpts := { scriptedLaunchOpts.value ++ Seq(
      "-Xmx1024M",
      "-XX:MaxPermSize=256M",
      "-Dplugin.version=" + version.value
    )},
    scriptedBufferLog := false,

    resolvers := ("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2") +: resolvers.value,

    PublishSettings.publishSettings
  )
