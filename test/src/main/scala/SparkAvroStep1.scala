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

    //custom schema: read only what you need
    val jsonSchema = sqlContext.read.json(spark.parallelize(Array("""{"Muon": [{"pt": 93.9306, "eta": -1.02724}]}""")))

    val input = sqlContext.read.format("json").schema(jsonSchema.schema).load("file:///home/alexeys/HEPSparkTests/SparkBaconAnalyzer_Alexeys/test/data/bacon.json").as[MuWrapper].cache()
    input.printSchema()
    //input.show(10)
    println(input.count())

    val muons_selected = input.flatMap(muon => muon.Muon).
                               filter(muon => (muon.eta < 2.4) & (muon.pt > 10.0))
    println(muons_selected.count())

    muons_selected.toDF().write.parquet("/user/alexeys/selected_muons")
  }
}

case class Mu(eta: Double,pt:Double)
case class MuWrapper(Muon: Array[Mu])
