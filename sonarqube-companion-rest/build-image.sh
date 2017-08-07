#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

pushd $DIR
docker build -t consdata/sonarqube-companion:latest .
popd
