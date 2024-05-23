package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BrowseReportsPageElements
{
	public static final String PAGE_TITLE = "Reports Directory";
	
	@FindBy(how = How.XPATH, using = "//div[@id='breadcrumbArea'][contains(., '\" + PAGE_TITLE + \"')]")
	public static WebElement breadcrumbTitleXpath;

	@FindBy(how = How.ID, using = "pageForm:pageForm:date")
	public static WebElement dateField;

	@FindBy(how = How.ID, using = "pageForm:pageForm:contentSet")
	public static WebElement contentSetInputField;

	public static final String REPORTS_OF_GIVEN_TYPE = "//tbody/tr/td/span[text()='%s']/following-sibling::table//a/span";
}
