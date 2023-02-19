package org.tub.tubtextservice.service.tubapi.model;

import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;

import java.util.ArrayList;
import java.util.Map;

public record TubPrintOuts(
    Map<String, ArrayList<TitlePrintouts>> titles,
    Map<String, ArrayList<AuthorPrintouts>> authors,
    Map<String, ArrayList<ManuscriptPrintouts>> manuscripts,
    Map<String, ArrayList<EditionPrintouts>> editions)
    implements ApiData {}
