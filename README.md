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

The use case should write each Pulse to 1 line string in a specific logger.
Consider adding a file appender to logback, like this:

``` xml
    <property name="PulseEventFileName" value="pulse-event-logger" />

    <appender name="PulseEventLogAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOGS}/${PulseEventFileName}.log</file>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <Pattern>%m%n</Pattern>
        </encoder>

        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- rollover daily and when the file reaches 10 MegaBytes -->
            <fileNamePattern>${LOGS}/archived/${PulseEventFileName}-%d{yyyy-MM-dd}.%i.log
            </fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
    </appender>

    <!-- LOG "com.github.alextremp.reactivetrackingdemo*" at INFO level -->
    <!-- 
    <logger name="<Create the class>" level="info" additivity="false">
        <appender-ref ref="PulseEventLogAppender" />
    </logger>
    -->
``` 

* Create a Pulse transformation to 1 line String in the format:

PULSE | pulse_id: {pulse.id} | pulse_clientId: {pulse.clientId} | pulse_name: {pulse.name} | *{pulse.payload.key}: {pulse.payload.value}

* Each mapped Pulse should be logged into the new custom logger in the specific format.    

This should make you ask:
* Where the Pulse to String transformation should occur?
* Where the LOG action should occur?
* What if we want to add another Pulse storage / notification in another completely different specific format (so we cannot use a common interface to write Pulses)?

So... let's discuss about that :)

Consider:
* Using a PulseRepository able to save Pulses in different storages (chained?). Do we want to save Pulses in a repository... only?
* Using a StorePulseService able to store Pulses enabled to use different repositories, underlying services... (a sequential order is relevant?)  
...

yeah, the "solved" part of this exercise won't be solved, obviously until the third block :)

(solved at branch: workshop/02-solved) 

### 3 Publish Pulses to a Domain Event Bus keeping the logger as a domain event observer

What about Publishing Pulses to a Bus?

Then, each emmitted Pulse can be consumed by each specific Observer implementations we need to use to store / notify / whatever them. So:

* Let's create a simple Domain Event Bus that allow us to notify "Received Pulse Events", and any event we need to publish in our future domain.

Consider this snippet:

``` java

public interface DomainEventBus {

  <T> void publish(T domainEvent);
}

```   

Check Spring's [ApplicationEventPublisher](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/ApplicationEventPublisher.html) to implement the Domain Event Bus.

Now:

* Create a specific Event that reflects a "Received Pulse Event".
* Publish a received Pulse event to the domain event bus for each reaceived Pulse.
* Check the Spring's [@EventListener](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/event/EventListener.html) annotation.
* Develop an Observer consuming the emmitted events, which has to log the received events into our custom file logger with specific format.  

(solved at branch: workshop/03-solved)

### 4 Convert service errors into Pulses

When any error occurs under our Controller processing Pulses... what would happen?

* Let's capture the Controller errors and transform then into new Pulses with a SAVE_PULSE_ERROR event. 

(solved at branch: workshop/04-solved)

### 5 Convert unhandled errors into Pulses

But... if we are facing an unexpected exception, or a bad request matched but not processable by our Controller?

* Let's create a Spring's Global Error Handler to capture unhandled request exceptions and transform them into a new Pulse with the UNHANDLED_ERROR event.

(solved at branch: workshop/05-solved) 

### 6 Extra: Performance evaluation

```
npm install -g artillery

artillery run artillery/benchmark.yml
```

More info on [Artillery.io](https://artillery.io)
