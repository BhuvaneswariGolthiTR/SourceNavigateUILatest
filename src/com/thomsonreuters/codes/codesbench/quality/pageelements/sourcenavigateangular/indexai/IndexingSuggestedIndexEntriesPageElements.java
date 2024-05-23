package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.SUGGESTED_INDEX_ENTRIES;

public class IndexingSuggestedIndexEntriesPageElements
{
    public static final String NO_SUGGESTED_INDEX_ENTRIES_PLACEHOLDER = SUGGESTED_INDEX_ENTRIES + "//div[@class='placeholder-text']";
    public static final String SUGGESTED_ENTRY = SUGGESTED_INDEX_ENTRIES + "//div[@class='entries-container']/label";
    public static final String SUGGESTED_ENTRY_TEXT = SUGGESTED_ENTRY + "[text()=' %s ']";
    public static final String SUGGESTED_ENTRY_CHECKBOX = SUGGESTED_ENTRY_TEXT + "/input[@type='checkbox']";
    public static final String SELECTED_SUGGESTED_ENTRY = SUGGESTED_ENTRY + "[contains(@class,'selected-entry') and text()=' %s ']";
    public static final String LIGHT_BULB = SUGGESTED_INDEX_ENTRIES + "/h3/span[contains(@class,'warning-icon')]";
    public static final String LIGHT_BULB_TOOLTIP_WINDOW = LIGHT_BULB + "/following-sibling::ngb-tooltip-window";


}
