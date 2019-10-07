package com.github.alextremp.pulse.domain.pulse;

import reactor.core.publisher.Mono;

public interface PulseIdService {

  Mono<String> generateId();
}
