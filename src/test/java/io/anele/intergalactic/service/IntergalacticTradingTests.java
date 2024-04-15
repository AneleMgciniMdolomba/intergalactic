package io.anele.intergalactic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.anele.intergalactic.config.ConfigProperties;
import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.service.impl.IntergalacticGoldCreditsStrategy;
import io.anele.intergalactic.service.impl.IntergalacticIronCreditsStrategy;
import io.anele.intergalactic.service.impl.IntergalacticSilverCreditsStrategy;
import io.anele.intergalactic.service.impl.IntergalacticToRomanStrategy;
import io.anele.intergalactic.service.impl.UnknownTradeStrategy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IntergalacticTradingTests {

  private static ConfigProperties properties;
  private static final Map<String, IntergalacticTrader> tradingLists = new HashMap<>();

  @BeforeAll
  static void setUp() {
    properties = new ConfigProperties();
    RomanArabicConverter converter = new RomanArabicConverter(properties);

    // trading strategies
    tradingLists.put("howMuch", new IntergalacticToRomanStrategy(converter));
    tradingLists.put("silverCredits", new IntergalacticSilverCreditsStrategy(converter));
    tradingLists.put("goldCredits", new IntergalacticGoldCreditsStrategy(converter));
    tradingLists.put("ironCredits", new IntergalacticIronCreditsStrategy(converter));
    tradingLists.put("unknownTrading", new UnknownTradeStrategy());
  }

  @ParameterizedTest(name = "[{index}] - Intergalactic Trading")
  @CsvSource(value = {
      "howMuch,pish tegj glob glob,42",
      "silverCredits,glob prok,68",
      "goldCredits,glob prok,57800",
      "ironCredits,glob prok,782"})
  void testIntergalacticTrading(String strategy, String question, int expected) {
    IntergalacticTrader trader = tradingLists.get(strategy);

    if (trader == null) {
      trader = new UnknownTradeStrategy();
    }

    assertNotNull(trader);

    // Get intergalactic values;
    var words = question.split(" ");
    Arrays.asList(words).forEach(System.out::println);

    List<RomanSymbol> romans = Arrays.stream(words)
        .map(word -> properties.search(word).getRomanSymbol())
        .toList();

    ArabicSymbol symbol = trader.trade(romans);
    assertNotNull(symbol);
    assertEquals(expected, symbol.getValue());
  }

  @ParameterizedTest(name = "[{index}] - Unknown Trading Strategy")
  @CsvSource(value = {
      "myTrading,pish tegj glob glob"})
  void unknownOrNoStrategyDefined(String strategy, String question) {
    var trader = tradingLists.get(strategy);

    if (trader == null) {
      // get unknown strategy
      trader = tradingLists.get("unknownTrading");
    }

    assertNotNull(trader);

    // Get intergalactic values;
    var words = question.split(" ");
    Arrays.asList(words).forEach(System.out::println);

    List<RomanSymbol> romans = Arrays.stream(words)
        .map(word -> properties.search(word).getRomanSymbol())
        .toList();

    IntergalacticTrader finalTrader = trader;
    InvalidIntergalacticTradingPromptException exception =
        assertThrows(InvalidIntergalacticTradingPromptException.class, () -> {
          finalTrader.trade(romans);
        });

    assertNotNull(exception);
  }
}
