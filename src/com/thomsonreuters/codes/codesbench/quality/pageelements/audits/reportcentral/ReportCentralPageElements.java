package com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReportCentralPageElements
{
	public static final String REPORT_CENTRAL_PAGE_TITLE = "Report Central";

	//top bar/legis year
	@FindBy(how = How.ID, using = "legisYearFilter")
	public static WebElement legislativeYearTextboxXpath;

	@FindBy(how = How.XPATH, using = "//*[@id='legisYearToggle']//button")
	public static WebElement legislativeYearToggleButtonXpath;

	@FindBy(how = How.ID, using = "legisYearFilterContainer")
	public static WebElement legislativeYearDropdownXpath;


	//top bar/report type
	@FindBy(how = How.ID, using = "reportTypeFilter")
	public static WebElement reportTypeTextboxXpath;

	@FindBy(how = How.XPATH, using = "//*[@id='reportTypeToggle']//button")
	public static WebElement reportTypeToggleButtonXpath;

	@FindBy(how = How.ID, using = "reportTypeFilterContainer")
	public static WebElement reportTypeDropdownXpath;

	//top bar/report identifier
	@FindBy(how = How.ID, using = "reportIdentifierFilter")
	public static WebElement reportIdentifierTextboxXpath;

	//top bar/request name
	@FindBy(how = How.ID, using = "requestNameFilter")
	public static WebElement requestersNameTextboxXpath;

	@FindBy(how = How.XPATH, using = "//*[@id='requestNameToggle']//button")
	public static WebElement requestersNameToggleButtonXpath;

	@FindBy(how = How.ID, using = "requestNameFilterContainer")
	public static WebElement requestersNameDropdownXpath;

	//top bar/report date
	@FindBy(how = How.ID, using = "requestDateFilter")
	public static WebElement requestDateTextboxXpath;

	@FindBy(how = How.XPATH, using = "//*[@id='requestDateToggle']//button")
	public static WebElement requestDateToggleButtonXpath;

	@FindBy(how = How.ID, using = "requestDateFilterContainer")
	public static WebElement requestDateDropdownXpath;

	//top bar/workflow status
	@FindBy(how = How.ID, using = "workflowStatusFilter")
	public static WebElement workflowStatusTextboxXpath;

	@FindBy(how = How.XPATH, using = "//*[@id='workflowStatusToggle']//button")
	public static WebElement workflowStatusToggleButtonXpath;

	@FindBy(how = How.ID, using = "workflowStatusFilterContainer")
	public static WebElement workflowStatusDropdownXpath;

	//columns
	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt0-th-yui-dt-col4 yui-dt0-th-legisYear ']/div")
	public static WebElement legislativeYearColumnXpath;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt0-th-yui-dt-col7 yui-dt0-th-reportType ']/div")
	public static WebElement reportTypeColumnXpath;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt0-th-yui-dt-col10 yui-dt0-th-reportIdentifier ']/div/a")
	public static WebElement reportIdentifierColumnLinkXpath;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt0-th-yui-dt-col13 yui-dt0-th-requestName ']/div")
	public static WebElement requestNameColumnXpath;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt0-th-yui-dt-col16 yui-dt0-th-requestDate ']/div")
	public static WebElement requestDateColumnXpath;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt0-th-requestTime ']/div")
	public static WebElement requestTimeColumnXpath;

	public static final String WORKFLOW_STATUS_COLUMN_XPATH = "//td[@headers='yui-dt0-th-yui-dt-col20 yui-dt0-th-workflowStatus ']/div";

	//bottom buttons
	@FindBy(how = How.XPATH, using = "//div[@id='yui-dt0-paginator1']/span/a[text()='Refresh']")
	public static WebElement refreshButton;

	@FindBy(how = How.XPATH, using = "//a[@onclick='CodesGrid.clearSort()']")
	public static WebElement clearSortButtonXpath;

	@FindBy(how = How.XPATH, using = "//a[@onclick='CodesGrid.clearAllFilters()']")
	public static WebElement clearAllFiltersButtonXpath;

	@FindBy(how = How.XPATH, using = "//select[@title='Rows per page']")
	public static WebElement resultsPerPageDropdownXpath;

	//page buttons
	@FindBy(how = How.XPATH, using = "//span[@class='yui-pg-first']")
	public static WebElement firstPageButtonXpath;

	@FindBy(how = How.XPATH, using = "//span[@class='yui-pg-previous']")
	public static WebElement previousPageButtonXpath;

	@FindBy(how = How.XPATH, using = "//span[@class='yui-pg-next']")
	public static WebElement nextPageButtonXpath;

	@FindBy(how = How.XPATH, using = "//span[@class='yui-pg-last']")
	public static WebElement lastPageButtonXpath;

	public static final String REPORT_IDENTIFIER = "//td[contains(@headers,'reportIdentifier')]//div[@class='yui-dt-liner']//a[text()='%s']";
}
