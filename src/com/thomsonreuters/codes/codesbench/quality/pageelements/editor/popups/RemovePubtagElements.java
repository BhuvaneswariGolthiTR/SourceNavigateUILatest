package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RemovePubtagElements 
{
	// Title Page
	public static String PAGE_TITLE = "Remove Pubtag";
	
	// Single right arrow button
	@FindBy(how = How.XPATH, using = "//input[@id='addOneButton']")
	public static WebElement singleRightArrowButton;
	
	// Available Pubtag List
	@FindBy(how = How.XPATH, using = "//select[@id='availablelist']")
	public static WebElement availablePubtagList;

	// OK button
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
	public static WebElement okButton;
	
	public static final String AVAILABLE_PUBTAG_LIST_XPATH = "//select[@id='availablelist']";
}
