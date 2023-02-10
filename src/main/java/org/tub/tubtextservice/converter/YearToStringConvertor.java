package org.tub.tubtextservice.converter;

import org.tub.tubtextservice.model.tubresponse.MediaWikiDate;

import java.util.List;

public class YearToStringConvertor {
    public static String convert(List<Integer> year , List<String> yearText) {
            var result = "";
            if (!year.isEmpty()&& year.get(0) != null) {
                result = year.get(0).toString();
            }
            if (!yearText.isEmpty()) {
                result = yearText.get(0);
            }
            return result;
        }
    public static String convertMediaWikiDate(List<MediaWikiDate> gregorian, List<String> gregorianText) {
        var year = "";

        if (!gregorian.isEmpty()) {
            year = MediaWikiDateConvertor.convert(gregorian.get(0));
        }
        if (!gregorianText.isEmpty()) {
            year = gregorianText.get(0);
        }
        return year;
    }
}
