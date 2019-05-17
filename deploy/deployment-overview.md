## Deployment Overview

Deployment starts with any change being pushed to master in lukedowell/dowell.dev. Travis picks up that change
and starts a build based on the parameters found in $PROJECT_ROOT/.travis.yml. After the tests are run, a jar is built
that takes the minified / optimized version of the front end app and places it in the /static directory so Spring can 
easily serve it up.

Travis then builds a docker image using $PROJECT_ROOT/Dockerfile and pushes it to my docker hub repository. Then,
Travis opens an SSH connection to a digital ocean droplet using the encrypted keys found in the travis_keys directory.
It stops and removes any containers running with the name dowell_dev and starts a new one with the tag that matches the 
current travis build number. The droplet is running an nginx and certbot container (whose docker-compose.yml can be 
found in the server directory) and seamlessly direct to the new, updated dowell.dev image. 