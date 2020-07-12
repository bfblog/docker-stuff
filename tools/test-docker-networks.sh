#!/bin/bash

echo "create sample networks ..."
for i in {1..50}; do docker network create net-$i; done

echo "inspect all docker networks ..."
for i in $(docker network ls -q); 
do 
   docker network inspect --format='{{.Name}} {{.IPAM.Config}}' $i; 
done

echo "remove sample networks ..."
for i in {1..50}; do docker network rm net-$i; done

echo "finished"
