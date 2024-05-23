package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.copy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CopyScriptForVersionPageElements
{
    private static final String PAGE_TITLE = "Copy Script for Version";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-script-copy-for-version-dialog";
    public static final String PAGE_XPATH = String.format("%s/h1[@class='mat-dialog-title' and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='description']")
    public static WebElement versionDescription;

    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='filterText']")
    public static WebElement contentSetFilter;

    public static final String VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE = DIALOG_CONTAINER_XPATH + "//div[contains(@class,'Form-hintText') and contains(text(),'Version Description is required.')]";

    public static final String AVAILABLE_CONTENT_SET_SPECIFIC_CHECKBOX = DIALOG_CONTAINER_XPATH + "//cdk-virtual-scroll-viewport[1]//mat-list-option//div[@class='mat-list-text' and contains(text(),'%s')]/../mat-pseudo-checkbox";
    public static final String SELECTED_CONTENT_SET = DIALOG_CONTAINER_XPATH + "//div[@class='collector-column']/span[contains(text(),'Selected')]/../cdk-virtual-scroll-viewport//mat-list-option//div[@class='mat-list-text' and contains(text(),'%s')]";

    public static final String MOVE_RIGHT_ARROW = DIALOG_CONTAINER_XPATH + "//div[@class='collector-column collector-buttons']/button[1]";

    public static final String SELECTED_CONTENT_SETS = DIALOG_CONTAINER_XPATH + "//div[@class='collector-column']/span[contains(text(),'Selected')]/../cdk-virtual-scroll-viewport//mat-list-option//div[@class='mat-list-text']";

    public static final String SUBMIT_BUTTON = DIALOG_CONTAINER_XPATH + "//div[@class='mat-dialog-actions']//button[span[contains(text(),'Submit')]]";

    public static final String CANCEL_BUTTON = DIALOG_CONTAINER_XPATH + "//div[@class='mat-dialog-actions']//button[span[contains(text(),'Cancel')]]";
}
