package MJSelection.bin

import baconhep._

package object runMonoX {
  class OutputRow(val stuff: Double)

  // Define RDDs for each sample
  def DY(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/DYJetsToLL*/*.avro").map(_._1.datum)
  def QCD(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/QCD_HT*/*.avro").map(_._1.datum)
  def T(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/ST_t*/*.avro").map(_._1.datum)  
  def W(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/WJetsToLNu*/*.avro").map(_._1.datum)
  def WW(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/WW_13TeV_pythia8/*.avro").map(_._1.datum)

  def allSelections(event: Events): List[OutputRow] = {
    List()
  }

}
