# workshop-webflux-tracking-service

Workshop: Create a Spring Boot tracking service to ingest data from clients and store it to multiple storage implementations.

## Setup

This project has been created from [Spring Initializr](https://start.spring.io/):
![configuration](docs/spring-initializr.png)

Requisites:
- Java8+

## What to do: The Pulse Service

## Workshop

### Preparation

```
git clone 
./gradlew bootRun
```

### 1 Consume POST data containing a bunch of Pulses

### 2 Create a Pulse logger 

### 3 Publish Pulses to a Domain Event Bus keeping the logger as a domain event observer

### 4 Convert service errors into Pulses

### 5 Convert unhandled errors into Pulses 

### 6 Adding performance evaluation

```
npm install -g artillery

artillery run artillery/load-test.yml
```