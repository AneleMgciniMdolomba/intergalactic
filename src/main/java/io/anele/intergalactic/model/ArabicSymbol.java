package io.anele.intergalactic.model;

public class ArabicSymbol {

  private int value;

  public ArabicSymbol() {
  }

  public ArabicSymbol(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "ArabicSymbol{" +
        "value=" + value +
        '}';
  }
}
