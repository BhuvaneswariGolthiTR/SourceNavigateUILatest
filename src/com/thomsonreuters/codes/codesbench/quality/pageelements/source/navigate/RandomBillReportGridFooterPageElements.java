package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RandomBillReportGridFooterPageElements
{
    @FindBy(how = How.XPATH, using = "//div[@class='yui-dt-paginator grid-pg-container']//span[contains(text(), 'Results')]")
    public static WebElement renditionResult;

    public static final String REPORT_CLEAR_ALL_FILTERS = "//div[@id='yui-dt0-paginator1']//a[text()='Clear All Filters']";
    public static final String REPORT_REFRESH = "//div[@id='yui-dt0-paginator1']//a[text()='Refresh']";
    public static final String REPORT_CLEAR_SORT = "//div[@id='yui-dt0-paginator1']//a[text()='Clear Sort']";
    public static final String REPORT_CLEAR_SELECTION = "//div[@id='yui-dt0-paginator1']//a[text()='Clear Selection']";
}
