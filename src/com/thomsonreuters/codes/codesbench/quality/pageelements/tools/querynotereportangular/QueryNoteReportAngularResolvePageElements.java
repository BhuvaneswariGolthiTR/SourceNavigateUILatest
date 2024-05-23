package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportAngularResolvePageElements extends QueryNoteReportAngularEditDeleteResolveCommonPageElements
{
	public static final String QUERY_NOTE_RESOLVE_MODAL_HEADER = "Resolve Query Note";
	public static final String QUERY_NOTE_RESOLVE_PAGE_XPATH = "//app-resolve-query-note";

	/**
	 *  Buttons/webelements
	 */
	@FindBy(how = How.XPATH, using = MODAL_BODY + "//select[@id = 'query_note_status']")
	public static WebElement queryNoteStatusSelect;

	@FindBy(how = How.ID, using = "query_note_resolution_comment")
	public static WebElement queryNoteResolutionCommentRadioButton;

	@FindBy(how = How.XPATH, using = MODAL_BODY + "//select[@id = 'query_note_resolution_comment']")
	public static WebElement queryNoteResolutionCommentSelect;
}
