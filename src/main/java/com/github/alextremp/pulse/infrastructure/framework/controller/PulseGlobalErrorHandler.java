package com.github.alextremp.pulse.infrastructure.framework.controller;

import com.github.alextremp.pulse.application.PulseApplicationService;
import com.github.alextremp.pulse.application.savepulses.io.PulseEvent;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class PulseGlobalErrorHandler {

  private static final Logger LOG = Logger.getLogger(PulseGlobalErrorHandler.class.getName());

  private final PulseApplicationService pulseApplicationService;

  @Autowired
  public PulseGlobalErrorHandler(PulseApplicationService pulseApplicationService) {
    this.pulseApplicationService = pulseApplicationService;
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<SavePulsesResponse>> handleCustomException(Exception error) {
    return Mono.fromCallable(() -> errorToPulse(error))
        .doOnNext(savePulsesRequest -> LOG.log(Level.SEVERE, "ERROR [" + error + "] " + savePulsesRequest))
        .flatMap(pulseApplicationService::savePulses)
        .map(response -> toResponseEntity(error, response));
  }

  private ResponseEntity<SavePulsesResponse> toResponseEntity(Exception error, SavePulsesResponse response) {
    if (ResponseStatusException.class.isAssignableFrom(error.getClass())) {
      return ResponseEntity.status(((ResponseStatusException) error).getStatus()).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

  }

  private SavePulsesRequest errorToPulse(Exception error) {
    PulseEvent event = new PulseEvent();
    event.setName("UNHANDLED_ERROR");
    event.getPayload().put("error", error);
    SavePulsesRequest pulse = new SavePulsesRequest();
    pulse.setId(PulseGlobalErrorHandler.class.getSimpleName());
    pulse.setEvents(event);
    return pulse;
  }
}
