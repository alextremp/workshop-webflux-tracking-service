package com.github.alextremp.pulse.application.savepulses.io;

import java.util.LinkedHashMap;
import java.util.Map;

public class PulseEvent {
  private String name;
  private Map<String, Object> payload = new LinkedHashMap<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, Object> getPayload() {
    return payload;
  }

  public void setPayload(Map<String, Object> payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return "PulseEvent{" +
        "name='" + name + '\'' +
        ", payload=" + payload +
        '}';
  }
}
