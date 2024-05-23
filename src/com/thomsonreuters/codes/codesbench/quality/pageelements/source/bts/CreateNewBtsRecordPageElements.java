package com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateNewBtsRecordPageElements 
{
    public static final String CREATE_NEW_BTS_RECORD_PAGE_TITLE = "Create New BTS Record"; 
	public static final String CREATE_NEW_BTS_RECORD_CREATE_POPUP_MESSAGE_XPATH = "//div[contains(@class, 'ui-widget-header')]/span[text() = 'Not same legislative year!']/../..//div[contains(@class, 'ui-widget-content')]/p[text() = 'Record will be inserted in a different location. Continue?']";
	public static final String OKAY_BUTTON = "//span[contains(text(),'Ok')]";
	
	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement createButton;
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Ok')]")
	public static WebElement okButton;

	@FindBy(how = How.LINK_TEXT, using = "Prior Year")
	public static WebElement PriorYearTab;
	
	@FindBy(how = How.LINK_TEXT, using = "Other")
	public static WebElement OtherTab;
	
    @FindBy(how = How.ID, using = "pageForm:docNum")
	public static WebElement documentNumberField;
    
    @FindBy(how = How.ID, using = "pageForm:docType")
	public static WebElement documentTypeDropdown;
    
    @FindBy(how = How.NAME, using = "pageForm:legYear")
	public static WebElement legislativeYear;
    
}
