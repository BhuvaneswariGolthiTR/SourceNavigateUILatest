package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportPageElements
{
	public static final String QUERY_NOTE_TITLE = "Query Note";
	public static final String EXPORT_TO_EXCEL = "//a/img[@id='exportToExcel']";
	public static final String GRID_LOADING = "//div[@ref = 'gridBody']//span[@class = 'ag-overlay-loading-center']";

	@FindBy(how = How.XPATH, using = "(//div[@id='gridContainer']/div[@class='yui-dt-paginator grid-pg-container']/select[@class='yui-pg-rpp-options'])[2]")
	public static WebElement resultsPerPageSelect;

	@FindBy(how = How.XPATH, using = "//div[@id='gridContainer']//span[@id='yui-pg0-1-page-report23']//em[contains(text(), 'Refresh')]/..")
	public static WebElement refreshButton;
}
