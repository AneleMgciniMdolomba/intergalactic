package io.anele.intergalactic.service.impl;

import static io.anele.intergalactic.validations.InputValidator.isInputValid;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.service.IntergalacticTrader;
import io.anele.intergalactic.service.RomanArabicConverter;
import java.util.List;

public class IntergalacticToRomanStrategy implements IntergalacticTrader {

  private final RomanArabicConverter converter;

  public IntergalacticToRomanStrategy(final RomanArabicConverter converter) {
    this.converter = converter;
  }

  @Override
  public ArabicSymbol trade(List<RomanSymbol> symbol)
      throws InvalidIntergalacticTradingPromptException {

    isInputValid(symbol);

    StringBuilder builder = new StringBuilder();
    for (RomanSymbol roman : symbol) {
      builder.append(roman.getId());
    }

    final var input = builder.toString();
    return converter.convert(input);
  }

}
