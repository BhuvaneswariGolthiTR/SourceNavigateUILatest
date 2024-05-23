package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SetLawTrackingPageElements
{
    public static final String PAGE_TITLE = "Set Law Tracking";

    public static final String SOURCE_LOAD_DATE_OPTION_XPATH = "//tbody[@class='yui-dt-data']//tr//td[contains(@class,'sourceLoadDate')]//div[text()='%s']";
    public static final String LAW_TRACKING_ITEM_YEAR_XPATH = "//tbody[@class='yui-dt-data']/tr//td[contains(@class, 'year')]";

    // Buttons
    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadTrackingButton;

    @FindBy(how = How.ID, using = "yearFilter")
    public static WebElement setLawTrackingYearInput;
    
    @FindBy(how = How.ID, using = "sessionFilter")
    public static WebElement setLawTrackingSessionInput;
    
    @FindBy(how = How.ID, using = "docNumberFilter")
    public static WebElement setLawTrackingDocNumberInput;
    
    @FindBy(how = How.ID, using = "classNumFilter")
    public static WebElement setLawTrackingClassNumberInput;

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Refresh")
    public static WebElement refreshButton;
}