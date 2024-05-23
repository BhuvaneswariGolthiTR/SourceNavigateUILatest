package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups;

public class ViewAssignmentStatusPageElements
{
    public static final String PAGE_TITLE = "View Assignment Status";

    public static final String ASSIGNMENTS_STATUS_GRID = "//tbody[@class='yui-dt-data']/tr";

    public static final String CONTENT_SET_NAME = ASSIGNMENTS_STATUS_GRID + "/td/div[contains(text(),'%s')]";

    public static final String NODE_ASSIGNMENT_BY_CONTENT_SET_NAME = ASSIGNMENTS_STATUS_GRID + "[td/div[contains(text(),'%s')]]/td[contains(@class,'NodeAssignmentStatus')]/div";

}
