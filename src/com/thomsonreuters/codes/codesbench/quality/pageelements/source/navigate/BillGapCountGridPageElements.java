package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BillGapCountGridPageElements
{
    public static String RECORDS = "//tbody[@class='yui-dt-data']/tr";

    public static String NO_RECORDS_FOUND_MESSAGE = "//tbody[@class='yui-dt-message']//div[contains(text(),'No records found.')]";

    @FindBy(how = How.XPATH, using = "//input[@value='Search']")
    public static WebElement SEARCH_BUTTON;

    @FindBy(how = How.ID, using = "docTypeFilter")
    public static WebElement DOC_TYPE_FILTER;

    public static String GRID_FILTER_X_XPATH = "//tbody[@class='yui-dt-data']//tr//td[contains(@headers,'%s')]";

    @FindBy(how = How.ID, using = "yui-dt0-th-docType-liner")
    public static WebElement DOC_TYPE_SORT;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt0-paginator1']//a[contains(text(),'Clear All Filters')]")
    public static WebElement CLEAR_ALL_FILTERS;
}
