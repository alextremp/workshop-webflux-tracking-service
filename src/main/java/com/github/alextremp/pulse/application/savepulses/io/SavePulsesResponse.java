package com.github.alextremp.pulse.application.savepulses.io;

public class SavePulsesResponse {

  static final String PROCESSED = "PROCESSED";
  static final String ERROR = "ERROR";

  private String code;
  private String message;

  public SavePulsesResponse() {
  }

  public SavePulsesResponse(String code) {
    this(code, null);
  }

  public SavePulsesResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static SavePulsesResponse processed() {
    return new SavePulsesResponse(PROCESSED);
  }

  public static SavePulsesResponse error(Throwable throwable) {
    return new SavePulsesResponse(ERROR, throwable.getMessage());
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "SavePulsesResponse{" +
        "code='" + code + '\'' +
        ", message='" + message + '\'' +
        '}';
  }
}
