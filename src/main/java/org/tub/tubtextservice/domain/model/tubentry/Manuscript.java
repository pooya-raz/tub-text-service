package org.tub.tubtextservice.domain.model.tubentry;

import org.tub.tubtextservice.domain.model.tubentry.year.editiondate.EditionDate;

public record Manuscript(String location, String city, String manuscriptNumber, EditionDate date) {

}
