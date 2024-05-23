package com.thomsonreuters.codes.codesbench.quality.pageelements.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RedliningComparePageElements
{
    public static final String REDLINING_COMPARE_PAGE_TITLE = "Redlining Compare";
    public static final String VOLUME = "//select[@id='pageForm:nonSelectedVolsList']/option[@value='%s']";
    public static final String UNSELECTED_DOCUMENT_PANE = "//select[@id='pageForm:nonSelectedDocsList']";
    public static final String UNSELECTED_DOCUMENT_PANE_DOCUMENT = "//select[@id='pageForm:nonSelectedDocsList']//option[@value='%s']";

    @FindBy(how = How.ID, using = "pageForm:addOneVolsButton")
    public static WebElement addOneVolumeButton;

    @FindBy(how = How.ID, using = "pageForm:confirmPartialButton")
    public static WebElement nextButton;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;

    @FindBy(how = How.ID, using = "pageForm:nonSelectedDocsList")
    public static WebElement nonSelectedDocsList;
}
