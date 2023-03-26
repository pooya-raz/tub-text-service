package org.tub.tubtextservice.domain.tubdata.model.tubresponse.printouts;

public sealed interface PersonPrintouts extends Printouts
    permits AuthorPrintouts, TranslatorPrintouts {}
