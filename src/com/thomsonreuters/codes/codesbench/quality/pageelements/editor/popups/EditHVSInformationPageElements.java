package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditHVSInformationPageElements
{
    public static final String PAGE_TITLE = "Edit HVS information";

    public static final String REPORTER_VOLUME_INPUT = "//input[@id='pageForm:reporterVolume']";
    public static final String REPORTER_NUMBER_INPUT = "//input[@id='pageForm:reporterNumber']";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:serialNumber']")
    public static WebElement serialNumberInput;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:reporterVolume']")
    public static WebElement reporterVolumeInput;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:reporterNumber']")
    public static WebElement reporterNumberInput;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:reporterPage']")
    public static WebElement reporterPageInput;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:headnoteNumber']")
    public static WebElement headnoteNumberInput;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:suggestButton']")
    public static WebElement suggestCourtValuesButton;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ccdb']")
    public static WebElement ccdb;

    public static final String NOD_TYPE_ID = "pageForm:nodType";

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:nodType']")
    public static WebElement nodType;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:neutralCitation']")
    public static WebElement neutralCitation;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:saveButton' or @id='pageForm:performSaveButton' or @id='pageForm:saveBtn' or @value='Save' or @id='inputAsForm:saveButton']")
    public static WebElement saveButton;
}
