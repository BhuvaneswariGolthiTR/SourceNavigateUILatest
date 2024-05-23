package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class IndexReportPageElements
{
	public static final String INDEX_REPORT_PAGE_TITLE = "Index Report";

	public static final String INDEX_REPORT_REPORT_TYPE_CHECKBOX = "//input[@type='radio' and @name='pageForm:selectReport' and @value='%s']";
	public static final String INDEX_REPORT_SORT_ORDER_TYPE_CHECKBOX = "//input[@type='radio' and @name='pageForm:selectSortOrderType' and @value='%s']";
	public static final String INDEX_REPORT_YEAR_DROPDOWN = "//select[@id='pageForm:selectYear']";
	public static final String INDEX_REPORT_CONTENT_SET_DROPDOWN = "//select[@id='pageForm:selectContentSet']";
	public static final String INDEX_REPORT_SESSION_DROPDOWN = "//select[@id='pageForm:selectSession']";
	public static final String INDEX_REPORT_SELECTED_RENDITIONS_TO_MOVE_OPTIONS = "//select[@id='pageForm:selectedRenditionsToMove']";
	public static final String INDEX_REPORT_SELECTED_RENDITIONS_MOVE_BUTTON = "//input[@value='>']";
	public static final String INDEX_REPORT_SELECTED_RENDITIONS_RUN_REPORT_BUTTON = "//input[@value='Run Report']";

	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement nextButton;

	@FindBy(how = How.ID, using = "pageForm:cancel")
	public static WebElement cancelButton;

	@FindBy(how = How.ID, using = "pageForm:selectedRenditionsToMove")
	public static WebElement renditionsToMoveOptions;
}
