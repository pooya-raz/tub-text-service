package org.tub.tubtextservice.service.wordservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.service.word.WordService;

class WordServiceTest {

    private WordService wordService;

    @BeforeEach
    void setUp() {
        wordService = new WordService();
    }

    @Test
    void createWordDocument()throws IOException {
        final var actual = wordService.createWordDocument(null);

        final var expected = new XWPFDocument();
        final var paragraph = expected.createParagraph();
        final var run = paragraph.createRun();
        run.setText("TUB Text Service");
        run.addBreak();
        run.setBold(true);
        final var run2 = paragraph.createRun();
        run2.setText("This is a test document.");
        run.addBreak();

        final var out = new FileOutputStream("test.docx");
        expected.write(out);
        out.close();
        expected.close();

        assertThat(actual).isEqualTo(expected);
    }
}
