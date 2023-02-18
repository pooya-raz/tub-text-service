package org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts;

public sealed interface PersonPrintouts extends Printouts permits AuthorPrintouts, TranslatorPrintouts {
}
