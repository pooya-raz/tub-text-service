package org.tub.tubtextservice.model.domain;

public enum TitleType {
  Monograph,
  Commentary,
  Gloss,
  MarginalNotes,
  Treatise,
  Summary,
  Poem,
  Refutation,
  Taqrirat,
  Translation,
  Unknown;
    public static TitleType valueOfTub(String name){
        return switch(name){
        case "Monograph" -> Monograph;
        case "Commentary (sharḥ)" -> Commentary;
        case "Gloss (ḥāshīyah)" -> Gloss;
        case "Marginal notes (taʿlīqa)" -> MarginalNotes;
        case "Treatise (risāla)" -> Treatise;
        case "Summary (khulāṣa/mukhtaṣar)" -> Summary;
        case "Poem (manẓūma)" -> Poem;
        case "Refutation (radd)" -> Refutation;
        case "Taqrīrāt" -> Taqrirat;
        case "Translation" -> Translation;
        default -> Unknown;
        };
    }
}
