package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

public class RandomBillReportContentSelectPageElements
{
    public static final String NONSELECTED_CONTENT_SET_OPTION = "//select[contains(@id, 'list1')]/option[text()='%s']";
    public static final String NONSELECTED_CONTENT_SET_LIST = "//select[contains(@id, 'list1')]";
    public static final String SELECTED_CONTENT_SET_LIST= "//select[contains(@id, 'list2')]";
    public static final String SELECT_ONE_OPTION_ARROW = "//input[contains(@id, 'move1to2')]";
    public static final String SELECT_ALL_OPTIONS_ARROW = "//input[contains(@id, 'moveAll1to2')]";
    public static final String DESELECT_ONE_OPTION_ARROW = "//input[contains(@id, 'move2to1')]";
    public static final String DESELECT_ALL_OPTIONS_ARROW = "//input[contains(@id, 'moveAll2to1')]";
    public static final String SEARCH_BUTTON = "//button[@id='searchForm:searchButton-button']";
    public static final String YEAR_DROPDOWN_XPATH = "//select[@id='searchForm:year']";
}
