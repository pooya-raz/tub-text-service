package org.tub.tubtextservice.model.domain;

import org.tub.tubtextservice.model.domain.year.editiondate.EditionDate;

public record Manuscript(String location, String city, String manuscriptNumber, EditionDate date) {

}
