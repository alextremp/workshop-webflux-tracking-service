package com.github.alextremp.pulse.domain.pulse.error;

public class MandatoryFieldException extends IllegalArgumentException {

  private static final String SUFFIX = " should not be empty";
  
  public MandatoryFieldException(String field) {
    super(field + SUFFIX);
  }
}
