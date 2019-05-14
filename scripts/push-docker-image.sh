#!/bin/bash

echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
docker build -t dowell.dev .
docker tag dowell.dev lukedowell/dowell.dev:$RELEASE_TAG
docker push lukedowell/dowell.dev:$RELEASE_TAG