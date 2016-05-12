import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import sys
import time

import numpy as np
import os


def make_histo(label,*histos):
    ''' Takes histogram as a dictinary output by the histogrammar (variable number)
        and the plot identifier (for instance, pT) and plots the histogram using matplotlib.
        Args:
            label: arbitrary string
            histos*: histograms to plot
        Output: 
            Output plot in png format. 
    '''
    
    low = histos[0]['data']['low']
    high = histos[0]['data']['high'] 
    nbins = len(histos[0]['data']['values'])
    step = (high-low)/nbins

    bin_edges = np.linspace(low,high,nbins)
    #variable_widths = [(bin_edges[b+1]-bin_edges[b]) for b in range(len(bin_edges)-1)]

    h1 = histos[0]['data']['values']
    h2 = histos[1]['data']['values']

    p1 = plt.bar(bin_edges, h1, width=step, color='b')
    p2 = plt.bar(bin_edges, h2, width=step, color='r',bottom= h1)

    plt.xlabel('Muon '+label)
    plt.ylabel('Entries/bin')
    plt.title('First plots')
    #ax.axis([40, 160, 0, 0.03])
    #ax.grid(True)
    #plt.ylim(0,max(data1)*5)

    plt.legend((p1[0],p2[0]), (histos[0]['sample'],histos[1]['sample']))

    plt.show()
    plt.savefig(label+".png")

def main(args):
    t0 = time.time()

    import json
    #for each data and MC sample read from JSON 
    #FIXME that will change
    with open("/user/alexeys/HEPoutput/QCD_HT1000to1500_13TeV_2_0") as f:
        histo1 = json.load(f)

    with open("/user/alexeys/HEPoutput/QCD_HT1500to2000_13TeV_2") as g:
        histo2 = json.load(g)

    histo1['sample'] = "QCD_HT1000to1500"
    histo2['sample'] = "QCD_HT1500to2000"
    make_histo("pt",*(histo1,histo2))

    t1 = time.time()
    print "Elapsed time: ", t1-t0, " sec"

if __name__ == "__main__":
    main(sys.argv)
