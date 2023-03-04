package org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.MediaWikiPageDetails;

import java.util.ArrayList;
import java.util.List;

public record EditionPrintouts(
    @JsonProperty("Title (transliterated)") List<String> titleTransliterated,
    @JsonProperty("Title (Arabic)") List<String> titleArabic,
    @JsonProperty("Has year(Gregorian)") List<Integer> yearGregorian,
    @JsonProperty("Has year(Gregorian) text") List<String> yearGregorianText,
    @JsonProperty("Has year(Hijri)") List<Integer> yearHijri,
    @JsonProperty("Has year(Hijri) text") List<String> yearHijriText,
    @JsonProperty("Has year(Shamsi)") List<Integer> yearShamsi,
    @JsonProperty("Has year(Shamsi) text") List<String> yearShamsiText,
    @JsonProperty("Published edition of title") List<MediaWikiPageDetails> editionOfTitle,
    @JsonProperty("Has editor(s)") List<String> editors,
    @JsonProperty("Has a publisher") List<String> publisher,
    @JsonProperty("Has a description") List<String> description,
    @JsonProperty("Edition type") List<String> editionType,
    @JsonProperty("City") List<MediaWikiPageDetails> city)
    implements DatedPrintouts {

  public EditionPrintouts {
    titleTransliterated = List.copyOf(titleTransliterated);
    titleArabic = List.copyOf(titleArabic);
    yearGregorian = List.copyOf(yearGregorian);
    yearGregorianText = List.copyOf(yearGregorianText);
    yearHijri = List.copyOf(yearHijri);
    yearHijriText = List.copyOf(yearHijriText);
    yearShamsi = List.copyOf(yearShamsi);
    yearShamsiText = List.copyOf(yearShamsiText);
    editionOfTitle = List.copyOf(editionOfTitle);
    editors = List.copyOf(editors);
    publisher = List.copyOf(publisher);
    description = List.copyOf(description);
    editionType = List.copyOf(editionType);
    city = List.copyOf(city);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    List<String> titleTransliterated = new ArrayList<>();
    List<String> titleArabic = new ArrayList<>();
    List<Integer> yearGregorian = new ArrayList<>();
    List<String> yearGregorianText = new ArrayList<>();
    List<Integer> yearHijri = new ArrayList<>();
    List<String> yearHijriText = new ArrayList<>();
    List<Integer> yearShamsi = new ArrayList<>();
    List<String> yearShamsiText = new ArrayList<>();
    List<MediaWikiPageDetails> publishedEditionOfTitle = new ArrayList<>();
    List<String> editors = new ArrayList<>();
    List<String> publisher = new ArrayList<>();
    List<String> description = new ArrayList<>();
    List<String> editionType = new ArrayList<>();
    List<MediaWikiPageDetails> city = new ArrayList<>();

    public Builder titleTransliterated(String titleTransliterated) {
      this.titleTransliterated.add(titleTransliterated);
      return this;
    }

    public Builder titleArabic(String titleArabic) {
      this.titleArabic.add(titleArabic);
      return this;
    }

    public Builder yearGregorian(Integer yearGregorian) {
      this.yearGregorian.add(yearGregorian);
      return this;
    }

    public Builder yearGregorianText(String yearGregorianText) {
      this.yearGregorianText.add(yearGregorianText);
      return this;
    }

    public Builder yearHijri(Integer yearHijri) {
      this.yearHijri.add(yearHijri);
      return this;
    }

    public Builder yearHijriText(String yearHijriText) {
      this.yearHijriText.add(yearHijriText);
      return this;
    }

    public Builder yearShamsi(Integer yearShamsi) {
      this.yearShamsi.add(yearShamsi);
      return this;
    }

    public Builder yearShamsiText(String yearShamsiText) {
      this.yearShamsiText.add(yearShamsiText);
      return this;
    }

    public Builder publishedEditionOfTitle(String title) {
      final var mediaWikiPageDetails = MediaWikiPageDetails.builder().fulltext(title).build();
      this.publishedEditionOfTitle.add(mediaWikiPageDetails);
      return this;
    }

    public Builder editors(String editors) {
      this.editors.add(editors);
      return this;
    }

    public Builder publisher(String publisher) {
      this.publisher.add(publisher);
      return this;
    }

    public Builder description(String description) {
      this.description.add(description);
      return this;
    }

    public Builder editionType(String editionType) {
      this.editionType.add(editionType);
      return this;
    }

    public Builder city(MediaWikiPageDetails city) {
      this.city.add(city);
      return this;
    }

    public EditionPrintouts build() {
      return new EditionPrintouts(
          titleTransliterated,
          titleArabic,
          yearGregorian,
          yearGregorianText,
          yearHijri,
          yearHijriText,
          yearShamsi,
          yearShamsiText,
          publishedEditionOfTitle,
          editors,
          publisher,
          description,
          editionType,
          city);
    }
  }
}
