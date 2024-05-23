package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SummaryReportPageElements
{
	public static final String SUMMARY_REPORT_PAGE_TITLE = "Summary Report";
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:contentSet")
	public static WebElement contentSetInputField;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:date")
	public static WebElement dateField;
}
