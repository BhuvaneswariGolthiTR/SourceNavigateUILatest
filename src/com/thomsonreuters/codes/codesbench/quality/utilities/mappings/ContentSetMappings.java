package com.thomsonreuters.codes.codesbench.quality.utilities.mappings;

import java.util.HashMap;

public class ContentSetMappings
{
    public static String getCurrentSetMapping(String currentContentSet)
    {
        HashMap<String, String> contentSetMap = new HashMap();

        contentSetMap.put("Iowa (Development)", "106");
        contentSetMap.put("USCA(Development)", "100");
        contentSetMap.put("Crown Dependencies", "8140");
        contentSetMap.put("FCA Handbook", "8170");
        contentSetMap.put("Florida (Development)", "113");
        contentSetMap.put("Maine (Development)", "104");
        contentSetMap.put("California (Development)", "2");
        contentSetMap.put("REG Guidance Summaries US", "8040");

        return contentSetMap.get(currentContentSet);
    }
}
