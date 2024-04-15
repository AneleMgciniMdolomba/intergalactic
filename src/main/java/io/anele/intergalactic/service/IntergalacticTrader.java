package io.anele.intergalactic.service;

import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.List;

public interface IntergalacticTrader {

  ArabicSymbol trade(List<RomanSymbol> symbol) throws InvalidIntergalacticTradingPromptException;
}
