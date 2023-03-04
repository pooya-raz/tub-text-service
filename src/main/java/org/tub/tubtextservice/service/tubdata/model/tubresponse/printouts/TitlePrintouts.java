package org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.MediaWikiPageDetails;

import java.util.List;

@SuppressWarnings({"findbugs:EI_EXPOSE_REP", "findbugs:EI_EXPOSE_REP2"})
public record TitlePrintouts(
    @JsonProperty("Category") List<MediaWikiPageDetails> category,
    @JsonProperty("Book type") List<String> bookType,
    @JsonProperty("Has number of commentaries") List<String> numberOfCommentaries,
    @JsonProperty("Title (Arabic)") List<String> titleArabic,
    @JsonProperty("Title (transliterated)") List<String> titleTransliterated,
    @JsonProperty("Has author(s)") List<MediaWikiPageDetails> author,
    @JsonProperty("Has translator(s)") List<String> translator,
    @JsonProperty("Has a catalogue description") List<String> catalogueDescription,
    @JsonProperty("Has base text") List<MediaWikiPageDetails> baseText)
    implements Printouts {}
