package org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts;

public sealed interface PersonPrintouts extends Printouts
    permits AuthorPrintouts, TranslatorPrintouts {}
