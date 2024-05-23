package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.delete;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteScriptConfirmationPageElements
{
    private static final String PAGE_TITLE = "Confirmation";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-confirm-dialog";
    public static final String PAGE_XPATH = String.format("%s/h1[@class='mat-dialog-title' and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    @FindBy(how = How.XPATH, using = "//div[@class='mat-dialog-content']/p")
    public static WebElement confirmationQuestion;

    @FindBy(how = How.XPATH, using = "//button[span[contains(text(),'Cancel')]]")
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = "//button[span[contains(text(),'Submit')]]")
    public static WebElement submitButton;
}
