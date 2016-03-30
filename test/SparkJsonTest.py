from pyspark import SparkContext
from pyspark.sql import SQLContext
from pyspark.sql.functions import explode
#from pyspark.sql.functions import udf
#from pyspark.sql.functions import broadcast

import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import os
import sys

def make_histo(histo,label):
    ''' Takes histogram in the format of pyspark rdd.histogram
        and the plot identifier (for instance, pT) and plots the histogram using matplotlib.
        Args:
            histo: tuple of lists
            label: arbitrary string
        Output: 
            Output plot in png format. 
    '''
    bin_edges,data = histo
    variable_widths = [(bin_edges[b+1]-bin_edges[b]) for b in range(len(bin_edges)-1)]

    plt.bar(bin_edges[:-1], data, width=variable_widths, color='green')
    plt.xlabel('Muon '+label)
    plt.ylabel('Events/bin')
    plt.title('First plots')
    #ax.axis([40, 160, 0, 0.03])
    #ax.grid(True)
    plt.ylim(0,max(data)*1.5)
    plt.show()
    plt.savefig(label+".png")


def main(args):
    '''
       Run as spark-submit --master local[5] SparkJsonTest.py
       On larger datasets, use yarn-client submission mode. Check spark-submit --help for more details       
    '''
    sc = SparkContext(appName="LoadJson")
    sqlContext = SQLContext(sc)
    #sqlContext.udf.register("df_apply_indexing", apply_indexing)

    run_dir = os.environ.get('PWD')
    input = sqlContext.read.json("file://"+os.path.join(run_dir,'data/bacon.json'))
    #input.printSchema()

    muons = input.select(explode("Muon").alias("Mu"))
    muonsDF = muons.selectExpr("Mu.pt", "Mu.eta", "Mu.dz", "Mu.d0", "Mu.nPixHits", "Mu.pogIDBits","Mu.chHadIso","Mu.neuHadIso","Mu.gammaIso","Mu.puIso")
    muonsDF.show()

    #FIXME replace filter with UDF?
    muonsDF_selected = muonsDF.filter((muonsDF.pogIDBits > 0) & (muonsDF.pt > 10.0) & (muonsDF.eta < 2.4) & ((muonsDF.chHadIso + muonsDF.neuHadIso + muonsDF.gammaIso - 0.5*muonsDF.puIso) < 0.2*muonsDF.pt))

    #prepare bin edges
    pt_bin_edges = [10,30,50,80,150,500,100000] 

    #prepare histograms
    pt_histo = muonsDF_selected.select("pt").rdd.map(lambda x: x.pt).histogram(pt_bin_edges)

    #Back to driver
    make_histo(pt_histo,"pt")


if __name__ == "__main__":
    main(sys.argv)
