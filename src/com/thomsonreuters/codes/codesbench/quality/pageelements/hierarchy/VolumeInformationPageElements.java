package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class VolumeInformationPageElements
{
    public static final String VOLUME_INFORMATION_PAGE_TITLE = "Volume Information";

    @FindBy(how = How.ID, using = "pageForm:volumeTitle")
    public static WebElement volumeTitle;

    @FindBy(how = How.ID, using = "pageForm:volumeNumber")
    public static WebElement volumeNumberDropdown;

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:volumeNumber']/option[@selected='selected']")
    public static WebElement selectedVolumeNumberDropdownOption;

    @FindBy(how = How.ID, using = "pageForm:editBovContentDS")
    public static WebElement editContentDynamicScrollingUnderBov;

    @FindBy(how = How.ID, using = "pageForm:editEovContentDS")
    public static WebElement editContentDynamicScrollingUnderEov;

    @FindBy(how = How.ID, using = "pageForm:submit")
    public static WebElement submitButton;
}
