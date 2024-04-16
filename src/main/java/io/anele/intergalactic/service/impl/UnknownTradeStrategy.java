package io.anele.intergalactic.service.impl;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.service.IntergalacticTrader;
import java.util.List;

public class UnknownTradeStrategy implements IntergalacticTrader {

  @Override
  public ArabicSymbol trade(List<RomanSymbol> symbol) {
    throw new InvalidIntergalacticTradingPromptException("I have no idea what you are talking about");
  }
}
