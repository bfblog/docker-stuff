FROM alpine:3.12.0 AS builder

WORKDIR /tmp

ENV SPARK_VERSION=3.0.0
ENV HADOOP_VERSION=2.7

SHELL ["/bin/ash", "-eo", "pipefail", "-c"]

RUN apk add --no-cache gnupg=2.2.20-r0 \
    && wget https://downloads.apache.org/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
    && wget https://downloads.apache.org/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz.asc \
    && wget https://downloads.apache.org/spark/KEYS 

# copy prepared files
COPY ./gnupg /root/.gnupg

RUN chmod 700 /root/.gnupg 
RUN chmod 600 /root/.gnupg/*
RUN gpg --list-sigs
RUN gpg --verify spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz.asc spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz
RUN mkdir spark_runtime

WORKDIR /spark_home
RUN tar xzvf /tmp/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz --strip-components=1 

FROM alpine:3.12.0 

ARG BUILD_DATE
ARG REVISION
ARG VERSION
ARG IMAGE

LABEL name="${IMAGE}" \
      version="${VERSION}" \
      release="1.0" \
      architecture="x86_64" \
      vendor="markus" \
      maintainer="markus" \
      io.k8s.description="unknown" \
      io.k8s.display-name="unknown" \
      io.openshift.expose-services="1313:http" \
      io.openshift.tags="builder" \
      org.opencontainers.image.created="${BUILD_DATE}" \
      org.opencontainers.image.authors="Markus Breuer" \
      org.opencontainers.image.url="https://github.com/skippi1/docker-stuff" \
      org.opencontainers.image.documentation="bytefusion.de examples" \
      org.opencontainers.image.source="https://github.com/skippi1/docker-stuff" \
      org.opencontainers.image.version="${VERSION}" \
      org.opencontainers.image.revision="${REVISION}" \
      org.opencontainers.image.vendor="n/a" \
      org.opencontainers.image.licenses="n/a" \
      org.opencontainers.image.ref.name="${IMAGE}"

SHELL ["/bin/ash", "-eo", "pipefail", "-c"]

HEALTHCHECK --interval=5s --timeout=3s CMD if [ -f /src/public/index.html ] ; then exit 0; else exit 1; fi

RUN apk add --no-cache tini=0.19.0-r0 openjdk8=8.242.08-r2 bash=5.0.17-r0 \
    && addgroup -Sg 1000 spark \
    && adduser -SG spark -u 1000 -h /src spark

USER spark

WORKDIR /home/spark

COPY --from=builder /spark_home .

ENTRYPOINT ["/sbin/tini", "--"] 

CMD ["/bin/bash", "-c", "/home/spark/bin/spark-shell" ]