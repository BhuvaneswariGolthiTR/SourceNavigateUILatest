package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteConfirmationPageElements
{
    public static final String DELETE_CONFIRMATION_PAGE_TITLE = "Delete Confirmation";

    @FindBy(how = How.ID, using = "pageForm:yesButton")
    public static WebElement yesButton;

    @FindBy(how = How.ID, using = "pageForm:noButton")
    public static WebElement noButton;
}