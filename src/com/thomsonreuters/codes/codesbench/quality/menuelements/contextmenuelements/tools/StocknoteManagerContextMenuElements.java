package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StocknoteManagerContextMenuElements
{
	@FindBy(how = How.LINK_TEXT, using = "Hot Key setup")
	public static WebElement hotKeySetup;
	
	@FindBy(how = How.LINK_TEXT, using = "Create New Note")	
	public static WebElement createNewNote;
	
	@FindBy(how = How.LINK_TEXT, using = "Delete Note")
	public static WebElement deleteNote;

	@FindBy(how = How.LINK_TEXT, using = "Properties")
	public static WebElement properties;

	@FindBy(how = How.LINK_TEXT, using = "Stocknote Search and Replace")
	public static WebElement searchAndReplace;

	@FindBy(how = How.LINK_TEXT, using = "Copy Note")
	public static WebElement copyNote;

	@FindBy(how = How.LINK_TEXT, using = "Copy To Another Content Set")
	public static WebElement copyToAnotherContentSet;

	@FindBy(how = How.LINK_TEXT, using = "Select to be Inserted")
	public static WebElement selectToBeInserted;
}
