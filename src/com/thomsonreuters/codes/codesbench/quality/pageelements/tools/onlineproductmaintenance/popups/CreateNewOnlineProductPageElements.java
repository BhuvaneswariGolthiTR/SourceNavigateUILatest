package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateNewOnlineProductPageElements
{

    public static final String PAGE_TITLE = "Create New Online Product";

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:productOption']")
    public static WebElement productTypeDropdown;

    @FindBy(how = How.XPATH, using = "//input[@id = 'pageForm:pubtag']")
    public static WebElement viewTagTextField;

    @FindBy(how = How.XPATH, using = "//input[@id = 'pageForm:longName']")
    public static WebElement productNameTextField;

    @FindBy(how = How.XPATH, using = "//input[@id = 'pageForm:newScriptShortName']")
    public static WebElement productShortNameTextField;

    @FindBy(how = How.XPATH, using = "//input[@id = 'pageForm:addContentSetButton']")
    public static WebElement addContentSet;

    @FindBy(how = How.XPATH, using = "//input[@id = 'pageForm:removeContentSetButton']")
    public static WebElement removeContentSet;

    public static final String SELECTED_CONTENT_SET_LIST = "//select[@id='pageForm:selectedContentSets']/option";

    public static final String NON_SELECTED_CONTENT_SET_LIST = "//select[@id='pageForm:nonSelectedContentSets']/option";

    public static final String NON_SELECTED_CONTENT_SET_BY_NAME = NON_SELECTED_CONTENT_SET_LIST +"[contains(text(), '%s')]";

}
