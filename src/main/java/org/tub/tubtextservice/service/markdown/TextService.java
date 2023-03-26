package org.tub.tubtextservice.service.markdown;

import java.util.List;
import org.tub.tubtextservice.model.domain.Entry;

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
  String createText(List<Entry> entries);
}
