package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateScriptVersionPageElements
{
    private static final String PAGE_TITLE = "Create Script Version";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-script-create-version-dialog";
    public static final String PAGE_XPATH = String.format("%s/h1[contains(@class,'mat-dialog-title') and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='script']")
    public static WebElement scriptName;

    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='description']")
    public static WebElement versionDescription;

    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='pubtag']")
    public static WebElement pubTag;

    public static final String SELECTED_CONTENT_SETS = DIALOG_CONTAINER_XPATH + "//div[@class='collector-column']/span[contains(text(),'Selected')]/../cdk-virtual-scroll-viewport//mat-list-option//div[@class='mat-list-text']";

    public static final String SUBMIT_BUTTON = "//div[@class='mat-dialog-actions']//button[span[contains(text(),'Submit')]]";

    public static final String CANCEL_BUTTON = "//div[@class='mat-dialog-actions']//button[span[contains(text(),'Cancel')]]";
}
