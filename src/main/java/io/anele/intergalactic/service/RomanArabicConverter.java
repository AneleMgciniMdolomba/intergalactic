package io.anele.intergalactic.service;

import io.anele.intergalactic.config.ConfigProperties;
import io.anele.intergalactic.exceptions.InvalidRomanSymbolsException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RomanArabicConverter {

  private final ConfigProperties configProperties;

  public RomanArabicConverter(ConfigProperties configProperties) {
    this.configProperties = configProperties;
  }

  public ArabicSymbol convert(String roman) {
    var romanSymbols = roman.toCharArray();

    List<RomanSymbol> input = asRomanSymbols(romanSymbols); // throw runtime exception
    validateInput(input);

    return toArabic(input);
  }

  private void validateInput(List<RomanSymbol> input) throws InvalidRomanSymbolsException {
    Map<Character, Integer> repeats = new HashMap<>();
    int countForL =1, countForD =1 , countForV = 1;

    if (input == null || input.isEmpty()) {
      throw new InvalidRomanSymbolsException("Cannot convert empty roman numerals");
    }

    // D, L & V can never be repeated
    for (RomanSymbol current : input) {
      if ('D' == current.getId()) {
        countForD += 1;
        repeats.put('D', countForD);
      }

      if ('L' == current.getId()) {
        countForL += 1;
        repeats.put('L', countForL);
      }

      if ('V' == current.getId()) {
        countForV += 1;
        repeats.put('V', countForV);
      }
    }

    if ((repeats.get('D') != null && repeats.get('D') > 1) ||
        (repeats.get('V') != null && repeats.get('V') > 1) ||
        (repeats.get('L') != null && repeats.get('L') > 1)) {
      throw new InvalidRomanSymbolsException("D,L or V can never repeat");
    }

  }

  private ArabicSymbol toArabic(List<RomanSymbol> romanSymbols) {
    int value = 0;
    int processIndex = 0;

    for (int index = 0; index < romanSymbols.size(); index++) {
      // terminate fast
      if (processIndex >= romanSymbols.size()) {
        break;
      }

      var current = romanSymbols.get(processIndex);

      // Check if current == last element
      if (processIndex + 1 >= romanSymbols.size()) {
        value += current.getValue();
      } else {
        // Next element
        var next = romanSymbols.get(processIndex + 1);

        // Check if next element > current element
        if (next.getValue() > current.getValue()) {
          // this says subtract current from next
          // but before, check if that is allowed to happen
          // set to skip next element in the loop as it has
          // been processed

          boolean subtractionAllowed = false; // convert allowed to list
          for (char allowedSubtraction : current.getAllowedSubtractions()) {
            if (allowedSubtraction == next.getId()) {
              subtractionAllowed = true;
              break;
            }
          }
          if (subtractionAllowed) {
            value += next.getValue() - current.getValue();
            processIndex = processIndex + 2;
          } else {
            throw new InvalidRomanSymbolsException(
                "Roman Symbols sequence seems invalid. Please check.");
          }
        } else {
          value += current.getValue();
          processIndex = index + 1;

        }
      }
    }

    return new ArabicSymbol(value);
  }

  private List<RomanSymbol> asRomanSymbols(char[] romanSymbols) {
    List<RomanSymbol> asRomanSymbols = new LinkedList<>();

    for (char romanSymbol : romanSymbols) {
      asRomanSymbols.add(configProperties.search(romanSymbol));
    }

    return asRomanSymbols;
  }

}
