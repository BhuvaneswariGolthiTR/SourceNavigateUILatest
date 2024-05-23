package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WestIdWizardPageElements
{
	public static final String WEST_ID_WIZARD_PAGE_TITLE = "West ID Wizard";

	@FindBy(how = How.ID, using = "pageForm:enteredMetaData")
	public static WebElement westIdTextBox;

	//SCP changes
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:scpReplace:0' and @type='radio']")
	public static WebElement scpReplaceRadioButton;

	@FindBy(how = How.ID, using = "pageForm:scpStockNote")
	public static WebElement scpReplaceDropdown;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:scpNoChange:0' and @type='radio']")
	public static WebElement scpNoChangeRadioButton;

	//HG2 changes
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:hg2NoChange:0' and @type='radio']")
	public static WebElement hg2NoChangeRadioButton;

	//Buttons
	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement submitButton;

	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;
}
