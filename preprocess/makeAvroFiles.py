#!/usr/bin/env python

from subprocess import Popen
import os
 
# Set the directory you want to start from
rootDir = '/scratch/network/pivarski'
outDirRoot = '/user/HEP'

make_directories = False
populate_directories = (not make_directories)

if make_directories:
    for dirName, subdirList, fileList in os.walk(rootDir):
        Popen("hdfs dfs -mkdir "+os.path.join(outDirRoot,dirName.split("/")[-1]),shell=True).wait()

if populate_directories:
    for dirName, subdirList, fileList in os.walk(rootDir):
        print('Populating directory: %s' % dirName)
        for fname in fileList:
            if '.root' not in fname: continue
            #print('\t%s' % fname)
            Popen("./build/root2avro "+os.path.join(dirName,fname)+" Events --inferTypes --mode=avro > "+os.path.join(outDirRoot,dirName.split("/")[-1])+"/"+fname.rstrip(".root")+".avro",shell=True).wait()
