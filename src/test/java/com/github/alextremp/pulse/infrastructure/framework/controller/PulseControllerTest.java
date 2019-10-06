package com.github.alextremp.pulse.infrastructure.framework.controller;

import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

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
}