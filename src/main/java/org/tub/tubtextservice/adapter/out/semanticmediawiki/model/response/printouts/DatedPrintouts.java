package org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts;

import java.util.List;

public sealed interface DatedPrintouts extends Printouts permits ManuscriptPrintouts, EditionPrintouts {

    List<Integer> yearHijri();

    List<String> yearHijriText();

    List<Integer> yearShamsi();

    List<String> yearShamsiText();

    List<Integer> yearGregorian();

    List<String> yearGregorianText();
}
