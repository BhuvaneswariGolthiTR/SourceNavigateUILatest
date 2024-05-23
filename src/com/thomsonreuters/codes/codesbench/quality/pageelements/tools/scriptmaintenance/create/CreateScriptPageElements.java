package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateScriptPageElements
{
    private static final String PAGE_TITLE = "Create Script";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-script-create-dialog";
    public static final String PAGE_XPATH = String.format("%s/h1[contains(@class,'mat-dialog-title') and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    @FindBy(how = How.XPATH, using = "//input[@formControlName='script']")
    public static WebElement scriptName;

    @FindBy(how = How.XPATH, using = "//input[@formControlName='description']")
    public static WebElement versionDescription;

    @FindBy(how = How.XPATH, using = "//input[@formControlName='pubtag']")
    public static WebElement pubTag;

    @FindBy(how = How.XPATH, using = "//input[@formControlName='filterText']")
    public static WebElement contentSetFilter;

    public static final String AVAILABLE_CONTENT_SET_SPECIFIC_CHECKBOX = "//cdk-virtual-scroll-viewport[1]//mat-list-option//div[@class='mat-list-text' and contains(text(),'%s')]/../mat-pseudo-checkbox";
    public static final String SELECTED_CONTENT_SET = "//div[@class='collector-column']/span[contains(text(),'Selected')]/../cdk-virtual-scroll-viewport//mat-list-option//div[@class='mat-list-text' and contains(text(),'%s')]";

    public static final String MOVE_RIGHT_ARROW = "//div[@class='collector-column collector-buttons']/button[1]";

    public static final String SAVE_BUTTON = "//div[@class='mat-dialog-actions']//button[span[contains(text(),'Save')]]";

    public static final String CANCEL_BUTTON = "//div[@class='mat-dialog-actions']//button[span[contains(text(),'Cancel')]]";

    public static final String SCRIPT_NAME_IS_REQUIRED_MESSAGE = "//div[contains(@class,'Form-hintText') and contains(text(),'Script Name is required.')]";

    public static final String VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE = "//div[contains(@class,'Form-hintText') and contains(text(),'Version Description is required.')]";

    public static final String PUBTAG_IS_REQUIRED_MESSAGE = "//div[contains(@class,'Form-hintText') and contains(text(),'PubTag is required.')]";
    public static final String PUBTAG_IS_LONGER_THAN_6_CHARACTERS_MESSAGE = "//div[contains(@class,'Form-hintText') and contains(text(),'Maximal length of PubTag is 6 characters.')]";
    public static final String PUBTAG_CANNOT_START_WITH_NUMERIC_SPECIAL_SPACES_MESSAGE = "//div[contains(@class,'Form-hintText') and contains(text(),'Cannot create PubTag that starts with a numeric value and contains special symbols and spaces.')]";
}
