package org.tub.tubtextservice.helper;

import java.util.List;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.Data;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.MediaWikiPageDetails;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.Meta;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.PrintRequest;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.Query;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.Results;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.TubResponse;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.TitlePrintouts;

public final class TubResponseHelper {
  private TubResponseHelper() {
    throw new UnsupportedOperationException("Utility class and cannot be instantiated");
  }

  private static List<PrintRequest> createPrintRequests() {
    final var r1 = new PrintRequest("", "", "", "_wpg", 2L, null);
    final var r2 = new PrintRequest("Category", "", "", "_wpg", 0L, "");
    return List.of(r1, r2);
  }

  public static TubResponse createTubResponse() {
    final var category =
        new MediaWikiPageDetails(
            "Category:Manuscript-only title",
            "http://10.164.39.147:8080/tub/index.php/Category:Manuscript-only_title",
            14,
            "1",
            "");
    final var author =
        new MediaWikiPageDetails(
            "Author",
            "http://10.164.39.147:8080/tub/index.php/Mu%E1%B8%A5ammad_b._Faraj_al-%E1%B8%A4imyar%C4%AB_al-Najaf%C4%AB",
            0,
            "1",
            "");
    final var printout =
        new TitlePrintouts(
            List.of(category),
            List.of("Treatise (risāla)"),
            List.of("0"),
            List.of("Title Arabic"),
            List.of("Title Transliterated"),
            List.of(author),
            List.of(),
            List.of(),
            List.of());
    final var data =
        new Data(
            printout,
            "Title Transliterated",
            "http://10.164.39.147:8080/tub/index.php/Abw%C4%81b_al-jin%C4%81n_al-mushtamil_%CA%BFal%C4%81_ras%C4%81%CA%BEil_tham%C4%81n",
            0,
            "1",
            "");
    final var result = new Results();
    result.setDataMap("Title Transliterated", data);
    final var meta = new Meta("5d2b4d4b9222e2c0aecb2d97ff595a19", 3L, 0L, "", "0.297985");
    final var query =
        new Query(
            createPrintRequests(), result, "SMW\\Serializers\\QueryResultSerializer", 2L, meta);
    return new TubResponse(0, query);
  }
}
