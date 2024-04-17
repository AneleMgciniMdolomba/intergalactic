package io.anele.intergalactic.validations;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.exceptions.InvalidRomanSymbolsException;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    Set<Character> repeatedSymbols = new HashSet<>();

    for (RomanSymbol symbol : symbols) {
      char id = symbol.getId();

      if (NO_REPEATS_ALLOWED.contains(id)) {
        if (repeatedSymbols.contains(id)) {
          throw new InvalidRomanSymbolsException("D, L, or V can never repeat.");
        }
        repeatedSymbols.add(id);
      }
    }
  }

  public static void validateAllowedMaxRepeats(List<RomanSymbol> symbols) {
    basicInputValidation(symbols);

    Map<Character, Integer> symbolCounts = new HashMap<>();

    RomanSymbol previous = symbols.get(0);
    for (RomanSymbol symbol : symbols) {
      // reset count for symbolCounts on symbols change
      if(symbol.getId() != previous.getId()) {
        symbolCounts.put(previous.getId(), 0);

        previous = symbol;
      }

      if (MAX_ALLOWED_OCCURRENCES.contains(symbol.getId())) {
        char id = symbol.getId();
        int maxSequentialAllowed = symbol.getMaxSequentialAllowed();
        int occurrences = symbolCounts.getOrDefault(id, 0);

        // Increment count
        symbolCounts.put(id, occurrences + 1);

        if (symbolCounts.get(id) > maxSequentialAllowed) {
          throw new InvalidRomanSymbolsException("Max Sequential Allowed exceeded.");
        }
      } else {
        // Reset count for symbols not in MAX_ALLOWED_OCCURRENCES
        symbolCounts.put(symbol.getId(), 0);
      }
    }
  }

  private static void basicInputValidation(List<RomanSymbol> symbols) {
    if (symbols == null || symbols.isEmpty()) {
      throw new InvalidRomanSymbolsException("Cannot convert empty roman numerals");
    }

  }
}
