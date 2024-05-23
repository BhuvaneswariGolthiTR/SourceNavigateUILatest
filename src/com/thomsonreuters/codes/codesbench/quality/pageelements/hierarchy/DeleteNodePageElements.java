package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteNodePageElements
{
	public static final String DELETE_PAGE_TITLE = "Delete";

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @value='Cancel' or @id='inputAsForm:cancelButton'] | //button[@id='cancelButton-button' or @id='b_cancel']")
	public static WebElement cancelButton;

	@FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
	public static WebElement quickLoadButton;

	@FindBy(how = How.ID, using = "pageForm:ok")
	public static WebElement submitButton;
}
