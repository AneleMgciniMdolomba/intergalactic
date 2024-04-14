package io.anele.intergalactic.config;

import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.model.RomanSymbol.RomanSymbolBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to configure some data
 * upfront to help with the program
 */
public class ConfigProperties {

  private final List<RomanSymbol> romanSymbols = new ArrayList<>(7);

  public ConfigProperties() {
  }

  public void initializeData(){
    initRomanSymbols();
  }

  private void initRomanSymbols() {
    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('I').value(1).maxSequentialAllowed(3)
        .nextSequentialId('V').allowedSubtractions(new char[]{'V', 'X'}).build());

    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('V').value(5).nextSequentialId('X').build());

    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('X').value(10).maxSequentialAllowed(3)
        .nextSequentialId('L').allowedSubtractions(new char[]{'L', 'C'}).build());

    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('L').value(50).nextSequentialId('C').build());

    this.romanSymbols.add(RomanSymbol.RomanSymbolBuilder.builder()
        .romanIdentifier('C').value(100).maxSequentialAllowed(3)
        .nextSequentialId('D').allowedSubtractions(new char[]{'D', 'M'}).build());

    this.romanSymbols.add(RomanSymbolBuilder.builder()
        .romanIdentifier('D').value(500)
        .nextSequentialId('M').build());

    this.romanSymbols.add(RomanSymbolBuilder.builder()
        .romanIdentifier('M').value(1000).maxSequentialAllowed(3)
        .build());
  }

  public List<RomanSymbol> getRomanSymbols() {
    return romanSymbols;
  }
}
