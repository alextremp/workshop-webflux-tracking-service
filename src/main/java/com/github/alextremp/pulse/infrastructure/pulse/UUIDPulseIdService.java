package com.github.alextremp.pulse.infrastructure.pulse;

import com.github.alextremp.pulse.domain.pulse.PulseIdService;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class UUIDPulseIdService implements PulseIdService {

  @Override
  public Mono<String> generateId() {
    return Mono.fromCallable(() -> UUID.randomUUID())
        .map(uuid -> uuid.toString());
  }
}
