package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.FINAL_INDEX_ENTRIES_BOX;

public class IndexingFinalIndexEntriesPageElements
{
    public static final String INDEX_ITEMS_WRAPPER = "//div[@class='index-items-wrapper']";
    public static final String FINAL_INDEX_ENTRIES_PATTERN = FINAL_INDEX_ENTRIES_BOX + INDEX_ITEMS_WRAPPER +
            "/button[text()=' %s ']";
    public static final String EMPTY_FINAL_INDEX_ENTRIES_BOX = FINAL_INDEX_ENTRIES_BOX +
            "//div[@class='index-items-wrapper' and not(child::button)]";
    public static final String FINAL_INDEX_ENTRY = INDEX_ITEMS_WRAPPER + "/button";

    // ---------- Validation ----------

    public static final String VALIDATION_DOT = FINAL_INDEX_ENTRIES_PATTERN + "/source-nav-validation-dots";
    public static final String VALIDATION_DOT_STYLE = VALIDATION_DOT + "/span[@class='validation-dot']";
    public static final String VALIDATION_DOT_STYLE_COLOR = VALIDATION_DOT + "/span[contains(@style,'%s')]";
    public static final String VALIDATION_HOVER_TOOLTIP = FINAL_INDEX_ENTRY + "/ngb-tooltip-window";

    public static final String TOOLTIP_VALIDATION_MESSAGE = "//span[contains(@style,'%s')]/parent::source-nav-validation-dots/following-sibling::div[@class='validation-issue-description']";
    public static final String TOOLTIP_VALIDATION_MESSAGE_FRAGMENTS = TOOLTIP_VALIDATION_MESSAGE + "//span[contains(@class,'validation-issue-fragment')]";
    public static final String TOOLTIP_VALIDATION_SPAN = "//span/parent::source-nav-validation-dots/following-sibling::div[@class='validation-issue-description']";

    public static final String BLUE_DOT_MESSAGE = "References contain a minimum of two fragments. Consider adding";
    public static final String PURPLE_DOT_MESSAGE = "Certain fragments should not be used as headings:";
    public static final String PINK_DOT_MESSAGE = "Certain fragments should not be used in the same reference:";
    public static final String ORANGE_DOT_MESSAGE = "Certain fragments should be used in a different order:";
    public static final String YELLOW_DOT_MESSAGE = "Only one reference should be made per heading. Delete or edit extra references.";

    public static final String BLUE_DOT = "rgb(100, 143, 255)";
    public static final String PINK_DOT = "rgb(220, 38, 127)";
    public static final String YELLOW_DOT = "rgb(255, 176, 0)";
    public static final String ORANGE_DOT = "rgb(254, 97, 0)";
    public static final String PURPLE_DOT = "rgb(120, 94, 240)";


}
