package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ClassNumberWizardPageElements
{
	public static final String CLASS_NUMBER_WIZARD_PAGE_TITLE = "Class Number Wizard";

	@FindBy(how = How.ID, using = "pageForm:enteredMetaData")
	public static WebElement classNumberTextBox;

	//SCP changes
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:scpReplace:0' and @type='radio']")
	public static WebElement scpReplaceRadioButton;

	@FindBy(how = How.ID, using = "pageForm:scpStockNote")
	public static WebElement scpReplaceDropdown;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:scpNoChange:0' and @type='radio']")
	public static WebElement scpNoChangeRadioButton;

	//DTYPE changes
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:dtypeNoChange:0' and @type='radio']")
	public static WebElement dtypeNoChangeRadioButton;

	//HG1 changes
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:hg1NoChange:0' and @type='radio']")
	public static WebElement hg1NoChangeRadioButton;

	//HG2 changes
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:hg2NoChange:0' and @type='radio']")
	public static WebElement hg2NoChangeRadioButton;

	//Buttons
	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement submitButton;

	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;
}
