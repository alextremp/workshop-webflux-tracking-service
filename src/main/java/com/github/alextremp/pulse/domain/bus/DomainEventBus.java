package com.github.alextremp.pulse.domain.bus;

public interface DomainEventBus {

  <T> void publish(T domainEvent);
}
