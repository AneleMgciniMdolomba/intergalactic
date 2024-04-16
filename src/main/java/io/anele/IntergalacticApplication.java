package io.anele;

import io.anele.intergalactic.utils.GalaxyTrader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntergalacticApplication {

  private static final Logger LOGGER = Logger.getLogger("Main");

  public static void main(String[] args) {
    LOGGER.log(Level.INFO, "Hello, Welcome to Galactic Currency");
    GalaxyTrader.trade();
    LOGGER.log(Level.INFO, "Done, Trading File Created under resources folder");
  }
}