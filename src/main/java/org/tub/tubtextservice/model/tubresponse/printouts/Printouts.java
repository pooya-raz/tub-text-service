package org.tub.tubtextservice.model.tubresponse.printouts;

public sealed interface Printouts
    permits AuthorPrintouts, EditionPrintouts, ManuscriptPrintouts, TitlePrintouts {}
