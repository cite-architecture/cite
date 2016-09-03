// Needed for specs2
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
// Repository for
resolvers += "uh-nexus" at "http://beta.hpcc.uh.edu/nexus/content/groups/public"

libraryDependencies += "edu.harvard.chs" % "cite" % "3.1.0"

//libraryDependencies += "edu.unc.epidoc" % "transcoder" % "1.2-SNAPSHOT"

libraryDependencies += "com.lihaoyi" % "ammonite" % "0.7.4" % "test" cross CrossVersion.full

libraryDependencies += "com.lihaoyi" %% "ammonite-ops" % "0.7.4"


libraryDependencies += "org.specs2" %% "specs2-core" % "3.6.5" % "test"

scalacOptions in Test ++= Seq("-Yrangepos")

initialCommands in (Test, console) := """ammonite.Main().run()"""

scalaVersion := "2.11.8"

name := "cite"

version := "0.9.0"
