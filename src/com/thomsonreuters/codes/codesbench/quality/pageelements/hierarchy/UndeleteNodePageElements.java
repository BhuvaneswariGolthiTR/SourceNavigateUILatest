package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UndeleteNodePageElements
{
	public static final String UNDELETE_PAGE_TITLE = "Undelete";

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @value='Cancel' or @id='inputAsForm:cancelButton'] | //button[@id='cancelButton-button' or @id='b_cancel']")
	public static WebElement cancelButton;

	@FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
	public static WebElement quickLoadTrackingButton;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
	public static WebElement submitButton;
}
