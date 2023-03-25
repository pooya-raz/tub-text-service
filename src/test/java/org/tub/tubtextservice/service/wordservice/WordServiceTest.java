package org.tub.tubtextservice.service.wordservice;


import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WordServiceTest {

    @Test
  @DisplayName("Use pandoc")
  void test4()throws IOException{
      final String[] command = {"pandoc", "src/main/resources/sample-tub.md", "-o", "test.docx"};
    Runtime.getRuntime().exec(command);
    }
}