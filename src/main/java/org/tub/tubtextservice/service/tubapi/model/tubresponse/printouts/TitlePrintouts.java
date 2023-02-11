package org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.MediaWikiPageDetails;

import java.util.ArrayList;
import java.util.List;

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
    implements Printouts {

    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        List<MediaWikiPageDetails> category = new ArrayList<>();
        List<String> bookType = new ArrayList<>();
        List<String> numberOfCommentaries = new ArrayList<>();
        List<String> titleArabic = new ArrayList<>();
        List<String> titleTransliterated = new ArrayList<>();
        List<MediaWikiPageDetails> author = new ArrayList<>();
        List<String> translator = new ArrayList<>();
        List<String> catalogueDescription = new ArrayList<>();
        List<MediaWikiPageDetails> baseText = new ArrayList<>();

        public Builder() {}

        public Builder category(MediaWikiPageDetails category) {
            this.category.add(category);
            return this;
        }

        public Builder bookType(String bookType) {
            this.bookType.add(bookType);
            return this;
        }

        public Builder numberOfCommentaries(String numberOfCommentaries) {
            this.numberOfCommentaries.add(numberOfCommentaries);
            return this;
        }

        public Builder titleArabic(String titleArabic) {
            this.titleArabic.add(titleArabic);
            return this;
        }

        public Builder titleTransliterated(String titleTransliterated) {
            this.titleTransliterated.add(titleTransliterated);
            return this;
        }

        public Builder author(MediaWikiPageDetails author) {
            this.author.add(author);
            return this;
        }

        public Builder translator(String translator) {
            this.translator.add(translator);
            return this;
        }

        public Builder catalogueDescription(String catalogueDescription) {
            this.catalogueDescription.add(catalogueDescription);
            return this;
        }

        public Builder baseText(MediaWikiPageDetails baseText) {
            this.baseText.add(baseText);
            return this;
        }

        public TitlePrintouts build() {
            return new TitlePrintouts(category, bookType, numberOfCommentaries, titleArabic, titleTransliterated, author, translator, catalogueDescription, baseText);
        }
    }
}
