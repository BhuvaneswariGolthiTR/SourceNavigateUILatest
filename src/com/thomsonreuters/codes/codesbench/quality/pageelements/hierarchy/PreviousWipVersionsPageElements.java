package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PreviousWipVersionsPageElements
{
    public static final String PREVIOUS_WIP_VERSIONS_PAGE_TITLE = "Previous Wip Versions";
    public static final String MOST_RECENT_WIP_VERSION_XPATH = "//tbody[@class='yui-dt-data']/tr[1]";
    public static final String SELECTED_WIP_VERSION_XPATH = "//tbody[@class='yui-dt-data']//tr[contains(@class, 'selected')]";
    public static final String SELECTED_WIP_VERSION_WIP_VERSION_NUM_XPATH = SELECTED_WIP_VERSION_XPATH + "/td[contains(@class, 'WIPVersion')]/div";
    public static final String SELECTED_WIP_VERSION_CREATED_BY_XPATH = SELECTED_WIP_VERSION_XPATH + "/td[contains(@class, 'CreatedBy')]";
    public static final String SELECTED_WIP_VERSION_CREATED_DATE_XPATH = SELECTED_WIP_VERSION_XPATH + "/td[contains(@class, 'CreatedDate')]";
    public static final String SELECTED_WIP_VERSION_LAW_TRACKING_XPATH = SELECTED_WIP_VERSION_XPATH + "/td[contains(@class, 'LawTracking')]";
    public static final String WIP_VERSION_BY_INDEX_XPATH =  "//tbody[@class='yui-dt-data']/tr[%s]";
    public static final String LAW_TRACKING_STATUS_BY_INDEX_XPATH =  "//tbody[@class='yui-dt-data']/tr[%s]/td[5]";

    @FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[contains(@class,'yui-dt-last')]")
    public static WebElement originalVersion;

    @FindBy(how = How.ID, using = "pageForm:cancel")
    public static WebElement closeButton;

    @FindBy(how = How.ID, using = "wait")
    public static WebElement proccessingPleaseWaitDialoge;
}
