package org.tub.tubtextservice.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.model.tubresponse.MediaWikiPageDetails;

import java.util.List;

public record TitlePrintouts(
    @JsonProperty("Category") List<MediaWikiPageDetails> category,
    @JsonProperty("Book type") List<String> bookType,
    @JsonProperty("Has number of commentaries") List<String> hasNumberOfCommentaries,
    @JsonProperty("Title (Arabic)") List<String> titleArabic,
    @JsonProperty("Title (transliterated)") List<String> titleTransliterated,
    @JsonProperty("Has author(s)") List<MediaWikiPageDetails> hasAuthors,
    @JsonProperty("Has translator(s)") List<MediaWikiPageDetails> hasTranslators,
    @JsonProperty("Has a catalogue description") List<String> hasACatalogueDescription,
    @JsonProperty("Has base text") List<MediaWikiPageDetails> hasBaseText)
    implements Printouts {}
