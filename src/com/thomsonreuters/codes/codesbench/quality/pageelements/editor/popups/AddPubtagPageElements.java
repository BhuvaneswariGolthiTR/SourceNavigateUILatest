package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddPubtagPageElements
{
	// Title Page
	public static String PAGE_TITLE = "Add Pubtag";
	
	// Single right arrow button
	@FindBy(how = How.XPATH, using = "//input[@id='addOneButton']")
	public static WebElement singleRightArrowButton;
	
	// Available Pubtag List
	@FindBy(how = How.XPATH, using = "//select[@id='availablelist']")
	public static WebElement availableList;
	
	public static final String AVAILABLE_LIST = "//select[@id='availablelist']";

	public static final String AVAILABLE_LIST_ID = "availablelist";
}
