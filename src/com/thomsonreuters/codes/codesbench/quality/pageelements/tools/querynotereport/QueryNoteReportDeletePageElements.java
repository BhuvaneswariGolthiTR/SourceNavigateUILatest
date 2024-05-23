package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportDeletePageElements
{
	public static final String QUERY_NOTE_DELETE_PAGE_TITLE = "queryNoteWeb/appPages/popups/generic";
	public static final String QUERY_NOTE_DELETE_MODAL_TITLE = "Delete Query";
	
	@FindBy(how = How.ID, using = "pageForm:deleteBtn")
	public static WebElement deleteButton;

	@FindBy(how = How.ID, using = "pageForm:okBtn-button")
	public static WebElement okButton;
}
