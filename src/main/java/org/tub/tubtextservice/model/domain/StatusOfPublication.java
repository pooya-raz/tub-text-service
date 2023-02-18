package org.tub.tubtextservice.model.domain;

public enum StatusOfPublication {
    Edited,
    ManuscriptOnly,
    NonExtant,
    Unknown;
    public static StatusOfPublication valueOfTub(String name){
        return switch(name){
            case "Category:Edited title" -> Edited;
            case "Category:Manuscript-only title" -> ManuscriptOnly;
            case "Category:Non-extant title" -> NonExtant;
            default -> Unknown;
        };
    }
}
