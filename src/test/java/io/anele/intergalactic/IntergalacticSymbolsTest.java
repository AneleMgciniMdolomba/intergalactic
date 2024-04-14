package io.anele.intergalactic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.anele.intergalactic.config.ConfigProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class IntergalacticSymbolsTest {

  private static ConfigProperties configProperties;

  @BeforeAll
  static void setUp() {
    configProperties = new ConfigProperties();
  }

  @ParameterizedTest(name = "[{index}] - Test valid Roman Symbols")
  @CsvSource(value = {"I;1", "V;5", "X;10",
      "L;50", "C;100", "D;500", "M;1000"}, delimiter = ';')
  void testRomanSymbolToArabicSymbol(char id, int expected) {
    var symbols = configProperties.getRomanSymbols();

    assertNotNull(symbols, "Failed to load data properties");

    var symbol = symbols.stream().filter(val -> val.getId() == id).findFirst();
    assertTrue(symbol.isPresent(), "Invalid Roman Symbol");
    assertEquals(symbol.get().getValue(), expected);
  }

  @Test
  void testInvalidRomanSymbol() {
    var invalidSymbol = configProperties.getRomanSymbols()
        .stream().filter(value -> value.getId() == 'Z').findFirst();

    assertFalse(invalidSymbol.isPresent(), "'Z' is not valid roman symbol. Check Config");
  }

  @ParameterizedTest(name = "[{index}] - Test Repeating Allowed Symbols")
  @ValueSource(chars = {'I', 'X', 'C', 'M'})
  void testRepetitionsAllowed(char symbol) {
    var symbols = configProperties.getRomanSymbols();

    assertNotNull(symbols, "Failed to load data properties");

    var filteredSymbols = symbols.stream().filter(val -> val.getId() == symbol).toList();
    assertNotNull(filteredSymbols, "Configured symbols not found.");

    filteredSymbols.forEach(each -> assertTrue(each.getMaxSequentialAllowed() > 0));
  }

  @ParameterizedTest(name = "[{index}] - Test Repeat NOT Allowed Symbols")
  @ValueSource(chars = {'D', 'L', 'V'})
  void testRepetitionsNotAllowed(char symbol) {
    var symbols = configProperties.getRomanSymbols();

    assertNotNull(symbols, "Failed to load data properties");

    var filteredSymbols = symbols.stream().filter(val -> val.getId() == symbol).toList();
    assertNotNull(filteredSymbols, "Configured symbols not found.");

    filteredSymbols.forEach(each -> assertEquals(0, each.getMaxSequentialAllowed()));
  }

  @ParameterizedTest(name = "[{index}] - Roman Symbol to Intergalactic Unit")
  @CsvSource(value = {"glob;I", "prok;V", "pish;X", "tegj;L"}, delimiter = ';')
  void testRomanToIntergalacticSymbols(String intergalactic, char roman) {
    var galacticSymbol = configProperties.getGalacticSymbols();

    assertNotNull(galacticSymbol, "Failed to load data from config");

    var symbol = galacticSymbol.stream().filter(unit -> intergalactic.equals(unit.getId())).findFirst();
    assertTrue(symbol.isPresent(), "Invalid Intergalactic Symbol");
    assertEquals(symbol.get().getRomanSymbol().getId(), roman);
  }

  @Test
  void testInvalidIntergalacticUnit() {
    var invalidSymbol = configProperties.getGalacticSymbols()
        .stream().filter(unit -> unit.getId().equals("wood")).findFirst();

    assertFalse(invalidSymbol.isPresent(), "'wood' is not valid intergalactic symbol. Check Config");
  }
}
