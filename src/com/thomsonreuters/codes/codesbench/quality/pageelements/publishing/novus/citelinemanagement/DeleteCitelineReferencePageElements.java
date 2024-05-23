package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteCitelineReferencePageElements
{
    @FindBy(how = How.ID, using = "yesButton")
    public static WebElement yesButton;

    @FindBy(how = How.ID, using = "noButton")
    public static WebElement noButton;
}
