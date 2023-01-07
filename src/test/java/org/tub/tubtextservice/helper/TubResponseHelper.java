package org.tub.tubtextservice.helper;

import org.tub.tubtextservice.model.tubresponse.Data;
import org.tub.tubtextservice.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.model.tubresponse.Meta;
import org.tub.tubtextservice.model.tubresponse.PrintRequest;
import org.tub.tubtextservice.model.tubresponse.Query;
import org.tub.tubtextservice.model.tubresponse.Results;
import org.tub.tubtextservice.model.tubresponse.TubResponse;
import org.tub.tubtextservice.model.tubresponse.printouts.TitlePrintouts;

import java.util.List;

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
            "Muḥammad b. Faraj al-Ḥimyarī al-Najafī",
            "http://10.164.39.147:8080/tub/index.php/Mu%E1%B8%A5ammad_b._Faraj_al-%E1%B8%A4imyar%C4%AB_al-Najaf%C4%AB",
            0,
            "1",
            "");
    final var printout =
        new TitlePrintouts(
            List.of(category),
            List.of("Treatise (risāla)"),
            List.of("0"),
            List.of("أبواب الجنان المشتمل على رسائل ثمان"),
            List.of("Abwāb al-jinān al-mushtamil ʿalā rasāʾil thamān"),
            List.of(author),
            List.of(),
            List.of(),
            List.of());
    final var data =
        new Data(
            printout,
            "Abwāb al-jinān al-mushtamil ʿalā rasāʾil thamān",
            "http://10.164.39.147:8080/tub/index.php/Abw%C4%81b_al-jin%C4%81n_al-mushtamil_%CA%BFal%C4%81_ras%C4%81%CA%BEil_tham%C4%81n",
            0,
            "1",
            "");
    final var result = new Results();
    result.setDataMap("Abwāb al-jinān al-mushtamil ʿalā rasāʾil thamān", data);
    final var meta = new Meta("5d2b4d4b9222e2c0aecb2d97ff595a19", 3L, 0L, "", "0.297985");
    final var query =
        new Query(
            createPrintRequests(), result, "SMW\\Serializers\\QueryResultSerializer", 2L, meta);
    return new TubResponse(3, query);
  }
}
