package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups;

public class AssignmentsPageElements
{
    public static final String GRID = "//tbody[@class='yui-dt-data']";

    public static final String GRID_ROW_BY_VIEW_TAG = GRID + "/tr[td/div[contains(text(),'%s')]]";

    public static final String GRID_ASSIGNMENT_STATUS_BY_VIEW_TAG = GRID_ROW_BY_VIEW_TAG + "/td[contains(@class,'AssignmentStatus')]";

    public static final String GRID_PRODUCT_TYPE_BY_VIEW_TAG = GRID_ROW_BY_VIEW_TAG + "/td[contains(@class,'ProductType')]";

    public static final String GRID_PRODUCT_NAME_BY_VIEW_TAG = GRID_ROW_BY_VIEW_TAG + "/td[contains(@class,'ProductName')]";

    public static final String GRID_VIEW_TAG = GRID + "/tr/td/div[contains(text(),'%s')]";

}
