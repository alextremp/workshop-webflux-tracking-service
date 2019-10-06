package com.github.alextremp.pulse.application.savepulses;

import com.github.alextremp.pulse.application.savepulses.io.SavePulsesRequest;
import com.github.alextremp.pulse.application.savepulses.io.SavePulsesResponse;
import reactor.core.publisher.Mono;

public class SavePulsesUseCase {

  public Mono<SavePulsesResponse> savePulses(SavePulsesRequest request) {
    return Mono.error(new UnsupportedOperationException("Not implemented: SavePulsesUseCase"));
  }
}
