package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ChangeVolumeNumberDescendantsPageElements
{
    public static final String CHANGE_VOLUME_NUMBER_DESCENDANTS_PAGE_TITLE = "Change Volume Number(descendants)";

    @FindBy(how = How.ID, using = "pageForm:volumeNumber")
    public static WebElement volumeNumberDropdownMenu;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadTrackingButton;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement okButton;
}