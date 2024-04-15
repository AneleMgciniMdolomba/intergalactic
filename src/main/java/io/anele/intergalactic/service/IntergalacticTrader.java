package io.anele.intergalactic.service;

import static io.anele.intergalactic.validations.InputValidator.isInputValid;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.List;

public interface IntergalacticTrader {

  ArabicSymbol trade(List<RomanSymbol> symbol) throws InvalidIntergalacticTradingPromptException;

  default ArabicSymbol convertUnitsAndGet(List<RomanSymbol> symbol,
      ArabicSymbol baseIronArabicSymbol, RomanArabicConverter converter) {
    isInputValid(symbol);

    StringBuilder builder = new StringBuilder();
    for (RomanSymbol roman : symbol) {
      builder.append(roman.getId());
    }

    final var input = builder.toString();
    ArabicSymbol credits = new ArabicSymbol(baseIronArabicSymbol.getValue());
    credits.addValue(converter.convert(input).getValue());

    return credits;
  }
}
