package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.activity;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ActivityUserLogContextMenuElements
{
	@FindBy(how = How.LINK_TEXT, using = "Create/Edit Note")
	public static WebElement createEditNote;
	
	@FindBy(how = How.LINK_TEXT, using = "Edit")
	public static WebElement edit;

	@FindBy(how = How.LINK_TEXT, using = "Cancel Check Out")
	public static WebElement cancelCheckOut;
	
	@FindBy(how = How.LINK_TEXT, using = "Validate")
	public static WebElement validate;
	
	@FindBy(how = How.LINK_TEXT, using = "Check In (Legislative)")
	public static WebElement checkInLegislative;
	
	@FindBy(how = How.LINK_TEXT, using = "Check In (Editorial)")
	public static WebElement checkInEditorial;
}
