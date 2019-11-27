# dowell.dev [![Build Status](https://travis-ci.com/LukeDowell/dowell.dev.svg?branch=master)](https://travis-ci.com/LukeDowell/dowell.dev)

How Michelle

## Running dowell.dev

*React App*

Navigate to src/main/webapp and execute `npm start`

*Spring Boot App*

In the root project directory, execute `./gradlew bootRun`

*Bundled app*

Execute `./gradlew clean build` then, `java -jar build/libs/dowell.dev.jar`

*Docker*

To build the image, execute `docker build -t dowell.dev .` then, to run the image, execute `docker run -p 8080:8080 -d docker.dev:latest`

## Deployment Overview ##

Can be found [here!](deploy/deployment-overview.md)
