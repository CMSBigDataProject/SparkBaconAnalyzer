import org.apache.avro.mapred.AvroKey
import org.apache.avro.mapreduce.AvroJob
import org.apache.avro.mapreduce.AvroKeyInputFormat
import org.apache.avro.mapreduce.AvroKeyRecordReader
import org.apache.avro.Schema
import org.apache.avro.specific.SpecificData
import org.apache.avro.specific.SpecificDatumReader
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.NullWritable

class MySpecificData(classLoader: java.lang.ClassLoader) extends SpecificData(classLoader)  // no customizations

class MyDatumReader[X] extends SpecificDatumReader[X](new MySpecificData(getClass.getClassLoader)) {
  // load arrays as java.util.List[_]
  override def newArray(old: AnyRef, size: Int, schema: Schema): AnyRef = new java.util.ArrayList[AnyRef]

  // load maps as java.util.Map[String, _]
  override def newMap(old: AnyRef, size: Int): AnyRef = new java.util.HashMap[String, AnyRef]
}

class MyKeyRecordReader[X](readerSchema: Schema, configuration: Configuration) extends AvroKeyRecordReader[X](readerSchema) {
  import org.apache.avro.file.SeekableInput
  import org.apache.avro.file.DataFileReader
  import org.apache.avro.io.DatumReader
  override def createAvroFileReader(input: SeekableInput, datumReader: DatumReader[X]): DataFileReader[X] = {
    // override the generic DatumReader
    val specificReader = new MyDatumReader[X]
    specificReader.setSchema(readerSchema)
    new DataFileReader[X](input, specificReader)
  }
}

class MyKeyInputFormat[X] extends AvroKeyInputFormat[X] {
  import org.apache.hadoop.mapreduce.InputSplit
  import org.apache.hadoop.mapreduce.TaskAttemptContext
  import org.apache.hadoop.mapreduce.RecordReader
  override def createRecordReader(split: InputSplit, context: TaskAttemptContext): RecordReader[AvroKey[X], NullWritable] = {
    // return a subclass of AvroKeyRecordReader to override the generic DatumReader
    val configuration = context.getConfiguration
    val readerSchema = AvroJob.getInputKeySchema(configuration)
    new MyKeyRecordReader[X](readerSchema, configuration)
  }
}
