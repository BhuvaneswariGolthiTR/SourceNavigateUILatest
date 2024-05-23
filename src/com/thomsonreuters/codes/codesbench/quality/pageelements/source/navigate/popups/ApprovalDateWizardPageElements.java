package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ApprovalDateWizardPageElements
{
	public static final String APPROVAL_DATE_PAGE_TITLE = "Approval Date Wizard";

	@FindBy(how = How.ID, using = "pageForm:enteredMetaData")
	public static WebElement approvalDateTextBox;

	//APV changes
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:apvReplace:0' and @type='radio']")
	public static WebElement apvReplaceRadioButton;

	@FindBy(how = How.ID, using = "pageForm:apvStockNote")
	public static WebElement apvReplaceDropdown;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:apvNoChange:0' and @type='radio']")
	public static WebElement apvNoChangeRadioButton;

	//Buttons
	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement submitButton;

	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;
}
