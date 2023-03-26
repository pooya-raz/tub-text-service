package org.tub.tubtextservice.adapter.semantic.model.tubresponse.printouts;

public sealed interface PersonPrintouts extends Printouts
    permits AuthorPrintouts, TranslatorPrintouts {}
