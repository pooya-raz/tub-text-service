package org.tub.tubtextservice.domain.tubdata.model.tubresponse.printouts;

import java.util.List;

public sealed interface DatedPrintouts extends Printouts
    permits ManuscriptPrintouts, EditionPrintouts {

  List<Integer> yearHijri();

  List<String> yearHijriText();

  List<Integer> yearShamsi();

  List<String> yearShamsiText();

  List<Integer> yearGregorian();

  List<String> yearGregorianText();
}
