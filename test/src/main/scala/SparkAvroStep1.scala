import scopt.OptionParser

import org.apache.spark.{SparkConf, SparkContext, SparkFiles}
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._


object SparkAvroStep1 {

  case class Params(inputFile: String = null, outputFile: String = null, muPtCut: Double = 10.0)
    extends AbstractParams[Params]

  def main(args: Array[String]) {

    val t0 = System.nanoTime()

    val defaultParams = Params()

    val parser = new OptionParser[Params]("SparkAvroStep1") {
      head("SparkAvroStep1: collections are flattened with flatMap transformation, and the event and muon selection is applied. Filterted datasets are saved in Parquet columnar format to an intermediate HDFS sink.") 
      opt[Double]("muPtCut")
        .required()
        .text(s"Muon pT cut: pass a minimum value of muon transverse momentum in GeV")
        .action((x, c) => c.copy(muPtCut = x))
      arg[String]("<inputFile>")
        .required()
        .text(s"input file, one JSON per line, located in NFS by default (file://)")
        .action((x, c) => c.copy(inputFile = x))
      arg[String]("<outputFile>")
        .required()
        .text(s"output file, Parquet columnar format, stored in HDFS by default")
        .action((x, c) => c.copy(outputFile = x))
      note(
        """
          |For example, the following command runs this app on a dataset:
          |
          | spark-submit --class "SparkAvroStep1" \ 
          | --master yarn-client --num-executors 20 --executor-memory 3g \
          | target/scala-2.10/BaconAnalysis-assembly-1.0.jar 
          | --muPtCut 10.0 /user/alexeys/HEP/QCD_HT1500to2000_13TeV_2/ /user/alexeys/HEPoutput/QCD_HT1500to2000_13TeV_2/
        """.stripMargin)
    }

    parser.parse(args, defaultParams).map { params =>
      run(params)
    } getOrElse {
      System.exit(1)
    }

    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/1000000000 + "s")
  }


  def run(params: Params) {
    val conf = new SparkConf().setAppName("SparkAvroStep1")
      //.set("spark.driver.maxResultSize", "10g")
      //.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      //.set("spark.kryoserializer.buffer.mb","24") 

    val spark = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(spark)
    import sqlContext.implicits._

    //custom schema: read only what you need
    val jsonSchema = sqlContext.read.json(spark.parallelize(Array("""{"Muon": [{"pt": 93.9306, "eta": -1.02724}]}""")))

    val input = sqlContext.read.format("json").schema(jsonSchema.schema).load("file://"+params.inputFile).as[MuWrapper].cache()
    input.printSchema()
    //input.show(10)
    println(input.count())

    val muons_selected = input.flatMap(muon => muon.Muon).
                               filter(muon => (muon.eta < 2.4) & (muon.pt > params.muPtCut))
    println(muons_selected.count())

    muons_selected.toDF().write.parquet("file://"+params.outputFile)
  }
}

case class Mu(eta: Double,pt:Double)
case class MuWrapper(Muon: Array[Mu])
