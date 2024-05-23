package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EnterDateCriteriaForGridFilterPageElements
{
    public static final String POPUP_WINDOW_TITLE = "Enter Date Criteria for Grid Filter";

    @FindBy(how = How.XPATH, using = "//div[@id='dateContent']//input[@value='Date']")
    public static WebElement dateRadioButton;

    @FindBy(how = How.ID, using = "singleDate")
    public static WebElement dateTextBox;

    @FindBy(how = How.ID, using = "pageForm:submitButton")
    public static WebElement submitButton;
}
