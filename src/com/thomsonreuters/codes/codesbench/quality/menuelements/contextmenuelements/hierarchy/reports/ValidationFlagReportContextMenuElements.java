package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ValidationFlagReportContextMenuElements
{
    @FindBy(how = How.XPATH, using = "//div[@id='validationFlagsGridContextMenu']//a[text()='Clear Warning Flag']")
    public static WebElement clearWarningFlag;
}
