package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportEditPageElements 
{
	public static final String QUERY_NOTE_EDIT_PAGE_TITLE = "queryNoteWeb/appPages/popups/generic";
	public static final String QUERY_NOTE_EDIT_MODAL_TITLE = "Edit Query Note";

	@FindBy(how = How.ID, using = "pageForm:queryNoteText")
	public static WebElement queryNoteText;
	
	@FindBy(how = How.ID, using = "pageForm:cancelBtn")
	public static WebElement cancelButton;

	@FindBy(how = How.ID, using = "//*[contains(@id, 'pageForm:saveBtn')]")
	public static WebElement saveButton;
}

