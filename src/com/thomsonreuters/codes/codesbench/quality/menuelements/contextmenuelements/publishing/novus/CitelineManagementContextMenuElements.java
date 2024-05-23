package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.novus;

public class CitelineManagementContextMenuElements
{
    public static final String COMMON_CONTEXT_MENU = "//div[contains(@class,'ag-menu')]//div[contains(@class,'ag-menu-option')]";

    public static final String COPY = COMMON_CONTEXT_MENU+ "//span[text()='Copy']";
    public static final String COPY_WITH_HEADERS = COMMON_CONTEXT_MENU+ "//span[text()='Copy with Headers']";
    public static final String PASTE = COMMON_CONTEXT_MENU+ "/span[text()='Paste']";
    public static final String EXPORT =  COMMON_CONTEXT_MENU+ "//span[text()='Export']";
}