package com.github.alextremp.pulse.infrastructure.framework.configuration;

import com.github.alextremp.pulse.domain.pulse.PulseFactory;
import com.github.alextremp.pulse.domain.pulse.PulseIdService;
import com.github.alextremp.pulse.infrastructure.pulse.UUIDPulseIdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PulseConfiguration {

  @Bean
  PulseFactory pulseFactory(PulseIdService pulseIdService) {
    return new PulseFactory(pulseIdService);
  }

  @Bean
  PulseIdService pulseIdService() {
    return new UUIDPulseIdService();
  }

}
