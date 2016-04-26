# Description of the analysis workflows

The test folder contains two independent analysis workflows

## Fully ```Python``` based

Bacon ntuple converted to ```JSON``` is ingested into dataframe and transformed/acted on using UDFs. The final result is plotted using Python's ```matplotlib```

To run on BD do:

```bash
spark-submit --master local[5] SparkJsonTest.py
```

## Scala and ```Python``` based

On the fiest step, a bacon ```JSON``` or ```Avro``` is ingested into dataset. The event and muon selection is applied and the filterted muon
dataset is saved into ```Parquet``` to an intermediate ```HDFS``` sink. 

To run the first step on BD:

```bash
spark-submit --class "SparkAvroStep1" --master yarn-client target/scala-2.10/BaconAnalysis-assembly-1.0.jar 
```

On the second step, selected muons are ingested into a dataframe, necessary histograms are prepared,
finally, the result is plotted using Python's ```matplotlib```    

To run the second step on BD do:

```bash
spark-submit --master local[5] SparkAvroStep2.py
```
