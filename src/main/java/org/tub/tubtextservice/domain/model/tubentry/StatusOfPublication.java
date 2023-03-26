package org.tub.tubtextservice.domain.model.tubentry;

public enum StatusOfPublication {
    EDITED,
    MANUSCRIPT_ONLY,
    NON_EXTANT,
    UNKNOWN;
    public static StatusOfPublication valueOfTub(String name){
        return switch(name){
            case "Category:Edited title" -> EDITED;
            case "Category:Manuscript-only title" -> MANUSCRIPT_ONLY;
            case "Category:Non-extant title" -> NON_EXTANT;
            default -> UNKNOWN;
        };
    }
}
