package io.anele.intergalactic.validations;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.List;

public class InputValidator {

  // Do not instantiate
  private InputValidator() {
    throw new IllegalArgumentException("Utility / Helper class. Do not instantiate");
  }
  public static void isInputValid(List<RomanSymbol> symbol) {
    if (symbol == null || symbol.isEmpty()) {
      throw new InvalidIntergalacticTradingPromptException("Can not trade with this currency.");
    }
  }
}
