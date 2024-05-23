package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AutoMergeReportPopupElementsAngular {

    public static final String PAGE_TITLE = "//div/h5[contains(text(), 'Auto Merge Report')]";

    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Download latest report')]")
    public static WebElement downloadLatestReport;

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Subscribe')]")
    public static WebElement subscribeButton;

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Unsubscribe')]")
    public static WebElement unsubscribeButton;

    @FindBy(how = How.CLASS_NAME, using = "close-btn")
    public static WebElement close;

}
