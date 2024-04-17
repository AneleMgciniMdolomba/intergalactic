package io.anele.intergalactic.model;

/**
 * Represents intergalactic Symbol. Each Galactic Symbol has ONLY ONE @{@link RomanSymbol}
 * used during trading.
 */
public class GalacticSymbol {

  private String id;
  private RomanSymbol romanSymbol;

  public GalacticSymbol() {
  }

  public GalacticSymbol(String id, RomanSymbol romanSymbol) {
    this.id = id;
    this.romanSymbol = romanSymbol;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RomanSymbol getRomanSymbol() {
    return romanSymbol;
  }

  public void setRomanSymbol(RomanSymbol romanSymbol) {
    this.romanSymbol = romanSymbol;
  }

  @Override
  public String toString() {
    return "GalacticSymbol{" +
        "id='" + id + '\'' +
        ", romanSymbol=" + romanSymbol +
        '}';
  }
}
