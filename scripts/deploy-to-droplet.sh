#!/usr/bin/env bash

#!/bin/bash

eval "$(ssh-agent -s)" # Start ssh-agent cache
chmod 600 .travis/travis_rsa # Allow read access to the private key
ssh-add .travis/travis_rsa # Add the private key to SSH

# Skip this command if you don't need to execute any additional commands after deploying.
ssh $SSH_USERNAME@$IP <<EOF

EOF