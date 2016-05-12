# Description of the analysis workflows

The test folder contains an analysis workflow based on Scala and Python. The workflow should recognize files in JSON and Avro formats prepared with ```root2avro``` conversion tool.  


## Preprocessing

[`root2avro`](https://github.com/diana-hep/rootconverter/tree/master/root2avro) is a C++ program that converts ROOT TTree data into an equivalent JSON or Avro data (which may be saved to a file on disk or streamed into another application).

To prepare Bacon JSON files, install the root2avro package following the instructions above and do:

```bash 
./build/root2avro /scratch/network/pivarski/DYJetsToLL_M_50_HT_100to200_13TeV/Output_10.root Events --inferTypes --mode=json > DYJetsToLL_M_50_HT_100to200_13TeV_output10.json
```

As of version 0.2, the output needs cleaning:

```bash
sed -i -e 's/inf/-999999.9/g' DYJetsToLL_M_50_HT_100to200_13TeV_output*
sed -i -e 's/-nan/-999999.9/g' DYJetsToLL_M_50_HT_100to200_13TeV_output*
sed -i -e 's/nan/-999999.9/g' DYJetsToLL_M_50_HT_100to200_13TeV_output*
```

## ```Scala``` and ```Python``` based workflow

On the first step, bacon ```JSON``` or ```Avro``` produced using the ```root2avro``` conversion tool is ingested into Spark's dataset. The corresponding collections are flattened with ```flatMap``` transformation, and 
the event and muon selection is applied. Histogramming using the ```Histogrammar``` is applied, and the output is saved as a JSON to the disk.

To run on BD, firt build the project with SBT:

```bash
sbt assembly
```

Next, submit the jobs specifying the class and path to the jar file:
```bash
spark-submit --class "SparkAvroStep1" --master yarn-client --num-executors 20 --executor-cores 3 --executor-memory 3g target/scala-2.10/BaconAnalysis-assembly-1.0.jar --muPtCut 10.0 file:///scratch/network/alexeys/HEP/QCD_HT1000to1500_13TeV_2/ /user/alexeys/HEPoutput/QCD_HT1000to1500_13TeV_2_0
```

On the second step, the results are plotted using Python's ```matplotlib```    

To run the second step on BD do:

```bash
python SparkAvroStep2.py
```

Note: second step will be modified in future.
