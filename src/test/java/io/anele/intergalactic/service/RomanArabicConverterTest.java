package io.anele.intergalactic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.anele.intergalactic.config.GalaxyTradingConfigurationProperties;
import io.anele.intergalactic.exceptions.InvalidRomanSymbolsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class RomanArabicConverterTest {

  private static RomanArabicConverter converter;

  @BeforeAll
  static void setUp() {
    converter = new RomanArabicConverter(new GalaxyTradingConfigurationProperties());
  }

  @ParameterizedTest(name = "[{index}] - Roman to Arabic")
  @CsvSource(value = {"MMVI;2006", "MCMXLIV;1944", "XXXIX;39",
      "M;1000", "IX;9", "MCI;1101", "XL;40",
      "XC;90", "XI;11", "XLII;42", "XL;40", "IV;4"}, delimiter = ';')
  void testRomanSymbolsConversionRules(String roman, int arabic) {
    var arabicValue = converter.convert(roman);

    assertEquals(arabic, arabicValue.getValue());
  }

  @ParameterizedTest(name = "[{index}] - Testing {arguments} repeats")
  @ValueSource(strings = {"LXL", "DD", "XVVL"})
  void testInvalidRomanSymbolsConversionRules(String roman) {
    // D, L & V can never repeat

    InvalidRomanSymbolsException invalidRomanSymbolsException = assertThrows(
        InvalidRomanSymbolsException.class, () -> converter.convert(roman));

    assertNotNull(invalidRomanSymbolsException, "Validation failed.");
  }

  @ParameterizedTest(name = "[{index}] - Testing {arguments}")
  @ValueSource(strings = {"XXXXIX", "XIIIIX", "XCCCC", "MMMM"})
  void testingMaxAllowedRepeats(String roman) {
    // 'I', 'X', 'C', 'M' can be repeated up to 3 consecutively
    InvalidRomanSymbolsException invalidRomanSymbolsException = assertThrows(
        InvalidRomanSymbolsException.class, () -> converter.convert(roman)
    );

    assertNotNull(invalidRomanSymbolsException);
    assertEquals("Max Sequential Allowed exceeded.", invalidRomanSymbolsException.getMessage());
  }
}
