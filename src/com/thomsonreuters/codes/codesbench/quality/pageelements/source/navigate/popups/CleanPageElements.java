package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CleanPageElements
{
    public static final String CLEAN_PAGE_TITLE = "Clean";

    @FindBy(how = How.ID, using = "pageForm:markCleanDate")
    public static WebElement markAsCleanDate;

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement saveButton;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;
}
