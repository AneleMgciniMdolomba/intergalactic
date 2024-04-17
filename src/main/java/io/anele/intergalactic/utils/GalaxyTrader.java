package io.anele.intergalactic.utils;

import static io.anele.intergalactic.utils.StaticUtilFields.INTERGALACTIC_STRATEGY;
import static io.anele.intergalactic.utils.StaticUtilFields.TRADING_SUFFIX;
import static io.anele.intergalactic.utils.StaticUtilFields.UNKNOWN_TRADING_STRATEGY;

import io.anele.intergalactic.config.GalaxyTradingConfigurationProperties;
import io.anele.intergalactic.exceptions.InvalidIntergalacticTradingPromptException;
import io.anele.intergalactic.model.ArabicSymbol;
import io.anele.intergalactic.model.GalacticSymbol;
import io.anele.intergalactic.model.RomanSymbol;
import io.anele.intergalactic.service.IntergalacticTrader;
import io.anele.intergalactic.service.RomanArabicConverter;
import io.anele.intergalactic.service.impl.IntergalacticGoldCreditsStrategy;
import io.anele.intergalactic.service.impl.IntergalacticIronCreditsStrategy;
import io.anele.intergalactic.service.impl.IntergalacticSilverCreditsStrategy;
import io.anele.intergalactic.service.impl.IntergalacticToRomanStrategy;
import io.anele.intergalactic.service.impl.UnknownTradeStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GalaxyTrader {

  private static final GalaxyTradingConfigurationProperties configProperties = new GalaxyTradingConfigurationProperties();
  private static final RomanArabicConverter converter = new RomanArabicConverter(configProperties);
  private static final Map<String, IntergalacticTrader> tradingStrategies = new HashMap<>();
  public static final String GALACTIC_CREDITS = "Credits";

  private GalaxyTrader() {

  }

  /**
   * Start trading. Reads input file from resources folder. input file has to be in a specific
   * format as src/main/resources/merchantsGuideToTrade.txt an example. Will define galactic units
   * and start trading using those configured galactic units
   */
  public static void trade() {
    // Register static trading strategies
    tradingStrategies.put(INTERGALACTIC_STRATEGY, new IntergalacticToRomanStrategy(converter));
    tradingStrategies.put(UNKNOWN_TRADING_STRATEGY, new UnknownTradeStrategy());

    try (Scanner scanner = new Scanner(new File("src/main/resources/merchantsGuideToTrade.txt"))) {
      while (scanner.hasNext()) {
        final String line = scanner.nextLine();

        List<String> lines = Arrays.asList(line.split(" "));

        if (isTradingInstructions(lines)) {
          createOrLoadTradingInstructions(lines);

        } else if (isTrading(lines)) {
          doTrading(lines);
        }
      }
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    }
  }

  /**
   * @param lines of the sentence broken into array list of words. if the first word IS 'how', we
   *              assume it's trading
   * @return boolean
   */
  private static boolean isTrading(List<String> lines) {
    return !isTradingInstructions(lines);
  }

  /**
   * @param lines of the sentence broken into array list of words. if the first word is not 'how',
   *              we assume it's trading instruction
   * @return boolean
   */
  private static boolean isTradingInstructions(List<String> lines) {
    return lines != null && !lines.isEmpty() &&
        !"how".equalsIgnoreCase(lines.get(0));
  }

  /**
   * @param lines to perform trading. Answers the 'howMuch' is galactic unit and 'howMany' credits
   *              is a commodity is in our world
   */
  private static void doTrading(List<String> lines) {
    final IntergalacticTrader trader = tradingStrategies.get(
        QuestionTranslator.getTradingStrategy(lines));

    List<RomanSymbol> currency = QuestionTranslator.processTradingQuestion(lines,
        configProperties.getGalacticSymbols());

    try {
      ArabicSymbol symbol = trader.trade(currency);

      String output = QuestionTranslator.toOutputLineAnswer(
          isCredits(lines) ? GALACTIC_CREDITS : "is",
          lines, symbol.getValue());

      TradingFileWriter.write(output);
    } catch (InvalidIntergalacticTradingPromptException exception) {
      TradingFileWriter.write(exception.getMessage() + "\n");
    }
  }

  /**
   * Configuring our trading app with galactic units, commodity unit price and registering trading
   * strategies
   *
   * @param lines input line from file as a list of words
   */
  private static void createOrLoadTradingInstructions(List<String> lines) {
    // if line = 3 and lines.get(1) == is; that is a galactic symbol
    if (lines.size() == 3 && "is".equalsIgnoreCase(lines.get(1))) {
      configProperties.addGalacticSymbol(lines.get(0), lines.get(2));

      return;
    }

    // Calculate cost per unit for commodities
    if (GALACTIC_CREDITS.equalsIgnoreCase(lines.get(lines.size() - 1))) {
      var credits = Double.parseDouble(lines.get(lines.size() - 2));
      var commodity = lines.get(lines.size() - 4).toLowerCase();

      // Get galactic symbols up to commodity
      List<GalacticSymbol> galacticSymbols = new ArrayList<>();
      for (String line : lines) {
        if (line.equalsIgnoreCase(commodity)) {
          break;
        }
        galacticSymbols.add(configProperties.search(line));
      }

      StringBuilder builder = new StringBuilder();
      for (GalacticSymbol galacticSymbol : galacticSymbols) {
        builder.append(galacticSymbol.getRomanSymbol().getId());
      }

      double costPerUnit = credits / converter.convert(builder.toString()).getValue();

      registerTradingStrategy(commodity, costPerUnit);
    }

  }

  private static boolean isCredits(List<String> lines) {
    return GALACTIC_CREDITS.equalsIgnoreCase(lines.get(2));
  }

  /**
   * Registering trading strategies
   *
   * @param commodity   to trade, known are silver, gold & iron
   * @param costPerUnit price for this commodity
   */
  private static void registerTradingStrategy(String commodity, double costPerUnit) {

    switch (commodity.toLowerCase()) {
      case "silver" -> tradingStrategies.put(commodity + TRADING_SUFFIX
          , new IntergalacticSilverCreditsStrategy(converter, costPerUnit));
      case "gold" -> tradingStrategies.put(commodity + TRADING_SUFFIX,
          new IntergalacticGoldCreditsStrategy(converter, costPerUnit));
      case "iron" -> tradingStrategies.put(commodity + TRADING_SUFFIX,
          new IntergalacticIronCreditsStrategy(converter, costPerUnit));
      default -> tradingStrategies.put(UNKNOWN_TRADING_STRATEGY, new UnknownTradeStrategy());
    }
  }

}
