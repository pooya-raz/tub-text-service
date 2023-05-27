package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import java.util.Comparator;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.domain.Commentary;

@Component
public class CommentaryComparator implements Comparator<Commentary> {
    @Override
    public int compare(Commentary o1, Commentary o2) {
        return o1.sortTimeStamp().compareTo(o2.sortTimeStamp());
    }
}
