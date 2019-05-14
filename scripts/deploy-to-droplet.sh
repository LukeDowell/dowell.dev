#!/bin/bash

eval "$(ssh-agent -s)" # Start ssh-agent cache
chmod 600 .travis/travis_rsa # Allow read access to the private key
ssh-add .travis/travis_rsa # Add the private key to SSH

ssh -o "UserKnownHostsFile=/dev/null" -o "StrictHostKeyChecking=no" $SSH_USERNAME@$IP <<EOF
    docker stop dowell.dev
    docker rm dowell.dev
    docker run -d -p 80:8080 --name dowell.dev lukedowell/dowell.dev:${RELEASE_TAG}
EOF