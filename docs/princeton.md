# Notes on Princeton analysis workflow

## Connecting to the cluster


### Mac OS users

Connecting to the BD cluster from Fermilab (or anywhere outside Princeton Campus network, really) is rather simple
for Mac OS users: https://princeton.service-now.com/snap/kb_search.do?id=1156

who can use the Sonic Mobile Connect VPN client. From inside the Princeton VPN, connecting can be done from the terminal window:

```bash
ssh -XC alexeys@bd.rc.princeton.edu
```

### Linux users

There is no dedicated VPN client for Linux OS. The existing best workflow consisted of 2 hops: 

1. Connect to tigressdata ssh

2. Connect to BD cluster from tigressdata via ssh


## Running Jupyter notebooks remotely

Jupyter notebook is an extremely useful and easy to use tool for data scientists. When performing large scale analyses, we are 
forced to perfrom bulk of calculations on clusters. Running jupyter notebook on the cluster is inconvenient, 
because one has to forward X11 and second, running a webbrowser on the headnode of the cluster is slow due to network latency.

The best solution is to run web browser on your local machine, run jupyter notebook on the BD cluster, and access it via ssh tunnel.


### Installation instructions on BD

Since we are going to use Jupyter for both Python and Scala analyses, one is going to need the Jupyter version 4.0 
or greater. Ensure you got the right version of jupyter by typing: 

```bash
jupyter --version
``` 

it should print a value >= 4.0. Otherwise, please follow the instructions on how to install Jupyter with Anaconda.
 
We wouldneed to create an isolated Anaconda environment for that:

```bash
module load anaconda3/2.5.0
conda create -n DarkMatter python=3.5.1 anaconda
source activate DarkMatter
conda install --name DarkMatter jupyter
```

Then download and run the Jupyter Scala launcher with:

```bash
curl -L -o jupyter-scala https://git.io/vrHhi && chmod +x jupyter-scala && ./jupyter-scala && rm -f jupyter-scala
```

This downloads the bootstrap launcher of Jupyter Scala, then runs it. 
If no previous version of it is already installed, this simply sets up 
the kernel in ~/Library/Jupyter/kernels/scala211 (OSX) or ~/.local/share/jupyter/kernels/scala211 (Linux). 
Note that on first launch, it will download its dependencies from Maven repositories. 
These can be found under ~/.jupyter-scala/bootstrap.

Once installed, the downloaded launcher can be removed, as it copies itself 
in ~/Library/Jupyter/kernels/scala211 or ~/.local/share/jupyter/kernels/scala211.

For Scala 2.10, please follow the instructions here: https://github.com/alexarchambault/jupyter-scala


In addition, we are going to need to install the histogrammar packages as described here:

https://github.com/histogrammar/histogrammar-scala

and here

https://github.com/histogrammar/histogrammar-python

the Python bindings should be installed in the same Anaconda environment as above (it does not matter for Scala though).


Note: pip-based installation is possible via:

```bash
pip install --user jupyter
```

Note: when using Anaconda, following two steps would have to be repeated every login:

```bash
module load anaconda3/2.5.0
source activate DarkMatter
```

in addition, one needs to make sure that the version used by PySpark is the same that is in the environment you install your packages to by setting:

```bash
export PYSPARK_PYTHON=/home/alexeys/.conda/envs/DarkMatter/bin/python3.5
export PYSPARK_DRIVER_PYTHON=/home/alexeys/.conda/envs/DarkMatter/bin/python3.5
```


Therefore, you can put it in the `.bashrc` file.

### Running the notebook via ssh tunnel

On the remote machine (BD cluster headnode in this case), launch the jupyter notebook:

```bash
jupyter notebook --no-browser --port=8889 --ip=127.0.0.1
```

On the local machine type:

```bash
ssh -N -f -L localhost:8888:localhost:8889 alexeys@bd.rc.princeton.edu
```

The first option -N tells SSH that no remote commands will be executed, 
and is useful for port forwarding. The second option -f has the effect that SSH 
will go to background, so the local tunnel-enabling terminal remains usable. 
The last option -L lists the port forwarding configuration (remote port 8889 to local port 8888).

Note: tunnel will be running in the background. The notebook can now be accessed from your browser at `http://localhost:8888`

### Running Spark-enabled notebook in a disrtibuted mode

To run Spark enabled Jupyter notebook on BD one would have to re-launch the Jupyter as follows:

Scala based:

```bash
IPYTHON_OPTS="notebook --no-browser --port=8889 --ip=127.0.0.1" spark-shell --master yarn-client --num-executors 10 --executor-cores 2 --executor-memory 5g
```

Python based:

```bash
IPYTHON_OPTS="notebook --no-browser --port=8889 --ip=127.0.0.1" pyspark --master yarn-client --num-executors 10 --executor-cores 2 --executor-memory 5g
```

## Next steps

One you are done with the installations and have the ssh tunnel up and running, go ahead and test few workflows 
which repeat the plotting examples from this page: http://histogrammar.org/docs/tutorials/
