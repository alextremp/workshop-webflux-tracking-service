package com.github.alextremp.pulse.domain.pulse;

import com.github.alextremp.pulse.domain.pulse.error.MandatoryFieldException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class Pulse {
  private final String id;
  private final String clientId;
  private final String name;
  private final Map<String, Object> payload;

  public Pulse(String id, String clientId, String name, Map<String, Object> payload) {
    if (StringUtils.isBlank(id)) {
      throw new MandatoryFieldException("Id");
    }
    if (StringUtils.isBlank(clientId)) {
      throw new MandatoryFieldException("Client Id");
    }
    if (StringUtils.isBlank(name)) {
      throw new MandatoryFieldException("Name");
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
