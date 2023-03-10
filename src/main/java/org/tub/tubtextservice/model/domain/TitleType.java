package org.tub.tubtextservice.model.domain;

public enum TitleType {
  MONOGRAPH,
  COMMENTARY,
  GLOSS,
  MARGINNOTES,
  TREATISE,
  SUMMARY,
  POEM,
  REFUTATION,
  TAQRIRAT,
  TRANSLATION,
  UNKNOWN;

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
}
