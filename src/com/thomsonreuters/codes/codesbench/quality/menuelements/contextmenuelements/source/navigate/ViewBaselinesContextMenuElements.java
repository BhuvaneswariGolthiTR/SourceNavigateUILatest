package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewBaselinesContextMenuElements
{
    public static final String CONTEXT_MENU = "//div[@id='contextMenu']";

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//li/a[text()='Restore Baseline']")
    public static WebElement restoreBaseline;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//li/a[text()='View Baseline']")
    public static WebElement viewBaseline;
}
