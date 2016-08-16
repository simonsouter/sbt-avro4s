resolvers +=  "Ludington Snapshots" at "https://artifactory.hub.bitbrew.com/artifactory/libs-snapshot"
resolvers +=  "Ludington Releases" at "https://artifactory.hub.bitbrew.com/artifactory/libs-release"
resolvers += Classpaths.sbtPluginReleases

credentials += Credentials(Path.userHome / ".artifactory" / ".credentials")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

libraryDependencies <+= (sbtVersion) { sv =>
  "org.scala-sbt" % "scripted-plugin" % sv
}
