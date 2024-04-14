package io.anele;

import io.anele.intergalactic.config.ConfigProperties;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class IntergalacticApplication {

  private static final Logger LOGGER = Logger.getLogger("Main");
  private static ConfigProperties configProperties;

  public static void main(String[] args) {
    LOGGER.log(Level.INFO, "Hello, Welcome to Galactic Currency");

    // Pretending we're loading from DB
    loadSymbols();

    print();
  }

  static void loadSymbols() {
    configProperties = new ConfigProperties();
    configProperties.initializeData();
  }

  static void print() {
    configProperties.getRomanSymbols().forEach(symbol ->
        LOGGER.log(new LogRecord(Level.INFO, symbol.toString()))
    );
  }
}