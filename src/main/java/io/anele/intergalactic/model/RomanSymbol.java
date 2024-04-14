package io.anele.intergalactic.model;

import java.util.Arrays;

public class RomanSymbol {

  private char id;
  private int value;
  private int maxSequentialAllowed;
  private char[] allowedSubtractions;
  private char next;

  private RomanSymbol() {
  }

  public char getId() {
    return id;
  }

  public int getValue() {
    return value;
  }

  public int getMaxSequentialAllowed() {
    return maxSequentialAllowed;
  }

  public char[] getAllowedSubtractions() {
    return allowedSubtractions;
  }

  public char getNext() {
    return next;
  }

  @Override
  public String toString() {
    return "RomanSymbol{" +
        "id=" + id +
        ", value=" + value +
        ", maxSequentialAllowed=" + maxSequentialAllowed +
        ", allowedSubtractions=" + Arrays.toString(allowedSubtractions) +
        ", next=" + next +
        '}';
  }

  public static class RomanSymbolBuilder {
    private char id;
    private int value;
    private int maxSequentialAllowed;
    private char[] allowedSubtractions;
    private char next;

    private RomanSymbolBuilder(){}
    public static RomanSymbolBuilder builder() {
      return new RomanSymbolBuilder();
    }

    public RomanSymbolBuilder romanIdentifier(char id) {
      this.id = id;
      return this;
    }

    public RomanSymbolBuilder value(int value) {
      this.value = value;
      return this;
    }
    public RomanSymbolBuilder maxSequentialAllowed(int maxAllowed) {
      this.maxSequentialAllowed = maxAllowed;
      return this;
    }
    public RomanSymbolBuilder allowedSubtractions(char[] allowedSubtractions) {
      this.allowedSubtractions = allowedSubtractions;
      return this;
    }

    public RomanSymbolBuilder nextSequentialId(char next) {
      this.next = next;
      return this;
    }
    public RomanSymbol build() {
      RomanSymbol romanSymbol = new RomanSymbol();
      romanSymbol.id = this.id;
      romanSymbol.value = this.value;
      romanSymbol.maxSequentialAllowed = this.maxSequentialAllowed;
      romanSymbol.allowedSubtractions = this.allowedSubtractions;
      romanSymbol.next = this.next;

      return romanSymbol;
    }
  }


}
