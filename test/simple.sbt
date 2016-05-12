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

initialCommands in console += """
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

val conf = new SparkConf().setMaster("local").setAppName("shell")

conf.registerKryoClasses(Array(classOf[Events], classOf[baconhep.TAddJet], classOf[baconhep.TElectron], classOf[baconhep.TEventInfo], classOf[baconhep.TGenEventInfo], classOf[baconhep.TGenParticle], classOf[baconhep.TJet], classOf[baconhep.TMuon], classOf[baconhep.TPhoton], classOf[baconhep.TTau], classOf[baconhep.TVertex]))

val sc = new SparkContext(conf)
"""
