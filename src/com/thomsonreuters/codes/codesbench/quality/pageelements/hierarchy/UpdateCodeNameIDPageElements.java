package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UpdateCodeNameIDPageElements
{
    public static final String UPDATE_CODE_NAME_ID_PAGE_TITLE = "Update CodeName/ID";
    public static final String EXISTING_CODE_NAME_ID_DROPDOWN_MENU = "//td//select";
    public static final String CURRENT_CODE_NAME_ID = "//select//option[@selected]";

    @FindBy(how = How.XPATH, using = "//input[@type='text' and @maxlength= '100']")
    public static WebElement createNewCodeNameIDEntryField;

    @FindBy(how = How.XPATH, using = "//div//input[@value= 'Cancel']")
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = "//div//input[@value= 'Submit']")
    public static WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//label[contains(text(),'Create New CodeName/ID')]/preceding-sibling::input[@type='radio']")
    public static WebElement createNewCodeNameIDRadioButton;

    @FindBy(how = How.XPATH, using = "//label[contains(text(),'Change Existing CodeName/ID')]/preceding-sibling::input[@type='radio']")
    public static WebElement changeExistingCodeNameIDRadioButton;

    @FindBy(how = How.XPATH, using = "//input[@type='checkbox']/../following-sibling::div[contains(text(), 'Assign CodeName/ID to Metadata')]")
    public static WebElement assignCodeNameIDToMetadataCheckBox;
}