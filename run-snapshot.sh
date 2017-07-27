#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

pushd $DIR
docker run --rm -d -p 8000:8080 -v $DIR:/opt/sonarqube-companion/repository consdata/sonarqube-companion:latest-snapshot
popd