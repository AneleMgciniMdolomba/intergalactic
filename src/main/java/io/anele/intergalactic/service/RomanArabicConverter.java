package io.anele.intergalactic.service;

import io.anele.intergalactic.config.ConfigProperties;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.ArrayList;
import java.util.List;

public class RomanArabicConverter {

  private final ConfigProperties configProperties;

  public RomanArabicConverter(ConfigProperties configProperties) {
    this.configProperties = configProperties;
  }
  public ArabicSymbol convert(String roman) {
    var romanSymbols = roman.toCharArray();

    List<RomanSymbol> input = asRomanSymbols(romanSymbols); // throw runtime exception

    return toArabic(input);
  }
  private ArabicSymbol toArabic(List<RomanSymbol> romanSymbols) {
    int value = 0;

    for (int index = 0; index < romanSymbols.size(); index++) {
      var current = romanSymbols.get(index);

      // Check if current == last element
      if(index + 1 >= romanSymbols.size()) {
        value += current.getValue();
      } else {
        // add or subtract
        var next = romanSymbols.get(index+1);

        var allowed = next.getAllowedSubtractions();
        if(allowed != null) {

          for (char allowedSubtraction : next.getAllowedSubtractions()) {
            System.out.println(allowedSubtraction);
          }
        }

        value += current.getValue();
      }
    }

    return new ArabicSymbol(value);
  }

  private List<RomanSymbol> asRomanSymbols(char[] romanSymbols) {
    List<RomanSymbol> asRomanSymbols = new ArrayList<>(romanSymbols.length);

    for (char romanSymbol : romanSymbols) {
      asRomanSymbols.add(configProperties.search(romanSymbol));
    }

    return asRomanSymbols;
  }

}
