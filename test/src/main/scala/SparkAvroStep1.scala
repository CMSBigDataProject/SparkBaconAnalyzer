import org.apache.spark.{SparkConf, SparkContext, SparkFiles}
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._


object SparkAvroStep1 {

  def main(args: Array[String]) {

    val t0 = System.nanoTime()
    val conf = new SparkConf().setAppName("TextProcessing")
      //.set("spark.driver.maxResultSize", "10g")
      //.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      //.set("spark.kryoserializer.buffer.mb","24") 

    val spark = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(spark)
    import sqlContext.implicits._

    val input = sqlContext.read.json("file:///home/alexeys/HEPSparkTests/SparkBaconAnalyzer/test/data/bacon_muon.json").as[Muon].cache()

    val muons_selected = input.filter(muon => (muon.eta < 2.4) & (muon.pt > 10.0))
    //muons_selected.show()
   
    muons_selected.toDF().write.parquet("/user/alexeys/selected_muons")
  }
}

case class Muon(pt: Double,eta:Double,phi:Double,trkIso:Double,ecalIso:Double,hcalIso:Double,chHadIso:Double,gammaIso:Double,neuHadIso:Double,puIso:Double,d0:Double,dz:Double,pogIDBits: Long)
