package io.anele.intergalactic.config;

import io.anele.intergalactic.exceptions.RomanSymbolNotFoundException;
import io.anele.intergalactic.model.GalacticSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.model.RomanSymbol.RomanSymbolBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to configure intergalactic trading units and symbols Initializes
 * @{@link RomanSymbol} and configure @{@link GalacticSymbol} at run time.
 */
public class GalaxyTradingConfigurationProperties {

  private final List<RomanSymbol> romanSymbols = new ArrayList<>(7);
  private List<GalacticSymbol> galacticSymbols = new ArrayList<>();

  public GalaxyTradingConfigurationProperties() {
    initializeData();
  }

  private void initializeData() {
    initRomanSymbols();
  }

  private void initRomanSymbols() {
    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('I').value(1).maxSequentialAllowed(3)
        .allowedSubtractions(new char[]{'V', 'X'}).build());

    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('V').value(5).build());

    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('X').value(10).maxSequentialAllowed(3)
        .allowedSubtractions(new char[]{'L', 'C'}).build());

    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('L').value(50).build());

    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('C').value(100).maxSequentialAllowed(3)
        .allowedSubtractions(new char[]{'D', 'M'}).build());

    this.romanSymbols.add(RomanSymbolBuilder.builder()
        .romanIdentifier('D').value(500)
        .build());

    this.romanSymbols.add(RomanSymbolBuilder.builder()
        .romanIdentifier('M').value(1000).maxSequentialAllowed(3)
        .build());
  }

  public List<RomanSymbol> getRomanSymbols() {
    return romanSymbols;
  }

  public void setGalacticSymbols(
      List<GalacticSymbol> galacticSymbols) {
    this.galacticSymbols = galacticSymbols;
  }

  public List<GalacticSymbol> getGalacticSymbols() {
    return galacticSymbols;
  }

  public RomanSymbol search(final char romanSymbol) {
    return this.romanSymbols.stream().filter(roman -> roman.getId() == romanSymbol)
        .findFirst().orElseThrow(
            () -> new RomanSymbolNotFoundException("Roman Symbol " + romanSymbol + " not found."));
  }

  public GalacticSymbol search(final String symbol) {
    return this.galacticSymbols.stream()
        .filter(galacticSymbol -> galacticSymbol.getId().equals(symbol))
        .findFirst().orElseThrow(RuntimeException::new);
  }

  public void addGalacticSymbol(String galacticSymbol, String romanSymbol) {
    if (galacticSymbols == null) {
      galacticSymbols = new ArrayList<>();
    }

    this.galacticSymbols.add(new GalacticSymbol(galacticSymbol, search(romanSymbol.charAt(0))));
  }
}
