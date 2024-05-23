package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StageCheckReportsPageElements
{
	public static final String 	STAGE_CHECK_REPORTS_PAGE_TITLE = "Stage Check Report";
	public static final String STAGE_CHECK_REPORT_CONTENT_SET_NON_SELECTED_OPTION = "//select[@id='searchForm:contentSetList:list1']//option[contains(text(),'%s')]";
	public static final String STAGE_CHECK_REPORT_ROW_IN_SEARCH_RESULT_GRID = "//div[@id='gridContainer']/div[@class='yui-dt-bd']/table/tbody[@class='yui-dt-data']/tr";

	@FindBy(how = How.ID, using = "searchForm:year")
	public static WebElement yearDropdown;

	@FindBy(how = How.ID, using = "searchForm:legislation")
	public static WebElement legislationDropdown;

	@FindBy(how = How.ID, using = "searchForm:contentSetList:move1to2")
	public static WebElement rightArrowButton;

	@FindBy(how = How.ID, using = "searchForm:searchButton")
	public static WebElement searchButton;

	@FindBy(how = How.ID, using = "searchForm:contentSetList:list2")
	public static WebElement selectedContentSetList;

	@FindBy(how = How.XPATH, using = "//div[@id='gridContainer']/div[contains(@id,'paginator')]/span/a[text()='Refresh']")
	public static WebElement refreshButton;
}
