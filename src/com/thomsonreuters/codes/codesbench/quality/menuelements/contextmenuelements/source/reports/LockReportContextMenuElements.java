package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LockReportContextMenuElements
{
    @FindBy(how = How.XPATH, using = "//a[text()='View Content']")
    public static WebElement viewContent;

    @FindBy(how = How.XPATH, using = "//a[text()='Unlock']")
    public static WebElement unlock;

    @FindBy(how = How.XPATH, using = "//a[text()='Force Unlock']")
    public static WebElement forceUnlock;

    @FindBy(how = How.XPATH, using = "//a[text()='Transfer Unlock']")
    public static WebElement transferUnlock;

    @FindBy(how = How.XPATH, using = "//a[text()='Transfer Lock']")
    public static WebElement transferLock;
}
