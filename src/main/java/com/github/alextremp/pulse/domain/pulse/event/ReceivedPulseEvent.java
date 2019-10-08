package com.github.alextremp.pulse.domain.pulse.event;

import com.github.alextremp.pulse.domain.pulse.Pulse;

public class ReceivedPulseEvent {

  private final Pulse receivedPulse;

  public ReceivedPulseEvent(Pulse receivedPulse) {
    this.receivedPulse = receivedPulse;
  }

  public Pulse getReceivedPulse() {
    return receivedPulse;
  }
}
