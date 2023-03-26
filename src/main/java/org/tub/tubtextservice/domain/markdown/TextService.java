package org.tub.tubtextservice.domain.markdown;

import java.util.List;
import org.tub.tubtextservice.domain.model.tubentry.TubEntry;

/**
 * This interface is responsible for the workflow of converting the data to a text.
 */
public interface TextService {

  /**
   * The main workflow to convert the data to a text.
   *
   * @param entries The data to convert.
   * @return The text.
   */
  String createText(List<TubEntry> entries);
}
