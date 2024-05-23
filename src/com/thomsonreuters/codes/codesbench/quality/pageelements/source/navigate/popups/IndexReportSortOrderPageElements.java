package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class IndexReportSortOrderPageElements
{
    public static final String INDEX_REPORT_SORT_ORDER_PAGE_TITLE = "Index Report Sort Order";
    public static final String INDEX_REPORT_SORT_ORDER_CHECKBOX = "//table[@id='pageForm:sortOrderTypeId']/tbody/tr/td/input[@type='radio' and @name='pageForm:sortOrderTypeId' and @value='%s']";

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement okButton;
}
