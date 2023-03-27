package org.tub.tubtextservice.application.usecase.docx.dto.in;

import java.util.List;
import org.tub.tubtextservice.domain.TubEntry;

public record EntriesDto(
        List<TubEntry> entries
) {}
