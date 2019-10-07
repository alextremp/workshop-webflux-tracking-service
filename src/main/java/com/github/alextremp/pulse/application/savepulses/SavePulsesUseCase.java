package com.github.alextremp.pulse.application.savepulses;

import com.github.alextremp.pulse.application.savepulses.io.PulseEvent;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import com.github.alextremp.pulse.domain.pulse.PulseFactory;
import com.github.alextremp.pulse.domain.pulse.error.MandatoryFieldException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SavePulsesUseCase {

  private final PulseFactory pulseFactory;

  public SavePulsesUseCase(PulseFactory pulseFactory) {
    this.pulseFactory = pulseFactory;
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
