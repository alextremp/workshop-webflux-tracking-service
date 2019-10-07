# workshop-webflux-tracking-service

Workshop: Create a Spring Boot tracking service to ingest data from clients and store it to multiple storage implementations.

## Setup

This project has been created from [Spring Initializr](https://start.spring.io/):
![configuration](docs/spring-initializr.png)

Requisites:
- Java8+

## What to do: The Pulse Service

The Pulse service will be a service that will allow clients to send pulse events to store them in more than one storage/tracking/indexation system (consider a database, an ELK, datadog, ...).

For example, this is a kind of request that should be accepted:

``` json
{
  "id": "sample-request",
  "events": [
    {
      "name": "EVENT_01",
      "payload": {
        "key1": "value1",
        "key2": 2,
        "key3": {
          "key31": "value31"
        }
      }
    },
    {
      "name": "EVENT_02"
    },
    {
      "name": "EVENT_03",
      "payload": {
        "key3": {
          "key31": "value31"
        }
      }
    }
  ]
}
```

A client request can contain from 1 to N pulse events in the same request.

* The **id** (mandatory) is the client specific id assigned to each request.

For each event:
* The **name** (mandatory) is the event name assigned by the client to its custom pulse event.
* The **payload** (optional) is the unformatted object containing what the client has designed to be the payload of each event.

The final service:

* Will process all received pulse events individually
* Each event will be stored in a log file with a specific format
* Any service exception will be processed as a system pulse event
  
## Workshop

### Preparation

```
git clone https://github.com/alextremp/workshop-webflux-tracking-service.git
cd workshop-webflux-tracking-service
./gradlew bootRun
```

### 1 Consume POST data containing a bunch of Pulses

* Check
    * _com.github.alextremp.pulse.domain.pulse.Pulse_ is our domain entity
    * _com.github.alextremp.pulse.application.savepulses.SavePulsesUseCase_ is our application use case to save the received Pulses
* Map the received request to 1..N Pulse
    * The received request id will be the Pulse's clientId
    * Consider using a UUID to set the Pulse id
* The response:
    * To ease the exercise, any individual Pulse event error will cause a request error.
    * Return a PROCESSED code for OK requests.
    * Return an ERROR code for failed requests.
    * Consider adding a message additionally to the code in order to validate responses.

(solved at branch: workshop/01-solved)

### 2 Create a Pulse logger

(solved at branch: workshop/02-solved) 

### 3 Publish Pulses to a Domain Event Bus keeping the logger as a domain event observer

(solved at branch: workshop/03-solved)

### 4 Convert service errors into Pulses

(solved at branch: workshop/04-solved)

### 5 Convert unhandled errors into Pulses

(solved at branch: workshop/05-solved) 

### 6 Extra: Performance evaluation

```
npm install -g artillery

artillery run artillery/benchmark.yml
```