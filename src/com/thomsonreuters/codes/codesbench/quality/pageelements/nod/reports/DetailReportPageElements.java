package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DetailReportPageElements
{
	public static final String DETAIL_REPORT_PAGE_TITLE = "Detail Report";
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:date")
	public static WebElement dateField;

	@FindBy(how = How.ID, using = "pageForm:pageForm:contentSet")
	public static WebElement contentSetInputField;
}
