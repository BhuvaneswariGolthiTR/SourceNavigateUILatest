package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class XmlExtractStateFeedPageElements
{
	public static final String XML_EXTRACT_STATE_FEED_PAGE_TITLE = "XML Extract - State Feed";
	public static final String LIST_OF_YEAR_OPTIONS_XPATH = "//select[@name='pageForm:j_idt24']/option";

	@FindBy(how = How.ID, using = "pageForm:groupingName")
	public static WebElement groupNameInput;

	@FindBy(how = How.ID, using = "pageForm:j_idt24")
	public static WebElement yearInputDropdown;

	@FindBy(how = How.XPATH, using = "//select[@name='pageForm:j_idt24']/option[1]")
	public static WebElement selectedYearInputOption;

	@FindBy(how = How.ID, using = "submitButton-button")
	public static WebElement submitButton;

	@FindBy(how = How.ID, using = "cancelButton-button")
	public static WebElement cancelButton;

	@FindBy(how = How.LINK_TEXT, using = "Close")
	public static WebElement close;

	@FindBy(how = How.XPATH, using = "//form[@id='pageForm']/a")
	public static WebElement workflowLink;
}
