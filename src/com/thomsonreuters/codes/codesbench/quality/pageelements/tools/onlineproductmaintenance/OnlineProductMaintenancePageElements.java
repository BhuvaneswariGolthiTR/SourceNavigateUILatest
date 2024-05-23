package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class OnlineProductMaintenancePageElements
{
    public static final String PAGE_TITLE = "Online Product Maintenance";
    public static final String CONTENT_SET = "//td[label[contains(text(), '%s')]]/input";

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:chooseProduct']")
    public static WebElement selectProductDropdown;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:showDeletedFlag']")
    public static WebElement showDeletedCheckbox;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:refreshButton']")
    public static WebElement refreshButton;
}
