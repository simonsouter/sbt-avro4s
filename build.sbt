import sbt._
import sbt.Keys._

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

    publishTo <<= version {
      (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },

    pomExtra := {
      <url>https://github.com/sksamuel/sbt-avro4s</url>
        <licenses>
          <license>
            <name>MIT</name>
            <url>http://opensource.org/licenses/MIT</url>
            <distribution>repo</distribution>
          </license>
        </licenses>
        <scm>
          <url>git@github.com:sksamuel/sbt-avro4s.git</url>
          <connection>scm:git@github.com:sksamuel/sbt-avro4s.git</connection>
        </scm>
        <developers>
          <developer>
            <id>sksamuel</id>
            <name>sksamuel</name>
            <url>http://github.com/sksamuel</url>
          </developer>
        </developers>
    }
  )
