package org.tub.tubtextservice.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Printouts(
    Category category,
    List<String> bookType,
    List<String> hasNumberOfCommentaries,
    List<String> titleArabic,
    List<String> titleTransliterated,
    @JsonProperty("Has author(s)") List<Author> hasAuthorS,
    @JsonProperty("Has translator(s)") List<Object> hasTranslatorS,
    List<String> hasACatalogueDescription,
    List<Object> hasBaseText) {}
