package io.anele.intergalactic.utils;

import static io.anele.intergalactic.utils.StaticUtilFields.COMMODITY_CREDITS_INDEX_CHECKER;
import static io.anele.intergalactic.utils.StaticUtilFields.HOW_MANY_INDEX_CHECKER;
import static io.anele.intergalactic.utils.StaticUtilFields.HOW_MUCH_INDEX_CHECKER;
import static io.anele.intergalactic.utils.StaticUtilFields.INTERGALACTIC_STRATEGY;
import static io.anele.intergalactic.utils.StaticUtilFields.IS_INDEX_CHECKER;
import static io.anele.intergalactic.utils.StaticUtilFields.TRADING_SUFFIX;
import static io.anele.intergalactic.utils.StaticUtilFields.UNKNOWN_TRADING_STRATEGY;

import io.anele.intergalactic.model.GalacticSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import java.util.ArrayList;
import java.util.List;

public class QuestionTranslator {
  private QuestionTranslator() {

  }

  /**
   * Determines which strategy should be used to perform trading
   *
   * @param question is the list of words making up trading as
   * @return strategy name to answer trading question
   */
  static String getTradingStrategy(List<String> question) {
    // How Much - strategy
    if (HOW_MUCH_INDEX_CHECKER.equalsIgnoreCase(question.get(1)) &&
        IS_INDEX_CHECKER.equalsIgnoreCase(question.get(2))) {
      return INTERGALACTIC_STRATEGY;
    }

    // How Many - Credits Strategy
    if (HOW_MANY_INDEX_CHECKER.equalsIgnoreCase(question.get(1)) &&
        COMMODITY_CREDITS_INDEX_CHECKER.equalsIgnoreCase(question.get(2))) {

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

  public static String toOutputLineAnswer(String unit, List<String> lines, double value) {
    StringBuilder builder = new StringBuilder();

    for (int index = lines.lastIndexOf(IS_INDEX_CHECKER) + 1; index < lines.size() - 1; index++) {
      builder.append(lines.get(index));
      builder.append(" ");
    }
    builder.append("is ");

    builder.append((int) value);

    if (COMMODITY_CREDITS_INDEX_CHECKER.equalsIgnoreCase(unit)) {
      builder.append(" ");
      builder.append(unit);
    }

    builder.append("\n");
    return builder.toString();
  }
}
