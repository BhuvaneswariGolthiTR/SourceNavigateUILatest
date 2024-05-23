package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MismatchedReportFiltersPageElements
{
    @FindBy(how = How.ID, using = "yearFilter")
    public static WebElement yearFilter;

    @FindBy(how = How.ID, using = "jurisdictionShortNameFilter")
    public static WebElement contentSetFilter;

    @FindBy(how = How.ID, using = "docTypeFilter")
    public static WebElement docTypeFilter;

    @FindBy(how = How.ID, using = "docNumberFilter")
    public static WebElement docNumberFilter;

    public static final String SOURCE_LOAD_DATE_CALENDAR_XPATH = "//button[@title='Show Calendar']//img[@alt='Show Calendar']";
}
