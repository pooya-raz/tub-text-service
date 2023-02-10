package org.tub.tubtextservice.model.domain;

public enum Category {
    Edited,
    ManuscriptOnly,
    NonExtant,
    Unknown;
    public static Category valueOfTub(String name){
        return switch(name){
            case "Category:Edited title" -> Edited;
            case "Category:Manuscript-only title" -> ManuscriptOnly;
            case "Category:Non-extant title" -> NonExtant;
            default -> Unknown;
        };
    }
}
