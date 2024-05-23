package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.edit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditScriptRulesConfirmationPageElements
{
    private static final String PAGE_TITLE = "Edit Script Rules";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-message-dialog";
    public static final String PAGE_XPATH = String.format("%s/h1[@class='mat-dialog-title' and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    public static String contentSetsEffectedListXpath = String.format("%s/mat-dialog-content[@class='mat-dialog-content']", DIALOG_CONTAINER_XPATH);

    @FindBy(how = How.XPATH, using = "//div[@class='mat-dialog-actions']//button[span[contains(text(),'OK')]]")
    public static WebElement okButton;
}
