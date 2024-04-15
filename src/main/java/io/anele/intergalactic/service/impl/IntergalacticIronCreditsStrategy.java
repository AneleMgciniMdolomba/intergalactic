package io.anele.intergalactic.service.impl;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.service.IntergalacticTrader;
import io.anele.intergalactic.service.RomanArabicConverter;
import java.util.List;

public class IntergalacticIronCreditsStrategy implements IntergalacticTrader {

  // Calculated from requirements / examples
  private final ArabicSymbol baseIronArabicSymbol = new ArabicSymbol(3890);
  private final RomanArabicConverter converter;

  public IntergalacticIronCreditsStrategy(RomanArabicConverter converter) {
    this.converter = converter;
  }

  @Override
  public ArabicSymbol trade(List<RomanSymbol> symbol)
      throws InvalidIntergalacticTradingPromptException {

    return convertUnitsAndGet(symbol, baseIronArabicSymbol, converter);
  }
}
