name := "BaconAnalysis"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
    // Spark dependency
    "org.apache.spark"  % "spark-core_2.10" % "1.6.1" % "provided",
    "org.apache.spark"  % "spark-sql_2.10" % "1.6.1" % "provided"
)

libraryDependencies += "com.databricks" %% "spark-avro" % "2.0.1"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.4.0"
