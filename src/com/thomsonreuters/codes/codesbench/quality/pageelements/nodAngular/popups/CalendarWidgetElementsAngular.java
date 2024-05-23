package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups;

public class CalendarWidgetElementsAngular
{
    public static final String DATE_PICKER = "//ngb-datepicker[contains(@class, 'bento-datepicker')]";
    public static final String YEAR = DATE_PICKER + "//select[@title='Select year']";
    public static final String FIRST_YEAR = YEAR + "/option[1]";
    public static final String MONTH = DATE_PICKER + "//select[@title='Select month']";
    public static final String DATE = DATE_PICKER + "//div[@class='btn-light' and text()='%s']";
    public static final String PICKER_WIDGET = "//div[@ref='%s']//div[@class='picker-container']" + DATE_PICKER;
    public static final String PANEL_FROM_1 = "ePanelFrom1";
    public static final String PANEL_FROM_2 = "ePanelFrom2";
    public static final String PANEL_TO_1 = "ePanelTo1";
    public static final String PANEL_TO_2 = "ePanelTo2";
}
