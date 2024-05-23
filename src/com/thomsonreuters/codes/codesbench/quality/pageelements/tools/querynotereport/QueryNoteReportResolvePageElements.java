package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportResolvePageElements
{
	public static final String QUERY_NOTE_RESOLVE_MODAL_TITLE = "Resolve Query Notes";
	public static final String QUERY_NOTE_RESOLVE_PAGE_TITLE = "queryNoteWeb/appPages/popups/generic";

	@FindBy(how = How.ID, using = "pageForm:queryNoteStatusUserSelected")
	public static WebElement queryNoteStatusSelect;

	@FindBy(how = How.ID, using = "pageForm:queryNoteResolvedCommentRadio0:0")
	public static WebElement queryNoteResolutionCommentFirstOption;
	
	@FindBy(how = How.ID, using = "pageForm:queryNoteResolvedCommentUserSelectedMenu")
	public static WebElement queryNoteResolutionCommentFirstOptionSelect;

	@FindBy(how = How.ID, using = "pageForm:saveBtn")
	public static WebElement saveButton;

	@FindBy(how = How.ID, using = "pageForm:saveBtn-button")
	public static WebElement okButton;
}
