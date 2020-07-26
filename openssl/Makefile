default: all

SHELL := /bin/bash
ROOT_DIR := $(shell git rev-parse --show-toplevel)
CURRENT_DIR := $(shell cd -P -- '$(shell dirname -- "$0")' && pwd -P)
BUILD_DATE := $(shell date -u +'%Y-%m-%dT%H:%M:%SZ')
REVISION := $(shell git rev-parse --short HEAD)
#VERSION := $(shell git describe --abbrev=0)

build:
	docker build . --tag bfblog/openssl:latest

run: 
	docker run -ti bfblog/openssl

push:
	docker push bfblog/openssl