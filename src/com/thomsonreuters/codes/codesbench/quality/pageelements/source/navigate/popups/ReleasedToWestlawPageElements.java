package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReleasedToWestlawPageElements
{
    public static final String RELEASED_TO_WESTLAW_PAGE_TITLE = "Released to Westlaw";

    @FindBy(how = How.ID, using = "pageForm:releasedToWestlawDate")
    public static WebElement releasedToWestlawDate;

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement saveButton;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;
}
