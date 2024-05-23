package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UpdateMetadataMultiplePageElements {
    //Note, this page happens to share the same name as the Update Metadata page, so it shares that element and there is no page title listed in this class
    public static final String EFFECTIVE_START_DATE_ENTRY_FIELD = "//td/div[@id='pageForm:effStartDate']";


    @FindBy(how = How.ID, using = "pageForm:ok")
    public static WebElement updateButton;

    @FindBy(how = How.ID, using = "pageForm:cancel")
    public static WebElement cancelButton;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadButton;

    @FindBy(how = How.ID, using = "pageForm:effectiveStartDateCheck")
    public static WebElement effectiveStartDateCheckBox;

    @FindBy(how = How.ID, using = "pageForm:effStartDate")
    public static WebElement effectiveStartDateEntryField;

    @FindBy(how = How.ID, using = "pageForm:codeNameCheck")
    public static WebElement codeNameCheckBox;

    @FindBy(how = How.ID, using = "pageForm:codeName")
    public static WebElement codeNameDropdownMenu;

    @FindBy(how = How.ID, using = "pageForm:versionedCheck")
    public static WebElement versionedCheckBox;

    @FindBy(how = How.ID, using = "pageForm:isVersioned")
    public static WebElement versionedDropdownMenu;

    @FindBy(how = How.ID, using = "pageForm:codeLevelCheck")
    public static WebElement codeLevelCheckBox;

    @FindBy(how = How.ID, using = "pageForm:codeLevel")
    public static WebElement codeLevelDropdownMenu;
}
