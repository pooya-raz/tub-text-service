package org.tub.tubtextservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.tub.tubtextservice.model.domain.year.editiondate.EditionDate;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.domain.year.editiondate.ShamsiDate;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;

public class EditionDateConvertor implements Converter<EditionPrintouts, EditionDate> {

        @Override
        public EditionDate convert(EditionPrintouts editionPrintouts) {
            var hijri = "";
            var shamsi = "";
            var gregorian = "";

            hijri = YearToStringConvertor.convert(editionPrintouts.yearHijri(), editionPrintouts.yearHijriText());
            shamsi = YearToStringConvertor.convert(editionPrintouts.yearShamsi(), editionPrintouts.yearShamsiText());
            gregorian = YearToStringConvertor.convert(editionPrintouts.yearGregorian(), editionPrintouts.yearGregorianText());

            if (!shamsi.isEmpty()) {
                return new ShamsiDate(shamsi, gregorian);
            }
            return new HijriDate(hijri, gregorian);


        }
}
