package com.github.alextremp.pulse.infrastructure.framework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alextremp.pulse.application.PulseApplicationService;
import com.github.alextremp.pulse.application.savepulses.io.PulseEvent;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class PulseController {

  private final Logger LOG = Logger.getLogger(PulseController.class.getName());
  private final PulseApplicationService pulseApplicationService;
  private final ObjectMapper objectMapper;

  @Autowired
  public PulseController(PulseApplicationService pulseApplicationService, ObjectMapper objectMapper) {
    this.pulseApplicationService = pulseApplicationService;
    this.objectMapper = objectMapper;
  }

  @PostMapping
  public Mono<SavePulsesResponse> savePulses(@RequestBody String requestBody) {
    return Mono.fromCallable(() -> objectMapper.readValue(requestBody, SavePulsesRequest.class))
        .subscribeOn(Schedulers.parallel())
        .flatMap(pulseApplicationService::savePulses)
        .onErrorResume(error -> onSavePulsesError(error, requestBody))
        .doOnNext(savePulsesResponse -> LOG.fine(() -> "RESPONSE::" + savePulsesResponse));
  }

  private Mono<SavePulsesResponse> onSavePulsesError(Throwable error, String requestBody) {
    return Mono.fromCallable(() -> errorSavePulsesRequest(error, requestBody))
        .doOnNext(savePulsesResponse -> LOG.fine(() -> "ERROR::" + error.getMessage() + " - Received [" + requestBody + "]"))
        .flatMap(pulseApplicationService::savePulses)
        .map(response -> SavePulsesResponse.error(error));
  }

  private SavePulsesRequest errorSavePulsesRequest(Throwable error, String requestBody) {
    PulseEvent pulseEvent = new PulseEvent();
    pulseEvent.setName("SAVE_PULSE_ERROR");
    pulseEvent.getPayload().put("error", error);
    pulseEvent.getPayload().put("requestBody", requestBody);
    SavePulsesRequest request = new SavePulsesRequest();
    request.setId(PulseController.class.getSimpleName());
    request.setEvents(pulseEvent);
    return request;
  }
}
