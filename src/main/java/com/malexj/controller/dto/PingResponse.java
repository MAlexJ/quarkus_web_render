package com.malexj.controller.dto;

public record PingResponse(String message) {

  private static final String MESSAGE = "pong";

  public PingResponse() {
    this(MESSAGE);
  }
}
