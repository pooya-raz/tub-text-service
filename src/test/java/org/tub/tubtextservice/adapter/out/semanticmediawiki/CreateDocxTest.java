package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.application.usecase.docx.dto.out.MarkdownDto;

class CreateDocxTest {

    private CreateDocx subject;

    @BeforeEach
    void setUp() {
        subject = new CreateDocx();
    }

    @Test
    void createDocx() {
    subject.createDocx(new MarkdownDto("# Header" ), "src/test/resources/output/");
  }
}
