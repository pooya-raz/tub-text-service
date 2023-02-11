package org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts;

public sealed interface Printouts
        permits AuthorPrintouts, DatedPrintouts, TitlePrintouts {}
