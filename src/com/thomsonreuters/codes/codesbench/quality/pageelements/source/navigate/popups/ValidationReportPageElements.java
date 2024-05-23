package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ValidationReportPageElements
{
	public static final String VALIDATION_REPORT_PAGE_TITLE = "Validation Report";
    public static final String FIRST_RENDITION_VALIDATE = "//div[@class='yui-dt-bd']//tr[1]//td[3]";	//TODO: to change
    public static final String VALIDATE_COUNT_RENDITION = "//div[@class='yui-dt-bd']//tbody[@class='yui-dt-data']//tr";   //TODO: to change

    @FindBy(how = How.XPATH, using = "//a[text() = 'Close']")
    public static WebElement closeButton;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Clear Warning Flags')]")
    public static WebElement clearWarningFlags;
}
