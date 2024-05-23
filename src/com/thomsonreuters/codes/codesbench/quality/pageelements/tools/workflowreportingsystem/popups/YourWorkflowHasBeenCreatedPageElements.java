package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class YourWorkflowHasBeenCreatedPageElements
{
    public static final String PAGE_TITLE = "Your Workflow Has Been Created";
    public static final String LINK_IN_WINDOW = "//a[contains(text(),'magellan3.int.westgroup.com') or contains(@href, 'magellan3.int.westgroup.com')]";
  
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'magellan3.int.westgroup.com') or contains(@href, 'magellan3.int.westgroup.com')]")
    public static WebElement linkInWindow;
}
