package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsertFootnotePageElements 
{
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:saveButton' or @id='pageForm:performSaveButton' or @id='pageForm:saveBtn' or @value='Save' or @id='inputAsForm:saveButton']")
	public static WebElement saveButton;
	
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:footnoteRef']")
	public static WebElement footnoteReference;
	
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:newFootnoteFlag']")
	public static WebElement newFootnoteCheckbox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:newFootnoteRefFlag']")
	public static WebElement newFootnoteReferenceCheckbox;

	public static final String REFERENCE_TYPE = "//select[@id='pageForm:footnoteRefType']/option[text()='%s']";
    public static final String FOOTNOTE_TYPE = "//select[@id='pageForm:footnoteType']/option[text()='%s']";

}

