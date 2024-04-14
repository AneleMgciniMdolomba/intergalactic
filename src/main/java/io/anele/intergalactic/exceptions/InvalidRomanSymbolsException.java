package io.anele.intergalactic.exceptions;

public class InvalidRomanSymbolsException extends RuntimeException {

  public InvalidRomanSymbolsException() {
  }

  public InvalidRomanSymbolsException(String message) {
    super(message);
  }
}
