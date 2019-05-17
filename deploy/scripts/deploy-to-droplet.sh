#!/bin/bash

eval "$(ssh-agent -s)" # Start ssh-agent cache
chmod 600 deploy/travis_keys/travis_rsa # Allow read access to the private key
ssh-add deploy/travis_keys/travis_rsa # Add the private key to SSH

ssh -o "UserKnownHostsFile=/dev/null" -o "StrictHostKeyChecking=no" $SSH_USERNAME@$IP <<EOF
    docker stop dowell-dev
    docker rm dowell-dev
    docker run -d -p 8080 --name dowell-dev --network travis_nginx-network lukedowell/dowell.dev:${RELEASE_TAG}
EOF