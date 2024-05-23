package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

public class SectionGroupingPageElements
{
    public static final String SECTION_GROUPING_PAGE_TITLE = "Grouping";

    public static final String GRID_WAIT = "groupableGridWaitDialog";

    public static final String SECTION_DELTA_GROUPING_NEXT_PAGE_LINK = "//a[@title='Next Page'][1]";

    public static final String SECTION_DELTA_GROUPING_PREVIOUS_PAGE_LINK = "//a[@title='Previous Page'][1]";

    public static final String SECTION_DELTA_GROUPING_SECOND_PAGE_SPAN = "//span[contains(@class, 'current-page') and text() = '2']";

    public static final String SECTION_DELTA_GROUPING_DELTAS_COUNT_IN_NEW_GROUP = "//div[@id='groups']//tbody[@class='yui-dt-data']//tr[td/div[text()='%s']]//td[contains(@headers, 'deltaCount')]/div[text()='%s']";

    public static final String SECTION_DELTA_GROUPING_GROUP_ON_POSITION = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[%s]/td[contains(@headers, 'groupName')]";

    public static final String SECTION_DELTA_GROUPING_HIGHLIGHTED_GROUP_ON_POSITION = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[%s][contains(@class,'selected')]/td[contains(@headers, 'groupName')]";

    public static final String SECTION_DELTA_GROUPING_GROUP_IS_SELECTED = "//div[@id='groups']//tbody[@class='yui-dt-data']/tr[contains(@class, 'yui-dt-selected')]/td[contains(@headers, 'groupDisplayName')]/div";
}
