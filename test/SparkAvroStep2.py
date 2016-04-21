from pyspark import SparkContext
from pyspark.sql import SQLContext
#from pyspark.sql.functions import explode
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
    sc = SparkContext(appName="LoadAvroStep2")
    sqlContext = SQLContext(sc)

    selected_muons = sqlContext.read.parquet("/user/alexeys/selected_muons")
    selected_muons.printSchema()

    #prepare bin edges
    pt_bin_edges = [10,30,50,80,150,500,100000] 

    #prepare histograms
    pt_histo = selected_muons.select("pt").rdd.map(lambda x: x.pt).histogram(pt_bin_edges)

    #Back to driver
    make_histo(pt_histo,"pt")

if __name__ == "__main__":
    main(sys.argv)
