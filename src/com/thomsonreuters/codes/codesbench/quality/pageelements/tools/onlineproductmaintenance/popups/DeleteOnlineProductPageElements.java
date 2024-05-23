package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteOnlineProductPageElements
{

    public static final String PAGE_TITLE = "Delete Online Product";

    public static final String ASSIGNMENTS_LIST = "//select[@id='pageForm:assigned_list']//option";

    public static final String DELETE_BUTTON = "//input[@id='pageForm:deleteScriptButton']";

    public static final String MESSAGE = "//span[@id='pageForm:message']";

    public static final String ASSIGNED_LIST = "//select[@id='pageForm:assigned_list']/option";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:deleteScriptButton']")
    public static WebElement delete;

}
