package com.github.alextremp.pulse.infrastructure.framework.bus;

import com.github.alextremp.pulse.domain.pulse.event.ReceivedPulseEvent;
import com.github.alextremp.pulse.domain.pulse.observer.LogReceivedPulseObserver;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class PulseEventListener {

  private final LogReceivedPulseObserver logReceivedPulseObserver;

  PulseEventListener(LogReceivedPulseObserver logReceivedPulseObserver) {
    this.logReceivedPulseObserver = logReceivedPulseObserver;
  }

  @EventListener
  void handle(ReceivedPulseEvent receivedPulseEvent) {
    logReceivedPulseObserver.on(receivedPulseEvent);
  }
}
