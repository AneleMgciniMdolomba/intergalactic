package io.anele.intergalactic.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public class TradingFileWriter {

  private static final String RANDOM_FILE_ID;
  private static final String FILE;

  private TradingFileWriter() {
  }

  static {
    RANDOM_FILE_ID = UUID.randomUUID().toString().substring(0, 5);
    FILE = "src/main/resources/trades-" + RANDOM_FILE_ID + ".txt";
  }

  public static void write(String output) {
    writeWithPath(FILE, output);
  }

  public static void write(final String filePath, String output) {
    String file;
    if (filePath.isEmpty()) {
      file = FILE;
    } else {
      file = filePath;
    }

    writeWithPath(file, output);
  }

  private static void writeWithPath(final String filePath, String output) {
    try (OutputStream writer = Files.newOutputStream(Path.of(filePath),
        StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

      writer.write(output.getBytes(StandardCharsets.UTF_8));
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
