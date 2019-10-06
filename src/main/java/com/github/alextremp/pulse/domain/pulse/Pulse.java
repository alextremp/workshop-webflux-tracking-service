package com.github.alextremp.pulse.domain.pulse;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class Pulse {
  private final String id;
  private final String clientId;
  private final String name;
  private final Map<String, Object> payload;

  public Pulse(String id, String clientId, String name, Map<String, Object> payload) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("Id cannot be empty");
    }
    if (StringUtils.isBlank(clientId)) {
      throw new IllegalArgumentException("Client Id cannot be empty");
    }
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    this.id = id;
    this.clientId = clientId;
    this.name = name;
    this.payload = payload;
  }

  public String getId() {
    return id;
  }

  public String getClientId() {
    return clientId;
  }

  public String getName() {
    return name;
  }

  public Map<String, Object> getPayload() {
    return payload;
  }

  @Override
  public String toString() {
    return "Pulse{" +
        "id='" + id + '\'' +
        ", clientId='" + clientId + '\'' +
        ", name='" + name + '\'' +
        ", payload=" + payload +
        '}';
  }
}
