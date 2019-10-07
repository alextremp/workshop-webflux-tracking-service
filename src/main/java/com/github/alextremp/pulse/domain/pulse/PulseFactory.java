package com.github.alextremp.pulse.domain.pulse;

import reactor.core.publisher.Mono;

import java.util.Map;

public class PulseFactory {

  private final PulseIdService pulseIdService;

  public PulseFactory(PulseIdService pulseIdService) {
    this.pulseIdService = pulseIdService;
  }

  public Mono<Pulse> createPulse(String clientId, String name, Map<String, Object> payload) {
    return pulseIdService.generateId()
        .map(id -> new Pulse(id, clientId, name, payload));
  }
}
