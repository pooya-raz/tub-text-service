package org.tub.tubtextservice.model;

import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.TitlePrintouts;

import java.util.List;

public record TubData(
    List<TitlePrintouts> titles,
    List<AuthorPrintouts> authors,
    List<ManuscriptPrintouts> manuscripts,
    List<EditionPrintouts> editions) {}
