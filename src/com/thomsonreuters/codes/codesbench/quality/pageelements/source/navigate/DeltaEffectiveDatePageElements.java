package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeltaEffectiveDatePageElements
{
    @FindBy(how = How.ID,using = "pageForm:effectiveDate")
    public static WebElement effectiveDateTextBox;

    @FindBy(how = How.ID,using = "pageForm:saveButton")
    public static WebElement saveButton;
}