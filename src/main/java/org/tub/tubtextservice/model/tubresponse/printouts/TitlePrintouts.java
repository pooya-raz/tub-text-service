package org.tub.tubtextservice.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.model.tubresponse.Author;
import org.tub.tubtextservice.model.tubresponse.Category;

import java.util.List;

public record TitlePrintouts(
    @JsonProperty("Category") List<Category> category,
    @JsonProperty("Book type") List<String> bookType,
    @JsonProperty("Has number of commentaries") List<String> hasNumberOfCommentaries,
    @JsonProperty("Title (Arabic)") List<String> titleArabic,
    @JsonProperty("Title (transliterated)") List<String> titleTransliterated,
    @JsonProperty("Has author(s)") List<Author> hasAuthors,
    @JsonProperty("Has translator(s)") List<Object> hasTranslators,
    @JsonProperty("Has a catalogue description") List<String> hasACatalogueDescription,
    @JsonProperty("Has base text") List<Object> hasBaseText)
    implements Printouts {}
