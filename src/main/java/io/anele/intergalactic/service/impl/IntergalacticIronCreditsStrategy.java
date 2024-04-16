package io.anele.intergalactic.service.impl;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.service.IntergalacticTrader;
import io.anele.intergalactic.service.RomanArabicConverter;
import java.util.List;

public class IntergalacticIronCreditsStrategy implements IntergalacticTrader {

  // Calculated from requirements / examples - refer to notes on
  // how to calculate unit cost per commodity
  private final ArabicSymbol costPerUnit;
  private final RomanArabicConverter converter;

  public IntergalacticIronCreditsStrategy(RomanArabicConverter converter, double costPerUnit) {
    this.converter = converter;
    this.costPerUnit = new ArabicSymbol(costPerUnit);
  }

  @Override
  public ArabicSymbol trade(List<RomanSymbol> symbol)
      throws InvalidIntergalacticTradingPromptException {

    return this.convertUnitsAndGet(symbol, costPerUnit, converter);
  }
}
