package org.tub.tubtextservice.service.tubapi.model;

import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;

import java.util.List;
import java.util.Map;

public record TubPrintOuts(
    List<TitlePrintouts> titles,
    Map<String, AuthorPrintouts> authors,
    Map<String, ManuscriptPrintouts> manuscripts,
    Map<String, EditionPrintouts> editions) {}
