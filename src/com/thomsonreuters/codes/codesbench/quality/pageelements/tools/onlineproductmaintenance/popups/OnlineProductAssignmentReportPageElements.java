package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class OnlineProductAssignmentReportPageElements
{
    public static final String PAGE_TITLE = "Online Product Assignment Report";

    public static final String SELECTED_PRODUCT = "//span[contains(text(),'Selected Product')]";

    @FindBy(how = How.XPATH, using = "//td[label[contains(text(),'Wip View')]]/input")
    public static WebElement wipView;

    @FindBy(how = How.XPATH, using = "//td[label[contains(text(),'Pub View')]]/input")
    public static WebElement pubView;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:effectiveDate']")
    public static WebElement effectiveDate;


}
