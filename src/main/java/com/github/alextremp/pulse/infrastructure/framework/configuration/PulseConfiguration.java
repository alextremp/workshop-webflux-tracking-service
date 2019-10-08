package com.github.alextremp.pulse.infrastructure.framework.configuration;

import com.github.alextremp.pulse.domain.pulse.LogLinePulseMapper;
import com.github.alextremp.pulse.domain.pulse.PulseFactory;
import com.github.alextremp.pulse.domain.pulse.PulseIdService;
import com.github.alextremp.pulse.domain.pulse.observer.LogReceivedPulseObserver;
import com.github.alextremp.pulse.infrastructure.pulse.UUIDPulseIdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

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

  @Bean
  LogLinePulseMapper logLinePulseMapper() {
    return new LogLinePulseMapper();
  }

  @Bean
  LogReceivedPulseObserver logReceivedPulseObserver(LogLinePulseMapper logLinePulseMapper) {
    return new LogReceivedPulseObserver(Logger.getLogger("PULSE_EVENT_LOGGER"), logLinePulseMapper);
  }

}
