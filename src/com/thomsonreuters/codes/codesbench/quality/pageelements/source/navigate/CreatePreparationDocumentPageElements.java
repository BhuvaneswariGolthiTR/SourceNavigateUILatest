package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreatePreparationDocumentPageElements
{
    public static final String createPreparationDocumentTitle = "Create Preparation Document";

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement submitButton;
}
