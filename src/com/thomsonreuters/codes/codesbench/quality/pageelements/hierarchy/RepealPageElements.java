package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RepealPageElements
{
    public static final String REPEAL_PAGE_TITLE = "Repeal";

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadTrackingButton;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement okButton;

    @FindBy(how = How.XPATH, using = "//input[@name='pageForm:effStartDate']")
    public static WebElement effectiveStartDateTextBox;

    @FindBy(how = How.XPATH, using = "//input[contains(@value, 'Share')]")
    public static WebElement shareNodsForwardRadioButton;

    @FindBy(how = How.XPATH, using = "//input[contains(@value, 'true')]")
    public static WebElement processAdRepleasedRangeRadioButton;

    @FindBy(how = How.XPATH, using = "//input[contains(@value, 'PTR_REAL_TIME')]")
    public static WebElement runPubTagRefreshNowRadioButton;

}
