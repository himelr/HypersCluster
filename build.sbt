name := "hyperscluster"

version := "1.0"

scalaVersion := "2.12.5"

val akkaVersion = "2.5.0"

unmanagedJars in Compile += file("mysql-connector-java-5.1.16.jar")

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.11",
    "com.typesafe.akka" %% "akka-agent" % "2.5.11",
    "com.typesafe.akka" %% "akka-camel" % "2.5.11",
    "com.typesafe.akka" %% "akka-cluster" % "2.5.11",
    "com.typesafe.akka" %% "akka-cluster-metrics" % "2.5.11",
    "com.typesafe.akka" %% "akka-cluster-sharding" % "2.5.11",
    "com.typesafe.akka" %% "akka-cluster-tools" % "2.5.11",
    "com.typesafe.akka" %% "akka-distributed-data" % "2.5.11",
    "com.typesafe.akka" %% "akka-multi-node-testkit" % "2.5.11",
    "com.typesafe.akka" %% "akka-persistence" % "2.5.11",
    "com.typesafe.akka" %% "akka-persistence-query" % "2.5.11",
    "com.typesafe.akka" %% "akka-persistence-tck" % "2.5.11",
    "com.typesafe.akka" %% "akka-remote" % "2.5.11",
    "com.typesafe.akka" %% "akka-slf4j" % "2.5.11",
    "com.typesafe.akka" %% "akka-stream" % "2.5.11",
    "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.11",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.11",
    "org.iq80.leveldb"            % "leveldb"          % "0.7",
    "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8",
    "com.typesafe.akka" %% "akka-contrib" % "2.5.11",
    "mysql" % "mysql-connector-java" % "5.1.16"

  )

}
