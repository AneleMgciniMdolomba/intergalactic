package io.anele.intergalactic.validations;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.exceptions.InvalidRomanSymbolsException;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputValidator {

  private static final List<Character> MAX_ALLOWED_OCCURRENCES =
      new ArrayList<>(Arrays.asList('I', 'X', 'C', 'M'));
  private static final List<Character> NO_REPEATS_ALLOWED =
      new ArrayList<>(Arrays.asList('D', 'L', 'V'));

  // Do not instantiate
  private InputValidator() {
    throw new IllegalArgumentException("Utility / Helper class. Do not instantiate");
  }

  public static void isInputValid(List<RomanSymbol> symbol) {
    if (symbol == null || symbol.isEmpty()) {
      throw new InvalidIntergalacticTradingPromptException("Can not trade with this currency.");
    }
  }

  public static void validateNonAllowedRepeat(List<RomanSymbol> symbols)
      throws InvalidRomanSymbolsException {
    basicInputValidation(symbols);

    for (int index = 0; index < symbols.size(); index++) {
      var symbol = symbols.get(index);

      if (NO_REPEATS_ALLOWED.contains(symbol.getId())) {
        var occurences = 0;

        for (int innerIndex = index; innerIndex < symbols.size(); innerIndex++) {
          var romanSymbol = symbols.get(innerIndex);
          if (symbol.getId() == romanSymbol.getId()) {
            occurences++;
            if (occurences > 1) {
              throw new InvalidRomanSymbolsException("D,L or V can never repeat");
            }
          }
        }
      }
    }
  }

  public static void validateAllowedMaxRepeats(List<RomanSymbol> symbols) {
    basicInputValidation(symbols);

    for (int index = 0; index < symbols.size(); index++) {
      var symbol = symbols.get(index);

      if (MAX_ALLOWED_OCCURRENCES.contains(symbol.getId())) {
        var occurences = 0;

        for (int innerIndex = index; innerIndex < symbols.size(); innerIndex++) {
          var romanSymbol = symbols.get(innerIndex);

          if (symbol.getId() == romanSymbol.getId()) {
            occurences++;

            if (occurences > symbol.getMaxSequentialAllowed()) {
              throw new InvalidRomanSymbolsException("Max Sequential Allowed exceeded.");
            }
          } else {
            break;
          }
        }
      }
    }
  }

  private static void basicInputValidation(List<RomanSymbol> symbols) {
    if (symbols == null || symbols.isEmpty()) {
      throw new InvalidRomanSymbolsException("Cannot convert empty roman numerals");
    }

  }
}
