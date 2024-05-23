package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StocknotePropertiesPageElements
{
	public static final String STOCKNOTE_PROPERTIES_PAGE_TITLE = "Stocknote Properties";

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @id='inputAsForm:cancelButton' or @value='Cancel'] | //button[@id='cancelButton-button' or @id='b_cancel']")
	public static WebElement cancelButton;
	
	@FindBy(how = How.XPATH, using = "//input[contains(@id, 'name')]")
	public static WebElement nameTextBox;

	@FindBy(how = How.ID, using = "pageForm:onContextMenu")
	public static WebElement onContextMenuCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:stateRuleDoc")
	public static WebElement stateRulesCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:localRuleDoc")
	public static WebElement localRulesCheckbox;

	public static final String USER_ASSIGNED_CATEGORY_DROPDOWN_ID = "pageForm:userAssignedCategory";

	@FindBy(how = How.ID, using = USER_ASSIGNED_CATEGORY_DROPDOWN_ID)
	public static WebElement userAssignedCategoryDropdown;
	
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:saveButton' or @id='pageForm:performSaveButton' or @id='pageForm:saveBtn' or @value='Save' or @id='inputAsForm:saveButton']")
	public static WebElement saveButton;
	
	@FindBy(how = How.ID, using = "pageForm:isPinned")
	public static WebElement pinToTopCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:allTarget")
	public static WebElement targetDocumentsSelectAllCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:allSource")
	public static WebElement sourceDocumentsSelectAllCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:content")
	public static WebElement contentTextArea;
	
	@FindBy(how = How.ID, using = "pageForm:validateButton")
	public static WebElement validateButton;

	@FindBy(how = How.ID, using = "pageForm:infoMessage")
	public static WebElement validateMessage;
}
