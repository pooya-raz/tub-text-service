package org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts;

public sealed interface PersonPrintouts extends Printouts
    permits AuthorPrintouts, TranslatorPrintouts {}
