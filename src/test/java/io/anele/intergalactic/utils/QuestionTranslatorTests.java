package io.anele.intergalactic.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.anele.intergalactic.AbstractTestSetups;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QuestionTranslatorTests extends AbstractTestSetups {

  @ParameterizedTest(name = "[{index}] - Testing getStrategies")
  @CsvSource(value = {
      "how much is pish tegj glob glob ?,howMuchStrategy",
      "how many Credits is glob prok Silver ?,silverStrategy",
      "how many Credits is glob prok Gold ?,goldStrategy",
      "how many Credits is glob prok Iron ?,ironStrategy",
      "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?,unknownStrategy"
  })
  void testGetTradingStrategy(String trade, String expectedStrategyName) {
    String strategy = QuestionTranslator.getTradingStrategy(List.of(trade.split(" ")));

    assertEquals(expectedStrategyName, strategy);
  }

  @ParameterizedTest(name = "[{index}] - test processTradingQuestion")
  @CsvSource(value = {
      "how much is pish glob glob ?;pish,glob,glob",
      "how many Credits is glob prok Gold ?;glob,prok",
  }, delimiter = ';')
  void testProcessTradingQuestion(String question, String intergalactic) {
    List<String> lines = List.of(question.split(" "));

    List<RomanSymbol> actualProcessTradingQuestionResult = QuestionTranslator.processTradingQuestion(
        lines,
        properties.getGalacticSymbols());

    List<String> expected = List.of(intergalactic.split(","));
    final List<RomanSymbol> symbols = new ArrayList<>();

    for (String expectedQuestion : expected) {
      symbols.add(properties.search(expectedQuestion).getRomanSymbol());
    }
    assertTrue(actualProcessTradingQuestionResult.containsAll(symbols));
  }

  @ParameterizedTest(name = "[{index}] - Test output writer")
  @CsvSource(value = {
      "unit;how much is glob glob ?;10;glob glob is 10",
      "unit;how much is pish tegl ?;56;pish tegl is 56"
  }, delimiter = ';')
  void testToAnswer(String unit, String input, int value, String output) {
    System.out.println(unit);
    System.out.println(input);
    System.out.println(value);
    System.out.println(output);
    List<String> lines = List.of(input.split(" "));
    assertEquals(output + "\n", QuestionTranslator.toOutputLineAnswer(unit, lines, value));
  }

  @ParameterizedTest(name = "[{index}] - Test output writer with Credits")
  @CsvSource(value = {
      "how many Credits is glob prok Silver ?;785;glob prok Silver is 785 Credits"
  }, delimiter = ';')
  void testToAnswer(String input, int value, String output) {
    System.out.println(input);
    System.out.println(value);
    System.out.println(output);
    List<String> lines = List.of(input.split(" "));
    assertEquals(output + "\n", QuestionTranslator.toOutputLineAnswer("Credits", lines, value));
  }
}
