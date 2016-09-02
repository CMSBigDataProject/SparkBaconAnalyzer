name := "BaconAnalysis"

version := "2.0"

scalaVersion := "2.11.8"

//resolvers += Resolver.mavenLocal
resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

libraryDependencies ++= Seq(
    // Spark dependency
    "org.apache.spark"  % "spark-core_2.11" % "2.0.0" % "provided",
    "org.apache.spark"  % "spark-sql_2.11" % "2.0.0" % "provided"
)

libraryDependencies += "org.scalamacros" % "quasiquotes_2.10" % "2.1.0"
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "org.diana-hep" % "histogrammar" % "0.9-prerelease"

//libraryDependencies += "org.json4s" %% "json4s-native" % "3.2.4"

libraryDependencies += "com.typesafe" % "config" % "1.3.0"
