package io.anele.intergalactic.service.impl;

import io.anele.intergalactic.config.ConfigProperties;
import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.service.IntergalacticTrader;
import io.anele.intergalactic.service.RomanArabicConverter;
import java.util.List;

public class IntergalacticToRomanStrategy implements IntergalacticTrader {

  private final ConfigProperties configProperties;
  private final RomanArabicConverter converter;

  public IntergalacticToRomanStrategy(final ConfigProperties configProperties,
      final RomanArabicConverter converter) {
    this.configProperties = configProperties;
    this.converter = converter;
  }

  @Override
  public ArabicSymbol trade(List<RomanSymbol> symbol)
      throws InvalidIntergalacticTradingPromptException {
    if (symbol == null || symbol.isEmpty()) {
      throw new InvalidIntergalacticTradingPromptException("Can not trade with this currency.");
    }

    StringBuilder builder = new StringBuilder();
    for (RomanSymbol roman : symbol) {
      builder.append(roman.getId());
    }

    final var input = builder.toString();
    return converter.convert(input);
  }
}
