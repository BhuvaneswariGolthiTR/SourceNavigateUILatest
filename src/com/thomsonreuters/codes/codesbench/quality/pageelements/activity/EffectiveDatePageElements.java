package com.thomsonreuters.codes.codesbench.quality.pageelements.activity;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EffectiveDatePageElements
{
	public static final String EFFECTIVE_DATE_PAGE_TITLE = "Effective Date";
	public static final String DELTA_EFFECTIVE_DATE_PAGE_TITLE = "Delta Effective Date";
	public static final String SECTION_EFFECTIVE_DATE_PAGE_TITLE = "Section Effective Date";
	public static final String INPUT_EFFECTIVE_DATE = "//input[@id= 'pageForm:effectiveDate']";

	@FindBy(how = How.ID, using = "pageForm:effectiveDate")
	public static WebElement effectiveDate;
	
	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement saveButton;
	
	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;

	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Run Cite Locate to Update Integration Status')]//input[@type='checkbox']")
	public static WebElement runCiteLocateCheckbox;
}
