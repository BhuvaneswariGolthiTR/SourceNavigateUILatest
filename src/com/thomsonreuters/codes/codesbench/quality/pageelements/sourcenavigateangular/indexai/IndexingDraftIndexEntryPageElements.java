package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.DRAFT_INDEX_ENTRY;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.DRAFT_INDEX_ENTRY_BOX;

public class IndexingDraftIndexEntryPageElements
{
    public static final String EMPTY_DRAFT_INDEX_ENTRY_BOX = DRAFT_INDEX_ENTRY +
            "/div[@dragula='DRAFT_ENTITIES' and not(child::div)]";
    public static final String DATA_ENTRY_FIELD = DRAFT_INDEX_ENTRY_BOX + "/div[%s]/span";
    public static final String DATA_ENTRY_FIELD_TEXT = DATA_ENTRY_FIELD + "[text()='%s']";
    public static final String INPUT_DRAFT_INDEX_ENTRY_BY_NUMBER = DRAFT_INDEX_ENTRY_BOX + "/div[%s]/input";
    public static final String INPUT_DRAFT_INDEX_ENTRY_BY_TEXT = DRAFT_INDEX_ENTRY_BOX + "/div/input/following-sibling::span[contains(text(),'%s')]";
    public static final String READONLY_DRAFT_INDEX_ENTRY_SPAN = DRAFT_INDEX_ENTRY_BOX + "/div/input[@readonly]/following-sibling::span[text()='%s']";
    public static final String READONLY_DRAFT_INDEX_ENTRY_INPUT = DRAFT_INDEX_ENTRY_BOX + "/div/span[text()='%s']/preceding-sibling::input[@readonly]";
    public static final String SELECTED_DRAFT_INDEX_ENTRY = DRAFT_INDEX_ENTRY_BOX + "/div[contains(@class,'entryFragment selected')][%s]/span[text()='%s']";
    public static final String DRAFT_ENTRY_SELECTOR_PATTERN = "div[dragula] > div:nth-child(%s)";


}
