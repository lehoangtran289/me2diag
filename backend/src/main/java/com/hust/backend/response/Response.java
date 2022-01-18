package com.hust.backend.response;

import lombok.Data;

@Data
public class Response {
  protected Status status;
  protected String message;

  public enum Status {
    OK,
    ERROR
  }
}
