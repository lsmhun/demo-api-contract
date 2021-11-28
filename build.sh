#!/bin/bash

echo Collecting Maven wrapper
mvn io.takari:maven:wrapper
echo Building demo-contract-api : OpenAPI generate
cd demo-contract-api
echo `pwd`
`pwd`/mvnw install
cd ..
echo Building demo-contract-server : Build and run tests included producer contract tests
cd demo-contract-server
echo `pwd`
`pwd`/mvnw verify
echo Adding stubs to local Maven repository
`pwd`/mvnw org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file
cd ..
echo Building demo-contract-client : Build and run tests included consumer contract tests
cd demo-contract-client
echo `pwd`
`pwd`/mvnw verify
cd ..
echo ---
echo 'You can start server application from CLI:'
echo `pwd`/demo-contract-server/mvnw spring-boot:run
echo 'You can run client application from CLI:'
echo `pwd`/demo-contract-client/mvnw spring-boot:run -Dspring-boot.run.arguments=--temperature.countryList=hu,li

