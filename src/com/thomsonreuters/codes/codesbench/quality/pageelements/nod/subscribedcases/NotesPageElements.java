package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NotesPageElements 
{
	public static final String NOTES_PAGE_TITLE = "Notes";

	@FindBy(how = How.ID, using = "pageForm:renditionLeveLTextArea")
	public static WebElement noteTextArea;
	
	public static final String NOTES_WINDOW_ID = "//class[@id='dialog_h']";
}
