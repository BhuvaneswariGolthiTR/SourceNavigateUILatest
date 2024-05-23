package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RunPubtagRefreshByVOLSPageElements
{
    public static final String PUBTAG_REFRESH_PAGE_TITLE = "Pubtag Refresh";
    public static final String SELECT_VOL_BY_GIVEN_VALUE = "//select[@id='pageForm:nonSelectedVolsList']//option[text()='%s']";
    public static final String ALL_VOLUMES_IN_SELECTED_VOLS_TABLE = "//select[@id='pageForm:selectedVolsList']//option";

    @FindBy(how = How.ID, using = "pageForm:addOneVolsButton")
    public static WebElement singleArrowToRightButton;

    @FindBy(how = How.ID, using = "pageForm:confirmPartialButton")
    public static WebElement pubtagRefreshButton;
}
