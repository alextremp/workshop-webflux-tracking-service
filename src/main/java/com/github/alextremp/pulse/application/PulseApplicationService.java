package com.github.alextremp.pulse.application;

import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import reactor.core.publisher.Mono;

public interface PulseApplicationService {

  Mono<SavePulsesResponse> savePulses(SavePulsesRequest request);
}
