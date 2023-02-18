package org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts;

public sealed interface Printouts
        permits DatedPrintouts, TitlePrintouts, PersonPrintouts {}
