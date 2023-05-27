package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import java.util.Comparator;
import org.tub.tubtextservice.domain.TubEntry;

public class EntryComparator implements Comparator<TubEntry> {
    @Override
    public int compare(TubEntry o1, TubEntry o2) {
        return Long.compare(o1.sortTimeStamp(), o2.sortTimeStamp());
    }
}
