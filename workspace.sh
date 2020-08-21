#!/usr/bin/env bash

case "$OSTYPE" in
  linux*)   echo "Linux / WSL" ;;
  darwin*)  echo "Mac OS" ;; 
  win*)     echo "Windows" ;;
  msys*)    echo "MSYS / MinGW / Git Bash" 
            TTY=winpty
            CURRENT_DIR=$(cygpath -w $(pwd));;
  cygwin*)  echo "Cygwin" 
            TTY=
            CURRENT_DIR=$(cygpath -w $(pwd));;
  bsd*)     echo "BSD" ;;
  solaris*) echo "Solaris" ;;
  *)        echo "unknown: $OSTYPE" ;;
esac

$TTY docker run \
    -ti \
    --volume /var/run/docker.sock:/var/run/docker.sock \
    --volume $CURRENT_DIR:/workspace \
    --env PS1="workspace> " \
    bfblog/centos:8