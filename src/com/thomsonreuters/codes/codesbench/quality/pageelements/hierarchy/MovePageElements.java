package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MovePageElements
{
    public static final String MOVE_PAGE_TITLE = "Move";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement okButton;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadButton;
}