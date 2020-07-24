default: all

SHELL := /bin/bash
ROOT_DIR := $(shell git rev-parse --show-toplevel)
CURRENT_DIR := $(shell cd -P -- '$(shell dirname -- "$0")' && pwd -P)
BUILD_DATE := $(shell date -u +'%Y-%m-%dT%H:%M:%SZ')
REVISION := $(shell git rev-parse --short HEAD)
#VERSION := $(shell git describe --abbrev=0)
SPARK_VERSION := "3.0.0"
HADOOP_VERSION := "2.7"
VERSION := "${SPARK_VERSION}"
IMAGE := "bfblog/spark"

all: print_details lint dockerfile

print_details:
	@echo "--------------------------------------------------"
	@echo "root directory   : " ${ROOT_DIR}
	@echo "current directory: " ${CURRENT_DIR}
	@echo "build date       : " ${BUILD_DATE}
	@echo "scm revision     : " ${REVISION}
	@echo "latest tag       : " ${VERSION}
	@echo "image name       : " ${IMAGE}
	@echo "SPARK_VERSION    : " ${SPARK_VERSION}
	@echo "HADOOP_VERSION   : " ${HADOOP_VERSION}
	@echo "--------------------------------------------------"

dockerfile:
	@docker build -t ${IMAGE}:${VERSION} --no-cache=true \
	        --build-arg BUILD_DATE=$(BUILD_DATE) \
	        --build-arg REVISION=$(REVISION) \
	        --build-arg VERSION=$(VERSION) \
			--build-arg SPARK_VERSION=${SPARK_VERSION} \
			--build-arg HADOOP_VERSION=${HADOOP_VERSION} \
	        --build-arg IMAGE=$(IMAGE) \
  	        .
	@echo "docker image ${IMAGE}:${VERSION} successfully build"
	@echo -e "\ninspect image labels:"
	@docker inspect -f '{{range $$k, $$v := .Config.Labels -}}{{ $$k }}={{ $$v }}{{printf "\n"}}{{ end -}}' ${IMAGE}:${VERSION}


lint: hado_lint dockerfile_lint
	@echo "lint completed"

hado_lint:
	@echo "applying hadolint ..."
	@docker run --rm -i hadolint/hadolint < Dockerfile

dockerfile_lint:
	@echo "applying dockerfile lint"
	@docker run -it --rm -v $(CURRENT_DIR):/root/ -v ${ROOT_DIR}/policies:/root/policies projectatomic/dockerfile-lint dockerfile_lint -r policies/rules.yml 
