package org.tub.tubtextservice.application.usecase.markdown;

import org.tub.tubtextservice.application.usecase.markdown.port.SaveMarkdownPort;

public class SaveMarkdown {

    private final SaveMarkdownPort saveMarkdownPort;

    public SaveMarkdown(SaveMarkdownPort saveMarkdownPort) {
        this.saveMarkdownPort = saveMarkdownPort;
    }

    public void save(String markdown) {
        saveMarkdownPort.save(markdown);
    }
}
