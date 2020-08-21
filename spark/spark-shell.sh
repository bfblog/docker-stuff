#!/bin/bash

docker run -ti --network spark_backend --volume $(pwd)/data:/home/spark/data bfblog/spark:3.0.0 ./bin/spark-shell --master spark://master:7077
