spark-shell --conf "spark.serializer=org.apache.spark.serializer.KryoSerializer" --conf "spark.kryo.classesToRegister=Events,baconhep.TAddJet,baconhep.TElectron,baconhep.TEventInfo,baconhep.TGenEventInfo,baconhep.TGenParticle,baconhep.TJet,baconhep.TMuon,baconhep.TPhoton,baconhep.TTau,baconhep.TVertex" --jars target/scala-2.10/BaconAnalysis-assembly-1.0.jar,`ls  ../../histogrammar-scala/sparksql/target/*.jar ../../histogrammar-scala/bokeh/target/*.jar ../../histogrammar-scala/bokeh/target/lib/*.jar | tr '\n' ','`