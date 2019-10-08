package com.github.alextremp.pulse.domain.pulse.observer;

import com.github.alextremp.pulse.domain.pulse.LogLinePulseMapper;
import com.github.alextremp.pulse.domain.pulse.event.ReceivedPulseEvent;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class LogReceivedPulseObserver {

  private final Logger logger;
  private final LogLinePulseMapper logLinePulseMapper;

  public LogReceivedPulseObserver(Logger logger, LogLinePulseMapper logLinePulseMapper) {
    this.logger = logger;
    this.logLinePulseMapper = logLinePulseMapper;
  }

  public void on(ReceivedPulseEvent receivedPulseEvent) {
    Mono.fromCallable(() -> receivedPulseEvent.getReceivedPulse())
        .flatMap(logLinePulseMapper::pulse2logLine)
        .doOnNext(logger::info)
        .subscribe();
  }
}
