package org.tub.tubtextservice.domain;

public enum TitleType {
    MONOGRAPH("Monograph"),
    COMMENTARY("Commentary (sharḥ)"),
    GLOSS("Gloss (ḥāshīyah)"),
    MARGINNOTES("Marginal notes (taʿlīqa)"),
    TREATISE("Treatise (risāla)"),
    SUMMARY("Summary (khulāṣa/mukhtaṣar)"),
    POEM("Poem (manẓūma)"),
    REFUTATION("Refutation (radd)"),
    TAQRIRAT("Taqrīrāt"),
    TRANSLATION("Translation"),
    UNKNOWN("Unknown");

    private final String titleType;

    TitleType(String titleType) {
        this.titleType = titleType;
    }

    public static TitleType valueOfTub(String name) {
        return switch (name) {
            case "Monograph" -> MONOGRAPH;
            case "Commentary (sharḥ)" -> COMMENTARY;
            case "Gloss (ḥāshīyah)" -> GLOSS;
            case "Marginal notes (taʿlīqa)" -> MARGINNOTES;
            case "Treatise (risāla)" -> TREATISE;
            case "Summary (khulāṣa/mukhtaṣar)" -> SUMMARY;
            case "Poem (manẓūma)" -> POEM;
            case "Refutation (radd)" -> REFUTATION;
            case "Taqrīrāt" -> TAQRIRAT;
            case "Translation" -> TRANSLATION;
            default -> UNKNOWN;
        };
    }

    public String getTitleType() {
        return titleType;
    }
}
