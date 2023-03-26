package org.tub.tubtextservice.service.markdown.layout;

import org.tub.tubtextservice.model.domain.TitleType;

/**
 * This class is responsible for creating the layout of the markdown file.
 */
public class LayoutCreator {

    private String createSection(String section){
        return """
        ## %s
        
        %s
        
        """.formatted(section,"%s");
    }

  /**
   * Creates the general layout.
   * It provides a template that can be string formatted with entries.
   * The sections are created based on the order of {@link TitleType}.
   * @return The layout template.
   */
  public String create() {
        var sections = new StringBuilder();
        for (var section : TitleType.values()){
            sections.append(createSection(section.getTitleType()));
        }
        return sections.toString();
    }

}
