package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CopyPageElements
{
    public static final String COPY_PAGE_TITLE = "Copy";
    public static final String VOLUMES_SELECTION_BOX_XPATH = "//select[@id='pageForm:volumeNumber']/option[@value='%s']";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement okButton;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadTrackingButton;

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:volumeNumber']/option[@selected='selected']")
    public static WebElement volumeSelectionBox;

    @FindBy(how = How.XPATH, using = "//table[@id='pageForm:j_idt37']//input[contains(@value,'Copy')]")
    public static WebElement createCopyOfNODsRadioButton;
}