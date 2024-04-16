package io.anele.intergalactic;

import io.anele.intergalactic.config.ConfigProperties;
import io.anele.intergalactic.model.GalacticSymbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractTestSetups {

  public static ConfigProperties properties;

  static void init() {
    properties = new ConfigProperties();
    List<GalacticSymbol> symbols = new ArrayList<>(
        Arrays.asList(
            new GalacticSymbol("glob", properties.search('I')),
            new GalacticSymbol("prok", properties.search('V')),
            new GalacticSymbol("pish", properties.search('X')),
            new GalacticSymbol("tegj", properties.search('L'))
        )
    );
    properties.setGalacticSymbols(symbols);
  }
  static List<GalacticSymbol> getGalacticSymbols() {

    return properties.getGalacticSymbols();
  }
}
