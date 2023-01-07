package org.tub.tubtextservice.model;

import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.TitlePrintouts;

import java.util.List;
import java.util.Map;

public record TubData(
    List<TitlePrintouts> titles,
    Map<String, AuthorPrintouts> authors,
    Map<String, ManuscriptPrintouts> manuscripts,
    Map<String, EditionPrintouts> editions) {}
