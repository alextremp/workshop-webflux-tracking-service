package com.github.alextremp.pulse.infrastructure.framework.configuration;

import com.github.alextremp.pulse.application.savepulses.SavePulsesUseCase;
import com.github.alextremp.pulse.domain.pulse.PulseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  SavePulsesUseCase createEventsUseCase(PulseFactory pulseFactory) {
    return new SavePulsesUseCase(pulseFactory);
  }
}
