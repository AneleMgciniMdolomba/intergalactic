package io.anele.intergalactic.utils;

import io.anele.intergalactic.model.GalacticSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.ArrayList;
import java.util.List;

public class QuestionTranslator {

  private QuestionTranslator() {

  }

  protected static final String TRADING_SUFFIX = "Strategy";
  protected static final String INTERGALACTIC_STRATEGY = "howMuch" + TRADING_SUFFIX;
  protected static final String UNKNOWN_TRADING_STRATEGY = "unknown" + TRADING_SUFFIX;

  /**
   * Determines which strategy should be used to perform trading
   *
   * @param question is the list of words making up trading as
   * @return strategy name to answer trading question
   */
  static String getTradingStrategy(List<String> question) {
    // How Much - strategy
    if ("much".equalsIgnoreCase(question.get(1)) &&
        "is".equalsIgnoreCase(question.get(2))) {
      return INTERGALACTIC_STRATEGY;
    }

    // How Many - Credits Strategy
    if ("many".equalsIgnoreCase(question.get(1)) &&
        "Credits".equalsIgnoreCase(question.get(2))) {

      switch (question.get(question.size() - 2).toLowerCase()) {
        case "silver" -> {
          return String.join("", "silver", TRADING_SUFFIX);
        }
        case "gold" -> {
          return String.join("", "gold", TRADING_SUFFIX);
        }
        case "iron" -> {
          return String.join("", "iron", TRADING_SUFFIX);
        }
        default -> {
          return UNKNOWN_TRADING_STRATEGY;
        }
      }
    }

    return UNKNOWN_TRADING_STRATEGY;
  }

  /**
   * This method takes lines and galactic units configured to do trade
   *
   * @param lines           trading question in a list form
   * @param galacticSymbols configured
   * @return list of 'RomanSymbols' corresponding to galactic unit
   */
  static List<RomanSymbol> processTradingQuestion(List<String> lines,
      List<GalacticSymbol> galacticSymbols) {

    List<RomanSymbol> symbols = new ArrayList<>();

    for (String galactic : lines) {
      for (GalacticSymbol configuredUnits : galacticSymbols) {
        if (galactic.equalsIgnoreCase(configuredUnits.getId())) {
          symbols.add(configuredUnits.getRomanSymbol());
        }
      }
    }

    return symbols;
  }

  public static String toAnswer(String unit, List<String> lines, double value) {
    StringBuilder builder = new StringBuilder();

    for (int index = lines.lastIndexOf("is") + 1; index < lines.size() - 1; index++) {
      builder.append(lines.get(index));
      builder.append(" ");
    }
    builder.append("is ");

    builder.append((int) value);

    if ("Credits".equalsIgnoreCase(unit)) {
      builder.append(" ");
      builder.append(unit);
    }

    builder.append("\n");
    return builder.toString();
  }
}
