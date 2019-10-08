package com.github.alextremp.pulse.infrastructure.framework.bus;

import com.github.alextremp.pulse.domain.bus.DomainEventBus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringDomainEventBus implements DomainEventBus {

  private final ApplicationEventPublisher publisher;

  public SpringDomainEventBus(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public <T> void publish(T domainEvent) {
    publisher.publishEvent(domainEvent);
  }
}
