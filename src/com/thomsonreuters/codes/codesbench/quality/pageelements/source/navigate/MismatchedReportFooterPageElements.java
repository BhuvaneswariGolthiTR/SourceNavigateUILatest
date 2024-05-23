package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MismatchedReportFooterPageElements
{
    @FindBy(how = How.XPATH, using = "//span[id='yui-pg0-1-page-report19']/a[text()='Refresh']")
    public static WebElement refreshButton;
}
