package io.anele.intergalactic.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class TradingFileWriterTest {

  @Test
  void testWriteFileToTest() {
    final String testPath = "src/test/resources/trades-test.txt";
    TradingFileWriter.write(testPath, "Output\n");

    assertTrue(Files.exists(Path.of(testPath)));
  }
}
