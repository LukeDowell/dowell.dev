#!/usr/bin/env bash

echo "Building and pushing dowell.dev to docker hub..."
echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
docker build -t dowell.dev .
docker tag dowell.dev lukedowell/dowell.dev:$RELEASE_TAG
docker tag dowell.dev lukedowell/dowell.dev:latest
docker push lukedowell/dowell.dev:$RELEASE_TAG
echo "Finished pushing dowell.dev to docker hub ^_^"