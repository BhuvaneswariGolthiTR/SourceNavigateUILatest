package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyScriptListingReportsPageElements
{
	public static final String HIERARCHY_MENU_SCRIPT_LISTING_REPORT_PAGE_TITLE = "Create Script Listing Report";
	public static final String SCRIPT_WITH_GIVEN_VALUE_XPATH = "//select//option[contains(text(), '%s')]";

	@FindBy(how = How.ID, using = "pageForm:scriptListingReport:okButton")
	public static WebElement okButton;

	@FindBy(how = How.ID, using = "pageForm:scriptListingReport:viewType:0")
	public static WebElement wipViewRadioButton;

	@FindBy(how = How.ID, using = "pageForm:scriptListingReport:viewType:1")
	public static WebElement pubViewRadioButton;
}
