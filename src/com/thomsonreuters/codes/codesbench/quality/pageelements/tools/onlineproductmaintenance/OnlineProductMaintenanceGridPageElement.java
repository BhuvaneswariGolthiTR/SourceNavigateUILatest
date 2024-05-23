package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance;

public class OnlineProductMaintenanceGridPageElement
{
    public static final String GRID = "//div[@id='viewScriptsGridDiv']//tbody[@class='yui-dt-data']";
    public static final String ROWS = GRID + "/tr";
    public static final String VIEW_TAG_WITH_TEXT = GRID + "/tr/td/div[text()='%s']";
    public static final String VIEW_TAG_WITH_STRIKE_TEXT = GRID + "/tr/td/div/strike[text()='%s']";
    public static final String ROW_WITH_VIEW_TAG_GIVEN = GRID + "/tr[td[contains(@class,'yui-dt-col-ViewTag')]/div[text()='%s']]";
    public static final String PRODUCT_ID = "/td[contains(@class, 'yui-dt-col-ProductID')]";
    public static final String NODE_ASSIGNMENT = "/td[contains(@class, 'NodeAssignmentStatus')]";
}
