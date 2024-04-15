package io.anele.intergalactic.model;

public class ArabicSymbol {

  private double value;

  public ArabicSymbol() {
  }

  public ArabicSymbol(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  public void calculateCredits(double costPerUnit) {
    this.value = value * costPerUnit;
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
