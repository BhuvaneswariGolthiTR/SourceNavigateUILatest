package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StocknoteHotKeysPageElements
{
	public static final String STOCKNOTE_HOT_KEY_SETUP_PAGE_TITLE = "Hot Key setup";
	public static final String STOCKNOTE_HOT_KEY_SETUP_PAGE_TITLE_DIV_XPATH = "//div[.='Hot Key setup']";
	
	@FindBy(how = How.ID, using = "pageForm:j_idt34:1")
	public static WebElement shiftAltSelector;
	
	@FindBy(how = How.ID, using = "pageForm:name")
	public static WebElement keyInputBox;
	
	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement saveButton;
	
	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;
	
	@FindBy(how = How.ID, using = "pageForm:hotkeyName")
	public static WebElement hotKeyCombination;
	
	@FindBy(how = How.ID, using = "pageForm:j_idt34:0")
	public static WebElement altSelector;
	
}
