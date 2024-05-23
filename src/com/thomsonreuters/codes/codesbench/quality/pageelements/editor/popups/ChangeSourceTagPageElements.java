package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ChangeSourceTagPageElements
{
	public static final String CHANGE_SOURCETAG_PAGE_TITLE = "Change SourceTag";

	public static final String SOURCE_TAG_DROPDOWN_ID="selectSourceTag";

	@FindBy(how = How.XPATH, using = "//select[@id='selectSourceTag']")
	public static WebElement sourceTagDropdown;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
	public static WebElement okButton;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @id='inputAsForm:cancelButton' or @value='Cancel'] | //button[@id='cancelButton-button' or @id='b_cancel']")
	public static WebElement cancelButton;
}
