package org.tub.tubtextservice.domain;

import org.tub.tubtextservice.domain.year.editiondate.EditionDate;

public record Edition(
        String titleTransliterated,
        String titleArabic,
        EditionDate date,
        String editor,
        String publisher,
        String description,
        String editionType,
        String placeOfPublication,
        Integer sortYearGregorian) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        String titleTransliterated;
        String titleArabic;
        EditionDate date;
        String editor;
        String publisher;
        String description;
        String editionType;
        String placeOfPublication;
        Integer sortYearGregorian;

        public Builder titleTransliterated(String titleTransliterated) {
            this.titleTransliterated = titleTransliterated;
            return this;
        }

        public Builder titleArabic(String titleArabic) {
            this.titleArabic = titleArabic;
            return this;
        }

        public Builder date(EditionDate date) {
            this.date = date;
            return this;
        }

        public Builder editor(String editor) {
            this.editor = editor;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder editionType(String editionType) {
            this.editionType = editionType;
            return this;
        }

        public Builder placeOfPublication(String placeOfPublication) {
            this.placeOfPublication = placeOfPublication;
            return this;
        }

        public Builder sortYearGregorian(Integer sortYearGregorian) {
            this.sortYearGregorian = sortYearGregorian;
            return this;
        }

        public Edition build() {
            return new Edition(
                    titleTransliterated,
                    titleArabic,
                    date,
                    editor,
                    publisher,
                    description,
                    editionType,
                    placeOfPublication,
                    sortYearGregorian);
        }
    }
}
