# SocialScore

![BUILD (Ubuntu 20.04)](https://github.com/alekseinovikov/SocialScore/workflows/BUILD%20(Ubuntu%2020.04)/badge.svg)
![TESTS (Ubuntu 20.04)](https://github.com/alekseinovikov/SocialScore/workflows/TESTS%20(Ubuntu%2020.04)/badge.svg)


## Introduction

### Motivation
As the test task is very simple I could implement it just as a couple 
simple Spring Boot applications or even as server-less lambdas (for example).

But, I believe that test tasks exists for skills demonstration.


## Description

### Technologies
* Java
* Gradle
* Spring Boot
* Kafka
* Redis
* Docker
* Test Containers
* Caffeine
* Protobuf
* GitHub Actions

### Approaches

I decided to use hexagonal architecture hiding details from the logic behind abstractions.
Every implementation uses interfaces and APIs to make it simple to substitute components in future 
and make every module as stable as possible (according to the clean architecture principles).

### Data flow

User -> REST -> Service (read file) -> Kafka -> Consumer (Calculations) -> Redis

### Project Structure

- api
    - consumer-api (interface for message listeners)
    - consumer-impl (Kafka implementation of consumer)
    - producer-api (interface for message publishing)
    - producer-impl (Kafka implementation of publisher)
    - proto (proto entities for protocol)
    
    
- consumer (Consumer application starter)
    - score-consumer-api (interface for calculated score consumers)
    - score-consumer-console (writing to console implementation)
    - score-consumer-redis (writing to Redis implementation)
    - service (listener, calculator and business logic)
    
    
- provider (Provider application starter)
    - rest (REST endpoint implementation)
    - service-api (API for business logic)
    - service-impl (receiver, seed adjuster and business logic)
    
    
## Tests

Project contains 2 types of tests:
* Unit - gradle task `test`
* Integration - gradle task `integrationTest`

Unit test task depends on check and runs on every build, 
integration tests have their own source set `it` and independent 
task that can be run on demand. Test containers project is used for
Kafka and Redis instances, so those tests are time-consuming and require Docker on the machine.

So, I created 2 GitHub actions:

* BUILD - to build project and run all unit tests
* TESTS - runs integration tests

Most of the classes are test covered.

## How to run

The project has directory `docker` that contains `docker-compose.yml` file 
that describes all the needed dependencies (Kafka, Redis, Zookeeper).

If you want the project run on your local machine just make `docker-compose up -d` in that directory and then:

`./gradlew :consumer:bootRun` - to run consumer

and

`./gradlew :provider:bootRun` - to run provider

Defaults:

* provider web port: 8080
* provider API: /person/register - POST - ```{
                                               "first_name": "firstName",
                                               "last_name": "lastName",
                                               "age": 12
                                           }```
* kafka: localhost:9092
* redis: localhost:6379

* seed file: calculation.properties : seed=0.5

Every parameter can be changed with command line argument or with env variables (Spring feature).

Example: to change seed file just pass: -Dcalculation.properties.file-name=<FILE_PATH>

