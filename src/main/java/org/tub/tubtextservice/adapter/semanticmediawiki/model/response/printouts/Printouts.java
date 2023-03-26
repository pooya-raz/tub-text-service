package org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts;

/**
 * Interface for all printouts. Printouts are the data returned by Semantic Mediawiki.
 *
 * @see <a href="https://www.semantic-mediawiki.org/wiki/Help:Displaying_information">Semantic
 *     Mediawiki - Displaying Information</a>
 */
public sealed interface Printouts permits DatedPrintouts, TitlePrintouts, PersonPrintouts {}
