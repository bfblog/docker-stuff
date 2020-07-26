FROM centos:8

SHELL ["/bin/bash", "-c"]

RUN yum install -y git make wget 

RUN dnf install -y dnf-plugin-config-manager

RUN dnf config-manager --add-repo=https://download.docker.com/linux/centos/docker-ce.repo

RUN dnf install docker-ce --nobest -y

USER 202020

WORKDIR /workspace