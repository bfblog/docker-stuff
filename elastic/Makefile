.PHONY: all config

default: all

SHELL := /bin/bash
ROOT_DIR := $(shell git rev-parse --show-toplevel)
CURRENT_DIR := $(shell cd -P -- '$(shell dirname -- "$0")' && pwd -P)
BUILD_DATE := $(shell date -u +'%Y-%m-%dT%H:%M:%SZ')
REVISION := $(shell git rev-parse --short HEAD)
#VERSION := $(shell git describe --abbrev=0)

OPENSSL := 	docker run \
	           --user $(shell id -u) \
		       -ti \
		       --volume ${CURRENT_DIR}/cert-config:/workspace/config \
		       --volume ${CURRENT_DIR}/certs:/workspace/certs \
		       bfblog/openssl

build:
	docker build . --tag bfblog/elastic:1.9.0

start:
	docker-compose up -d
	docker-compose logs --follow

stop:
	docker-compose down

logs:
	docker-compose logs --follow

config: ./certs/root-ca.pem ./certs/odfe-node1.pem ./certs/odfe-node2.pem

certs: 
	@echo "create directory ./certs"
	mkdir -p certs

certs/root-ca.pem: certs
	${OPENSSL} genrsa -out certs/root-ca-key.pem 2048
	${OPENSSL} req -x509 -new -key certs/root-ca-key.pem -sha256 -out certs/root-ca.pem -config config/root-ca.cnf
	${OPENSSL} x509 -in certs/root-ca.pem -text

certs/odfe-node1.pem: certs
	${OPENSSL} genrsa -out certs/odfe-node1-pkcs12-key.pem 2048
	${OPENSSL} pkcs8 -v1 "PBE-SHA1-3DES" -in "certs/odfe-node1-pkcs12-key.pem" -topk8 -out "certs/odfe-node1-key.pem" -nocrypt
	${OPENSSL} req -new -key certs/odfe-node1-key.pem -out certs/odfe-node1.csr -config config/odfe-node1.cnf
	${OPENSSL} x509 -req -in certs/odfe-node1.csr -CA certs/root-ca.pem -CAkey certs/root-ca-key.pem -CAcreateserial -out certs/odfe-node1.pem -sha256 -extensions extensions -extfile config/odfe-node1.cnf
	
certs/odfe-node2.pem: certs
	${OPENSSL} genrsa -out certs/odfe-node2-pkcs12-key.pem 2048
	${OPENSSL} pkcs8 -v1 "PBE-SHA1-3DES" -in "certs/odfe-node2-pkcs12-key.pem" -topk8 -out "certs/odfe-node2-key.pem" -nocrypt
	${OPENSSL} req -new -key certs/odfe-node2-key.pem -out certs/odfe-node2.csr -config config/odfe-node2.cnf
	${OPENSSL} x509 -req -in certs/odfe-node2.csr -CA certs/root-ca.pem -CAkey certs/root-ca-key.pem -CAcreateserial -out certs/odfe-node2.pem -sha256 -extensions extensions -extfile config/odfe-node2.cnf

certs-inspect:
	${OPENSSL} x509 -in certs/root-ca.pem -text
	${OPENSSL} x509 -in certs/odfe-node1-key.pem -text
	${OPENSSL} x509 -in certs/odfe-node2-key.pem -text

