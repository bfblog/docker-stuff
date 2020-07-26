default: all

SHELL := /bin/bash
ROOT_DIR := $(shell git rev-parse --show-toplevel)
CURRENT_DIR := $(shell cd -P -- '$(shell dirname -- "$0")' && pwd -P)
BUILD_DATE := $(shell date -u +'%Y-%m-%dT%H:%M:%SZ')
REVISION := $(shell git rev-parse --short HEAD)
#VERSION := $(shell git describe --abbrev=0)

all: build 

build:
	docker build . --tag bfblog/centos:8

run:
	docker run \
	   -ti \
	   --volume /var/run/docker.sock:/var/run/docker.sock \
	   --volume ${CURRENT_DIR}:/workspace \
	   bfblog/centos:8