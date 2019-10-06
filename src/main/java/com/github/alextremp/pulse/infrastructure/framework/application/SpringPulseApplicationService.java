package com.github.alextremp.pulse.infrastructure.framework.application;

import com.github.alextremp.pulse.application.PulseApplicationService;
import com.github.alextremp.pulse.application.savepulses.SavePulsesUseCase;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SpringPulseApplicationService implements PulseApplicationService {

  private final SavePulsesUseCase createEventsUseCase;

  @Autowired
  public SpringPulseApplicationService(SavePulsesUseCase createEventsUseCase) {
    this.createEventsUseCase = createEventsUseCase;
  }

  @Override
  public Mono<SavePulsesResponse> savePulses(SavePulsesRequest request) {
    return createEventsUseCase.savePulses(request);
  }
}
