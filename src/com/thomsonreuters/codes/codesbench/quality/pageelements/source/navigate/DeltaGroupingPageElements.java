package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DeltaGroupingPageElements
{
    public static final String DELTA_GROUPING_PAGE_TITLE = "Grouping";

    @FindBy(how = How.ID, using = "groupableGridWaitDialog_c")
    public static WebElement gridWait;

    public static final String GRID_WAIT = "//*[@id='groupableGridWaitDialog_c']";
}
