package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.model.domain.Edition;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EditionConverterTest {

    private EditionConverter subject;

    @Mock
    private TubDateConverter editionDateConverter;

    @BeforeEach
    void setUpBeforeEach() {
        subject = new EditionConverter(editionDateConverter);
    }

    @Test
    void convertShouldReturnEditionWithHijriDates() {
        final var editionPrintouts = EditionPrintouts.builder()
                .titleTransliterated("editionName")
                .titleArabic("editionPlace")
                .publisher("editionPublisher")
                .city(MediaWikiPageDetails.builder().fulltext("City").build())
                .description("Description")
                .yearHijri(678)
                .yearGregorian(1400)
                .editionType("Modern Print")
                .build();
        final var expected = Edition.builder()
                .titleTransliterated("editionName")
                .titleArabic("editionPlace")
                .publisher("editionPublisher")
                .placeOfPublication("City")
                .description("Description")
                .editionType("Modern Print")
                .date(new HijriDate("678", "1400")).build();

        when(editionDateConverter.convert(editionPrintouts)).thenReturn(new HijriDate("678", "1400"));
        final var actual = subject.convert(editionPrintouts);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void convertShouldReturnEditionWithNullFieldsWhenGivenAllNullFields() {
        final var editionPrintouts = EditionPrintouts.builder().build();
        final var expected = Edition.builder().build();
        final var actual = subject.convert(editionPrintouts);
        assertThat(actual).isEqualTo(expected);
    }

}