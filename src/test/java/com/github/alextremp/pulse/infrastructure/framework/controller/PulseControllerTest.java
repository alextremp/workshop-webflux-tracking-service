package com.github.alextremp.pulse.infrastructure.framework.controller;

import com.github.alextremp.pulse.application.savepulses.io.PulseEvent;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PulseControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void badRequest() {
    ResponseEntity<SavePulsesResponse> response = restTemplate.postForEntity("/", null, SavePulsesResponse.class);
    assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void savePulseResponseIsError() {
    SavePulsesResponse response = restTemplate.postForObject("/", new SavePulsesRequest(), SavePulsesResponse.class);
    assertEquals(response.getCode(), "ERROR");
  }

  @Test
  public void shouldProcessAPulseRequest() {
    Function<Integer, PulseEvent> event = id -> {
      PulseEvent pulseEvent = new PulseEvent();
      pulseEvent.setName("event" + id);
      pulseEvent.getPayload().put("k1", "v1" + id);
      pulseEvent.getPayload().put("k2", "v2" + id);
      return pulseEvent;
    };
    SavePulsesRequest request = new SavePulsesRequest();
    request.setId("shouldProcessAPulseRequest");
    request.setEvents(new PulseEvent[]{event.apply(1), event.apply(2)});

    SavePulsesResponse response = restTemplate.postForObject("/", request, SavePulsesResponse.class);
    assertEquals(response.getCode(), "PROCESSED");
  }

  @Test
  public void shouldErrorAPulseRequestIfAnyPulseEventIsIncomplete() {
    Function<Integer, PulseEvent> event = id -> {
      PulseEvent pulseEvent = new PulseEvent();
      pulseEvent.setName(id < 3 ? "event" + id : null);
      pulseEvent.getPayload().put("k1", "v1" + id);
      pulseEvent.getPayload().put("k2", "v2" + id);
      return pulseEvent;
    };
    SavePulsesRequest request = new SavePulsesRequest();
    request.setId("shouldProcessAPulseRequest");
    request.setEvents(new PulseEvent[]{
        event.apply(1),
        event.apply(2),
        event.apply(3)
    });

    SavePulsesResponse response = restTemplate.postForObject("/", request, SavePulsesResponse.class);
    assertEquals(response.getCode(), "ERROR");
    assertTrue(StringUtils.contains(response.getMessage(), "should not be empty"));
  }
}