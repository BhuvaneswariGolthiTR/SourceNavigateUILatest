package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ValidationFlagsReportPopupPageElements
{
    public static final String VALIDATION_FLAGS_REPORT_POPUP_PAGE_TITLE = "validationFlagsReportPopup";
    public static final String VALIDATION_FLAGS_REPORT_POPUP_PAGE_HEADER = "//div[@id='breadcrumbArea']//span[text()='Validation Flag Report']";
    public static final String NODE_WITH_GIVEN_VALUE = "//*[text()='%s']";
    public static final String VALIDATION_ERROR_OF_GIVEN_NODE = "//td[contains(@class, 'NodeUUID')]//a[text()='%s']/../../following-sibling::td[contains(@class, 'Description')]/div";

    @FindBy(how = How.XPATH, using = "//*[text()='Clear Warning Flag']")
    public static WebElement clearWarningFlagOption;

    @FindBy(how = How.ID, using = "pageForm:close")
    public static WebElement closeButton;
}
