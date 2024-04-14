package io.anele.intergalactic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.anele.intergalactic.config.ConfigProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RomanArabicConverterTest {

  private static RomanArabicConverter converter;

  @BeforeAll
  static void setUp() {
    converter = new RomanArabicConverter(new ConfigProperties());
  }

  @ParameterizedTest(name = "[{index}] - Roman to Arabic")
  @CsvSource(value = {"MMVI;2006", "MCMXLIV;1944", "XXXIX;39",
      "M;1000", "IX;9", "MCI;1101"}, delimiter = ';')
  void testRomanSymbolsConversionRules(String roman, int arabic) {
    var arabicValue = converter.convert(roman);

    assertEquals(arabic, arabicValue.getValue());
  }
}
