package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MismatchedReportGridPageElements
{
    public static final String MISMATCHED_REPORT_NO_RECORDS_FOUND = "//div[@id='gridContainer']//div[text() = 'No records found.']";
    public static final String RENDITIONS_XPATH = "//tbody[@class='yui-dt-data']/tr";

    @FindBy(how = How.ID, using = "sourceLoadDateFilter")
    public static WebElement loadDate;
}
