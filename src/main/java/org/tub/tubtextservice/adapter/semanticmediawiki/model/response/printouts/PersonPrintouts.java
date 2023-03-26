package org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts;

public sealed interface PersonPrintouts extends Printouts
    permits AuthorPrintouts, TranslatorPrintouts {}
