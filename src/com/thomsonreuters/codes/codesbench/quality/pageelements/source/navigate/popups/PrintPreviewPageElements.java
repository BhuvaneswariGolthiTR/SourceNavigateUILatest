package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PrintPreviewPageElements
{
    public static final String TABULAR_PRINT_PREVIEW_PAGE_TITLE = "Tabular Print Preview";
    public static final String PRINT_PREVIEW_PAGE_TITLE = "Print Preview";

    @FindBy(how = How.XPATH, using = "//span[@id='pageForm:outputMsg']/a[contains(@href, 'workflowId')]")
    public static WebElement workflowIDLink;

    @FindBy(how = How.ID, using = "pageForm:okButton")
    public static WebElement okButton;
}
