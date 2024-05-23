package com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReportCentralFiltersPageElements
{
	@FindBy(how = How.ID, using = "legisYearFilter")
	public static WebElement legislativeYearFilter;
	
	@FindBy(how = How.ID, using = "reportTypeFilter")
	public static WebElement reportTypeFilter;

	@FindBy(how = How.ID, using = "requestNameFilter")
	public static WebElement requestersNameFilter;
	
	@FindBy(how = How.ID, using = "reportIdentifierFilter")
	public static WebElement reportIdentifierFilter;
}
