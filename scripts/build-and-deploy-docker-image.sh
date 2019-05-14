#!/usr/bin/env bash

echo "Building and pushing $DOCKER_IMAGE_NAME to docker hub..."
echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
docker build -t $DOCKER_IMAGE_NAME .
docker push $DOCKER_IMAGE_NAME
echo "Finished pushing $DOCKER_IMAGE_NAME to doc- ker hub ^_^"