package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportFiltersPageElements
{
	@FindBy(how = How.ID, using = "queryTextFilter")
	public static WebElement queryTextTextBox;

	@FindBy(how = How.ID, using = "typeFilter")
	public static WebElement typeFilter;

	@FindBy(how = How.ID, using = "statusFilter")
	public static WebElement statusFilter;

	@FindBy(how = How.ID, using = "actionDateFilter")
	public static WebElement actionDateFilter;

	@FindBy(how = How.ID, using = "volsFilter")
	public static WebElement vols;

	@FindBy(how = How.ID, using = "targetValueFilter")
	public static WebElement targetValue;


}
