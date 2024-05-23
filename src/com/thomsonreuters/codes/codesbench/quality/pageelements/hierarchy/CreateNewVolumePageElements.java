package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateNewVolumePageElements
{
    public static final String PAGE_TITLE = "Create New Volume";

    @FindBy(how = How.ID, using = "pageForm:volumeNumber")
    public static WebElement volumeNumber;

    @FindBy(how = How.ID, using = "pageForm:volumeTitle")
    public static WebElement volumeTitle;

    @FindBy(how = How.ID, using = "pageForm:submit-button")
    public static WebElement submit;

    @FindBy(how = How.ID, using = "pageForm:cancel")
    public static WebElement cancel;
}
