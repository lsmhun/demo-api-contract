# Spring Boot Contract test demo

## Intro
Today [poliglot](https://en.wikipedia.org/wiki/Polyglot_(computing)) approach is more popular then before. 
companies started to use [API first approach](https://swagger.io/resources/articles/adopting-an-api-first-approach/)
instead of traditional REST definitions.
Using [OpenAPI](https://www.openapis.org/) definitions it is much more easy to implement applications and based on that
cooperation between teams can be fluent and clear. In practice [Swagger](https://swagger.io/specification/) 
is the industry standard.

Unfortunatly this is not enough. Developers need precise definitions, because there could be lots of
Map<String, Object> formátumú elemekkel a leírást, akkor nem sokra fognak menni a jövendőbeli
kliensek, és kénytelenek lesznek a dokumentációt bújni vagy mindennel a produceer oldalt zaklatni.

[Spring Cloud Contract](https://cloud.spring.io/spring-cloud-contract/)
project offers a good solution to this cooperation with comfortable interface for both client and server side
(producer and consumer sides).

## Technology
[Marcin Grzejszczak](https://toomuchcoding.com/) is one of the lead developer of Spring Cloud Contract. 
I would recommend these two videos from him:

[![Contract Tests in the Enterprise. Marcin Grzejszczak, Pivotal](https://img.youtube.com/vi/yQjcDlibdWM/0.jpg)](https://www.youtube.com/watch?v=yQjcDlibdWM)
Live coding session:
[![Consumer Driven Contracts and Your Microservice Architecture. Marcin Grzejszczak, Pivotal](https://img.youtube.com/vi/sAAklvxmPmk/0.jpg)](https://www.youtube.com/watch?v=sAAklvxmPmk&t=540s)

Spring Cloud Contract uses [wiremock](http://wiremock.org/). Contracts can be defined in YAML or Groovy format,
then these files will be converted to wiremock JSON format. After that it's archived into a JAR, which is edible for
wiremock. In addition to that it can generate producer/server side tests based on contracts. It supports multiple
format (i.e. [Groovy SPOCK](https://spockframework.org/) and jUnit5). This stub JAR file can be used in 
consumer-client side as well, as you can below:

```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"com.example:http-server-dsl:+:stubs:6565"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class LoanApplicationServiceTests {
    . . .
}
```

## Abstract
The example application has to identify the hottest country based on average temperature. It will have three compontents:

- [API](../demo-contract-api/README.md)
- [Server side](../demo-contract-server/README.md)
- [Client side](../demo-contract-client/README.md)

Server side will present the requested country last year's average temperature (in version 1.0). 
It will use database for future development.

Client side is a simple and stupid application, which can handle incoming parameters as a list of countries
and it will present the hottest countries (it is possible to have similar average temperatures.)

## Implementation - version 1.0
### API
The most important part is the [API definítion](../demo-contract-api/src/main/resources/openapi/v1/demo-contract-openapi.yaml).
[OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator) was chosen, however 
[Swagger](https://swagger.io/tools/swagger-codegen/) plugin is more powerful.

Please take into consideration that all applications are independent! These apps are in same git repo and in the 
same maven and idea project, but these are not Maven submodules! This is just for easy to use.

In API project there will be just generated source codes. For client side there will be a RestTemplate client 
implementation, for server side there will be just some stub interfaces.

Actually it would be better if API and contracts could be located in a separated git repo, but I chose a simplified
version. As a result there will be mini maven project which will create a jar file and this can be used as a 
dependency. You can generate these stubs for any language from our OpenAPI definition.

By default _mvn install_ command will install the package into the local Maven repository, so we can use it.

### Server side (Producer)
Our application will use just a simple H2 database (it can be changed any time). At this moment just some basic 
country_code - average temparature pairs are persisted. Spring JPA with [Liquibase](https://www.liquibase.org/)
is more then enough for this target.

[Spring Cloud Contract Maven plugin](https://cloud.spring.io/spring-cloud-contract/spring-cloud-contract-maven-plugin/plugin-info.html)
is a useful tool for developers. It can cover most of the cases without CLI, some pom.xml definition can define
everything what you need. For this example the most important parameter is baseClassForTests, which will define the parent
of our generated tests.

```xml
<plugin>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-contract-maven-plugin</artifactId>
    <version>3.0.4</version>
    <extensions>true</extensions>
    <configuration>
        <baseClassForTests>hu.lsm.demo.contract.server.api.contract.TemperatureContractBase</baseClassForTests>
        <testFramework>JUNIT5</testFramework>
    </configuration>
</plugin>
```
More mappings are supported also, because it's common if different tests would use separeted environments/mocks/etc.
This is an example from documentation page:
```xml
...
<configuration>
    <baseClassForTests>hu.lsm.demo.contract.server.api.contract.TemperatureContractBase</baseClassForTests>
    <baseClassMappings>
        <baseClassMapping>
            <contractPackageRegex>.*com.*</contractPackageRegex>
            <baseClassFQN>com.example.TestBase</baseClassFQN>
        </baseClassMapping>
    </baseClassMappings>
</configuration>
...
```
Worth to mention, that mocking is based on team's decision. It can be shallow or deep contract testing. In ideal world
just the outside/third party app should be mocked. It is possible to mock on highest level, but in this case
deep implementation changes won't break the contract test, which is unfortunate. However it can be enough for some
quick stub definitions. Eternal question: where is the borderline between functional and integration/contract tests?

Default location of contracts is _resources/contracts_ directory, where yaml or groovy files are located. In one yaml 
file there could be more contracts in the same time. Then convert will generate the wiremock formatted JSON, which 
will be archived into stub jar. It will be ready during Maven build, but it's possible to execute it manually.

Last trick with [Maven install plugin](https://maven.apache.org/plugins/maven-install-plugin/examples/installing-secondary-artifacts.html#)
and 'classifier' parameter this stub jar can be installed into local Maven repo. There is a Contract plugin SCM uploader, 
but it uses SSH key for git and this dummy example doesn't need it now, rather we target a standalone setup.

Starting the server appliation (it will run on standard 8080 by default):
```shell
mvnw spring-boot:run
```
### Client side (Consumer)
Client is Spring boot application without web layer. It can be configured by application.properties :
```properties
spring.main.web-application-type=none
```
RestTemplate client code has been generated in API project. It can be generated in this project also, but not now.
Just the URL has to be configurable. Default host and port is in application.properties.

[TemperatureStartupArgumentCollector](../demo-contract-client/src/main/java/hu/lsm/demo/contract/client/startup/TemperatureStartupArgumentCollector.java)
will handle incoming parameters from CLI.

Example execution:
```shell
mvnw spring-boot:run -Dspring-boot.run.arguments=--temperature.countryList=hu,li
```

Client side contract test is in [TemperatureClientContractTest](../demo-contract-client/src/test/java/hu/lsm/demo/contract/client/TemperatureClientContractTest.java)
class. Annotations are the most important in this test. During test execution it starts a wiremock server and loads
contracts, then it will run the tests with that. As you can see 
_stubsMode = StubRunnerProperties.StubsMode.LOCAL_ was used, which will try to use the local repository for stubs. 
In case of remote git or Nexus artifactory, _REMOTE_ is the decent value.

In this example lot of parameters are hard coded. Stub version and wiremock port are fixed. You can use '+' for latest
stub version, however if release version will be _x.y.z_ then it will be the target. @DirtyContext is needed because
of fixed port. 

## Integration
Small [build script](../build.sh) was created. It will build and run all tests. Takari Maven wrapper collector is a
useful tool (no need to upload binary maven wrapper jar into your repository)
```shell
mvn io.takari:maven:wrapper
```

## Future development

There could be a good demo for checking API evolution and results on contracts. At this moment this article tried to
show the basics.

## Screenshots
![Contract tests in IDEA](contract-demo-02-with-comments.png)

## Useful links
- https://cloud.spring.io/spring-cloud-contract/reference/html/
- https://stackoverflow.com/questions/4955635/how-to-add-local-jar-files-to-a-maven-project
- https://medium.com/better-practices/api-first-software-development-for-modern-organizations-fdbfba9a66d3
- https://www.baeldung.com/spring-boot-command-line-arguments