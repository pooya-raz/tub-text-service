package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import java.util.Comparator;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.domain.Manuscript;

@Component
public class ManuscriptComparator implements Comparator<Manuscript> {
    @Override
    public int compare(Manuscript o1, Manuscript o2) {
        return Integer.compare(o1.sortYearGregorian(), o2.sortYearGregorian());
    }
}
