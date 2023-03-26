package org.tub.tubtextservice.builder;

import java.util.ArrayList;
import java.util.List;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.TitlePrintouts;

public class TitlePrintoutsBuilder {

  List<MediaWikiPageDetails> category = new ArrayList<>();
  List<String> bookType = new ArrayList<>();
  List<String> numberOfCommentaries = new ArrayList<>();
  List<String> titleArabic = new ArrayList<>();
  List<String> titleTransliterated = new ArrayList<>();
  List<MediaWikiPageDetails> author = new ArrayList<>();
  List<String> translator = new ArrayList<>();
  List<String> catalogueDescription = new ArrayList<>();
  List<MediaWikiPageDetails> baseText = new ArrayList<>();

  public static TitlePrintoutsBuilder builder() {
    return new TitlePrintoutsBuilder();
  }

  public TitlePrintoutsBuilder category(final String category) {
    this.category.add(MediaWikiPageDetailsBuilder.builder().fulltext(category).build());
    return this;
  }

  public TitlePrintoutsBuilder bookType(String bookType) {
    this.bookType.add(bookType);
    return this;
  }

  public TitlePrintoutsBuilder titleArabic(String titleArabic) {
    this.titleArabic.add(titleArabic);
    return this;
  }

  public TitlePrintoutsBuilder titleTransliterated(String titleTransliterated) {
    this.titleTransliterated.add(titleTransliterated);
    return this;
  }

  public TitlePrintoutsBuilder author(String author) {
    this.author.add(MediaWikiPageDetailsBuilder.builder().fulltext(author).build());
    return this;
  }

  public TitlePrintoutsBuilder baseText(final String baseText) {
    this.baseText.add(MediaWikiPageDetailsBuilder.builder().fulltext(baseText).build());
    return this;
  }

  public TitlePrintouts build() {
    return new TitlePrintouts(
        category,
        bookType,
        numberOfCommentaries,
        titleArabic,
        titleTransliterated,
        author,
        translator,
        catalogueDescription,
        baseText);
  }
}
