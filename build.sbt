
// Project name (artifact name in Maven)
name := "zk"

// orgnization name (e.g., the package name of the project)
organization := "com.pink"

version := "1.0-SNAPSHOT"
scalaVersion := "2.10.4"
// project description
description := "Treasure Data Project"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

// library dependencies. (orginization name) % (project name) % (version)

libraryDependencies ++= {
  val curatorVer =  "2.7.1"
  Seq(
    "org.apache.curator" % "curator-framework" % curatorVer,
    "org.apache.curator" % "curator-recipes"      % curatorVer,
    "org.apache.zookeeper" % "zookeeper" % "3.4.6"
  )
}



assemblyMergeStrategy in assembly := { 
	  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
	  case PathList("javax", "transaction", xs@_*) => MergeStrategy.first
	  case PathList("javax", "mail", xs@_*) => MergeStrategy.first
	  case PathList("javax", "activation", xs@_*) => MergeStrategy.first
	  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
	  case "application.conf" => MergeStrategy.concat
	  case "unwanted.txt" => MergeStrategy.discard
	  case x =>  val oldStrategy = (assemblyMergeStrategy in assembly).value
    		oldStrategy(x)
}


//To skip the test during assembly,
test in assembly := {}

//scalacOptions ++= Seq("-encoding", "UTF-8")
javacOptions ++= Seq("-encoding", "UTF-8")