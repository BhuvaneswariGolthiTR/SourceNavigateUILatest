package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditNamesAndAssignmentsPageElements
{

    public static final String PAGE_TITLE = "Edit Online Product";

    @FindBy(how = How.XPATH, using = "//span[@id='pageForm:productOptionReadOnly']")
    public static WebElement productType;

    @FindBy(how = How.XPATH, using = "//span[@id = 'pageForm:pubtagReadOnly']")
    public static WebElement viewTag;

    @FindBy(how = How.XPATH, using = "//span[@id = 'pageForm:scriptIdValue']")
    public static WebElement productId;

    @FindBy(how = How.XPATH, using = "//input[@id = 'pageForm:longName']")
    public static WebElement productNameTextField;

    @FindBy(how = How.XPATH, using = "//input[@id = 'pageForm:newScriptShortName']")
    public static WebElement productShortNameTextField;

}
