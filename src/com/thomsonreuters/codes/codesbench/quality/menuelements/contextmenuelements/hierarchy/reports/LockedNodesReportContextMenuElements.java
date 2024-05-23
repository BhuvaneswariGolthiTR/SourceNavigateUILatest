package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LockedNodesReportContextMenuElements
{
    public static final String LOCKED_NODES_REPORT_CONTEXT_MENU_ITEMS = "//div[@id='lockedNodesReportContextMenu']//div//li/a";

    @FindBy(how = How.XPATH, using = "//div[@id='lockedNodesReportContextMenu']//div//li/a[text()='Unlock']")
    public static WebElement unlock;

    @FindBy(how = How.XPATH, using = "//div[@id='lockedNodesReportContextMenu']//div//li/a[text()='Force Unlock']")
    public static WebElement forceUnlock;

    @FindBy(how = How.XPATH, using = "//div[@id='lockedNodesReportContextMenu']//div//li/a[text()='View in Hierarchy']")
    public static WebElement viewInHierarchy;
}
