package io.anele.intergalactic.service;

import static io.anele.intergalactic.validations.InputValidator.validateAllowedMaxRepeats;
import static io.anele.intergalactic.validations.InputValidator.validateNonAllowedRepeat;

import io.anele.intergalactic.config.ConfigProperties;
import io.anele.intergalactic.exceptions.InvalidRomanSymbolsException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.LinkedList;
import java.util.List;

public class RomanArabicConverter {

  private final ConfigProperties configProperties;

  public RomanArabicConverter(ConfigProperties configProperties) {
    this.configProperties = configProperties;
  }

  public ArabicSymbol convert(String roman) {
    var romanSymbols = roman.toCharArray();

    List<RomanSymbol> input = asRomanSymbols(romanSymbols); // throw runtime exception
    validateNonAllowedRepeat(input);
    validateAllowedMaxRepeats(input);

    return toArabic(input);
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
        processIndex = processIndex + 1;
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
          processIndex = processIndex + 1;

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
