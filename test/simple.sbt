name := "BaconAnalysis"

version := "1.0"

scalaVersion := "2.10.5"

//resolvers += Resolver.mavenLocal
resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

libraryDependencies ++= Seq(
    // Spark dependency
    "org.apache.spark"  % "spark-core_2.10" % "1.6.1" % "provided",
    "org.apache.spark"  % "spark-sql_2.10" % "1.6.1" % "provided"
)

libraryDependencies += "com.databricks" %% "spark-avro" % "2.0.1"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.4.0"

libraryDependencies += "org.scalamacros" % "quasiquotes_2.10" % "2.1.0"
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "org.diana-hep" % "histogrammar" % "0.9-prerelease"

//libraryDependencies += "org.json4s" %% "json4s-native" % "3.2.4"

initialCommands in console += """
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.avro.mapred.AvroKey
import org.apache.hadoop.io.NullWritable
import scala.collection.JavaConversions._
import MakeRow._

import org.dianahep.histogrammar._
import org.dianahep.histogrammar.json._

//import sqlContext.implicits._
//import org.json4s._
//import org.json4s.native.JsonMethods._

//val conf = new SparkConf().setMaster("local").setAppName("shell")
val conf = new SparkConf().setMaster("yarn-client").setAppName("shell")

conf.registerKryoClasses(Array(classOf[Events], classOf[baconhep.TAddJet], classOf[baconhep.TElectron], classOf[baconhep.TEventInfo], classOf[baconhep.TGenEventInfo], classOf[baconhep.TGenParticle], classOf[baconhep.TJet], classOf[baconhep.TMuon], classOf[baconhep.TPhoton], classOf[baconhep.TTau], classOf[baconhep.TVertex]))

val sc = new SparkContext(conf)
val sqlContext = new org.apache.spark.sql.SQLContext(sc)
import sqlContext.implicits._

def makeAvroRDD(hdfsFileName: String) =
    sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]](hdfsFileName).map(_._1.datum)
"""
