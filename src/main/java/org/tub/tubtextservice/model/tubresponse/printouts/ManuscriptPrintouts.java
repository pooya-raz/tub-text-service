package org.tub.tubtextservice.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.model.tubresponse.MediaWikiPageDetails;

import java.util.ArrayList;
import java.util.List;

public record ManuscriptPrintouts(
    @JsonProperty("Has a location") List<String> location,
    @JsonProperty("Has references") List<String> references,
    @JsonProperty("Has year(Gregorian)") List<Integer> yearGregorian,
    @JsonProperty("Has year(Gregorian) text") List<String> yearGregorianText,
    @JsonProperty("Has year(Hijri)") List<Integer> yearHijri,
    @JsonProperty("Has year(Hijri) text") List<String> yearHijriText,
    @JsonProperty("Has year(Shamsi)") List<Integer> yearShamsi,
    @JsonProperty("Has year(Shamsi) text") List<String> yearShamsiText,
    @JsonProperty("Located in a city") List<MediaWikiPageDetails> city,
    @JsonProperty("Manuscript number") List<String> manuscriptNumber,
    @JsonProperty("Manuscript of title") List<MediaWikiPageDetails> manuscriptOfTitle)
    implements DatedPrintouts {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder{
        List<String> location = new ArrayList<>();
        List<String> references = new ArrayList<>();
        List<Integer> yearGregorian = new ArrayList<>();
        List<String> yearGregorianText = new ArrayList<>();
        List<Integer> yearHijri = new ArrayList<>();
        List<String> yearHijriText = new ArrayList<>();
        List<Integer> yearShamsi = new ArrayList<>();
        List<String> yearShamsiText = new ArrayList<>();
        List<MediaWikiPageDetails> city = new ArrayList<>();
        List<String> manuscriptNumber = new ArrayList<>();
        List<MediaWikiPageDetails> manuscriptOfTitle = new ArrayList<>();

        public Builder location(String location){
            this.location.add(location);
            return this;
        }

        public Builder yearGregorian(Integer yearGregorian){
            this.yearGregorian.add(yearGregorian);
            return this;
        }

        public Builder yearHijri(Integer yearHijri){
            this.yearHijri.add(yearHijri);
            return this;
        }

        public Builder city(MediaWikiPageDetails city){
            this.city.add(city);
            return this;
        }

        public Builder manuscriptNumber(String manuscriptNumber){
            this.manuscriptNumber.add(manuscriptNumber);
            return this;
        }

        public Builder manuscriptOfTitle(String title){
            final var manuscriptOfTitle = MediaWikiPageDetails.builder().fulltext(title).build();
            this.manuscriptOfTitle.add(manuscriptOfTitle);
            return this;
        }

        public ManuscriptPrintouts build(){
            return new ManuscriptPrintouts(location, references, yearGregorian, yearGregorianText, yearHijri, yearHijriText, yearShamsi, yearShamsiText, city, manuscriptNumber, manuscriptOfTitle);
        }
    }
}
