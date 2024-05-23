package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReportPageElements
{
	public static final String REPORT_PAGE_TITLE = "Report";
	public static final String WORKSHEET_REPORT_TITLE = "WorkSheet Report";
	public static final String UNUSED_REPORT_TITLE = "Unused Report";
	public static final String INTEGRATION_REPORT_TITLE = "Integration Report";
	public static final String REPORT_TITLE = "//div[@id='resizablepanel']/div[text()='%s']";

	@FindBy(how = How.ID, using = "printButton")
	public static WebElement printButton;

	@FindBy(how = How.ID, using = "cancelButton")
	public static WebElement closeButton;
}
