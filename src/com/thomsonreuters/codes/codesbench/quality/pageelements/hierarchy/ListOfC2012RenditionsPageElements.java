package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

public class ListOfC2012RenditionsPageElements
{
    public static final String PAGE_TITLE = "//title";
    public static final String EXPECTED_TITLE = "List of C2012 Renditions";
    public static final String EXPECTED_WINDOW_TITLE = "Set Law Tracking";

    //table
    public static final String LAW_TRACKING_RAW = "//tbody[2]/tr[%s]";
    public static final String TABLE_CELL = "//tbody[2]/tr[%s]/td[%s]";
    //context menu
    public static final String SET_LAW_TRACKING = "//li/a[@class='yuimenuitemlabel' and text() = 'Set Law Tracking']";


}
