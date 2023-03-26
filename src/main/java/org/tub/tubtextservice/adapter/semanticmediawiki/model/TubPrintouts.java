package org.tub.tubtextservice.adapter.semanticmediawiki.model;

import java.util.ArrayList;
import java.util.Map;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.Data;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.AuthorPrintouts;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.EditionPrintouts;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.TitlePrintouts;
import org.tub.tubtextservice.domain.TubEntry;

/**
 * A record that represents the data available in the TUB wiki. Each field represents the categories
 * available in the wiki, and are necessary for the construction of an {@link TubEntry}.
 *
 * @param titles titles in TUB. The key is the title of the Wiki article found in {@link
 *     Data#fulltext()}. Since each {@link TubEntry} is based on one title, it cannot be a list.
 * @param authors authors in TUB. The key is the title of the Wiki article found in {@link
 *     Data#fulltext()}.
 * @param manuscripts manuscripts in TUB. The key is the value in {@link
 *     ManuscriptPrintouts#manuscriptOfTitle()} which refers to the key of a title. One title can
 *     have multiple manuscripts.
 * @param editions editions in TUB. The key is the value in {@link
 *     EditionPrintouts#editionOfTitle()} which refers to the key of a title. One title can have
 *     multiple editions.
 */
public record TubPrintouts(
    Map<String, TitlePrintouts> titles,
    Map<String, AuthorPrintouts> authors,
    Map<String, ArrayList<ManuscriptPrintouts>> manuscripts,
    Map<String, ArrayList<EditionPrintouts>> editions) {}
