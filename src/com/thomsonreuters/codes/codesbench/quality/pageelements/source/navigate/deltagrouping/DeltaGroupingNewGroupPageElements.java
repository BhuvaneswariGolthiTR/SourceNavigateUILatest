package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeltaGroupingNewGroupPageElements
{
    @FindBy(how = How.XPATH, using = "//div[@id='newGroupDialogContainer']//input[@name='title']")
    public static WebElement newGroupTitle;

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Save')]")
    public static WebElement saveButton;
}
