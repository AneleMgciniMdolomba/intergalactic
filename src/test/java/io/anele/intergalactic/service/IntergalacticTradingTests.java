package io.anele.intergalactic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.anele.intergalactic.config.ConfigProperties;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.RomanSymbol;
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

  private static IntergalacticTrader trader;
  private static RomanArabicConverter converter;
  private static ConfigProperties properties;
  private static Map<String, IntergalacticTrader> tradingLists = new HashMap<>();

  @BeforeAll
  static void setUp() {
    properties = new ConfigProperties();
    converter = new RomanArabicConverter(properties);
    tradingLists.put("howMuch", new IntergalacticToRomanStrategy(properties, converter));
  }

  @ParameterizedTest(name = "[{index}] - Intergalactic Trading")
  @CsvSource(value = {"howMuch,pish tegj glob glob,42"})
  void testIntergalacticTrading(String strategy, String question, int expected) {
    trader = tradingLists.get("howMuch");

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

}
