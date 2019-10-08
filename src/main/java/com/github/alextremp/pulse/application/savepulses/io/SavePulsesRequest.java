package com.github.alextremp.pulse.application.savepulses.io;

import java.util.Arrays;

public class SavePulsesRequest {
  private String id;
  private PulseEvent[] events;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PulseEvent[] getEvents() {
    return events;
  }

  public void setEvents(PulseEvent... events) {
    this.events = events;
  }

  @Override
  public String toString() {
    return "SavePulsesRequest{" +
        "id='" + id + '\'' +
        ", events=" + Arrays.toString(events) +
        '}';
  }
}
