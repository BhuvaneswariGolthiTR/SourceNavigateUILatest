package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateQueryNotePageElements
{
	
	public static final String QUERY_NOTE_PAGE_TITLE = "Create Query Note";
	
	@FindBy(how = How.ID, using = "pageForm:queryNoteActionDate")
	public static WebElement actionTextbox;

	@FindBy(how = How.ID, using = "pageForm:queryNoteText")
	public static WebElement textField;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:saveButton' or @id='pageForm:performSaveButton' or @id='pageForm:saveBtn' or @value='Save' or @id='inputAsForm:saveButton']")
	public static WebElement saveButton;

	@FindBy(how = How.ID, using = "pageForm:queryNoteType")
	public static WebElement typeSelect;
	public static final String typeSelect_ID= "pageForm:queryNoteType";

	@FindBy(how = How.XPATH, using = "//form[@id='headerForm']//input[@name='headerForm:j_idt8' or @name='headerForm:j_idt7']")
	public static WebElement refreshButton;
}
