package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportContextMenuElements
{
	@FindBy(how = How.XPATH, using = "//a[text()='Resolve Query Note']")
	public static WebElement resolveQueryNote;
	
	@FindBy(how = How.XPATH, using = "//a[text()='Delete Query Note']")
	public static WebElement deleteQueryNote;
	
	@FindBy(how = How.XPATH, using = "//a[text()='Find Document in Hierarchy']")
	public static WebElement findDocumentInHierarchy;

	@FindBy(how = How.XPATH, using = "//a[text()='Edit Document']")
	public static WebElement editDocument;

	@FindBy(how = How.XPATH, using = "//a[text()='Edit Query Note']")
	public static WebElement editQueryNote;

	@FindBy(how = How.XPATH, using = "//a[text()='Delete Query Notes']")
	public static WebElement deleteQueryNotes;
}
