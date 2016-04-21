# Description of the analysis workflows

The test folder contains two independent analysis workflows

## Fully ```Python``` based

Bacon ntuple converted to ```JSON``` is ingested into dataframe and transformed/acted on using UDFs. The final result is plotted using Python's ```matplotlib```

## Scala and ```Python``` based

On the fiest step, a bacon ```JSON``` or ```Avro``` is ingested into dataset. The event and muon selection is applied and the filterted muon
dataset is saved into ```Parquet``` to an intermediate ```HDFS``` sink. On the second step, selected muons are ingested into a dataframe, necessary histograms are prepared,
finally, the result is plotted using Python's ```matplotlib```    
