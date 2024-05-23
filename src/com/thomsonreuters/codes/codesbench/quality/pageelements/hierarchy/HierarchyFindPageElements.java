package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyFindPageElements
{
    public static final String FIND_PAGE_TITLE = "Find";
    public static final String ALL_UUIDS_IN_GRID = "//td[contains(@class,'NodeUUID')]/div/a";

    @FindBy(how = How.XPATH, using = "//div[@id='multipleFindResultsGridDiv']//tbody//td[contains(@class,'NodeUUID')]//a")
    public static WebElement firstNodeUuidLink;

    @FindBy(how = How.ID, using = "pageForm:text")
    public static WebElement searchResultSummaryInFindPage;
}