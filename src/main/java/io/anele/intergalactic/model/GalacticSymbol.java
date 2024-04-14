package io.anele.intergalactic.model;

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
