package com.github.alextremp.pulse.infrastructure.framework.configuration;

import com.github.alextremp.pulse.application.savepulses.SavePulsesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  SavePulsesUseCase createEventsUseCase() {
    return new SavePulsesUseCase();
  }
}
