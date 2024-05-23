package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ChangeEndDateDescendantsPageElements
{
    public static final String CHANGE_END_DATE_DESCENDANTS_PAGE_TITLE = "Change End Date(descendants)";

    @FindBy(how = How.ID, using = "pageForm:changeDate")
    public static WebElement endEffectiveDateField;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadButton;

    @FindBy(how = How.ID, using = "pageForm:ok")
    public static WebElement submitButton;
}