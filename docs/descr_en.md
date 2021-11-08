# Spring Boot Contract test demo

## Knowledge base
I can recommend this video from [Marcin Grzejszczak](https://github.com/marcingrzejszczak):

[![Contract Tests in the Enterprise. Marcin Grzejszczak, Pivotal](https://img.youtube.com/vi/yQjcDlibdWM/0.jpg)](https://www.youtube.com/watch?v=yQjcDlibdWM)

[API first](https://medium.com/better-practices/api-first-software-development-for-modern-organizations-fdbfba9a66d3)

## Abstract
The example application has to identify the hottest country based on average temperature. It will have three compontents:
- [API](../demo-contract-api/README.md)
- [Server implementation of API](../demo-contract-server/README.md)
- [Client implementation of API](../demo-contract-client/README.md)


All applications are based on Spring Boot. Server side implements API defined REST interface. It will use H2 database 
with JPA, changes followed by Liquibase. Client side is just a REST client, which can collect the average temperatures
of countries and it can select the hottest country. If there are more country with the same average, then all of them 
will be written out.

## Step #1: Implement version 1.0
### Implement API
### Implement Server Side
### Implement Client Side
### Run Contract test
### Upload stub

## Step #2: implement version 1.1 - non breaking changes
### Upgrade API
### Upgrade Server Side
### Upgrade Client Side
### Run Contract test

## Step #3: implement version 1.2 - API breaking changes
### Upgrade API
### Upgrade Server Side
### Upgrade Client Side
### Run Contract test

## Conclusion

## Screenshots


## Articles