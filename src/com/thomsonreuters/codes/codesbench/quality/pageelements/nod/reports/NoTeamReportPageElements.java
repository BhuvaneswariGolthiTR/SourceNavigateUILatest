package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NoTeamReportPageElements
{
	public static final String NO_TEAM_REPORT_PAGE_TITLE = "No Team Report";
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:date")
	public static WebElement dateField;
}
