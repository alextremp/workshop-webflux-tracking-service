package com.github.alextremp.pulse.application.savepulses;

import com.github.alextremp.pulse.application.savepulses.io.PulseEvent;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import com.github.alextremp.pulse.domain.pulse.LogLinePulseMapper;
import com.github.alextremp.pulse.domain.pulse.PulseFactory;
import com.github.alextremp.pulse.domain.pulse.error.MandatoryFieldException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class SavePulsesUseCase {

  private final PulseFactory pulseFactory;
  private final LogLinePulseMapper logLinePulseMapper;

  private static final Logger LOG = Logger.getLogger(SavePulsesUseCase.class.getName());

  public SavePulsesUseCase(PulseFactory pulseFactory, LogLinePulseMapper logLinePulseMapper) {
    this.pulseFactory = pulseFactory;
    this.logLinePulseMapper = logLinePulseMapper;
  }

  public Mono<SavePulsesResponse> savePulses(SavePulsesRequest request) {
    return Mono.fromCallable(() -> notEmptyPulseEvents(request))
        .flux()
        .flatMap(Flux::fromArray)
        .flatMap(pulseEvent -> pulseFactory.createPulse(
            request.getId(),
            pulseEvent.getName(),
            pulseEvent.getPayload()
        ))
        .flatMap(logLinePulseMapper::pulse2logLine)
        .doOnNext(line -> LOG.info(line))
        .collectList()
        .map(pulses -> SavePulsesResponse.processed());
  }

  private PulseEvent[] notEmptyPulseEvents(SavePulsesRequest request) {
    if (request.getEvents() == null || request.getEvents().length == 0) {
      throw new MandatoryFieldException("Request events");
    }
    return request.getEvents();
  }
}
