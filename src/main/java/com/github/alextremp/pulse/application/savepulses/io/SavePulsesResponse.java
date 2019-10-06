package com.github.alextremp.pulse.application.savepulses.io;

public class SavePulsesResponse {

  private String code;

  public SavePulsesResponse() {
  }

  public SavePulsesResponse(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return "SavePulsesResponse{" +
        "code='" + code + '\'' +
        '}';
  }
}
