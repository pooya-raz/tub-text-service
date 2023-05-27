package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import java.util.Comparator;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.domain.Edition;

@Component
public class EditionComparator implements Comparator<Edition> {
    @Override
    public int compare(Edition o1, Edition o2) {
        return Integer.compare(o1.sortYearGregorian(), o2.sortYearGregorian());
    }
}
