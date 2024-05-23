package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UpdateMetadataPageElements
{
    public static final String UPDATE_METADATA_PAGE_TITLE = "Update Metadata";
    public static final String NODE_UUID_XPATH = "//span[@id='pageForm:nodeUuid']";
    public static final String CANCEL_BUTTON = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @id='inputAsForm:cancelButton' or @value='Cancel'] | //button[@id='cancelButton-button' or @id='b_cancel']";
    public static final String LIST_OF_KEYWORD_OPTIONS_XPATH = "//select[@id='pageForm:keyword']/option";
    public static final String SET_KEYWORD_DROPDOWN = "//select[@id='pageForm:keyword']/option[text()='%s']";
    public static final String SET_STATUS_DROPDOWN = "//select[@id='pageForm:status']//option[text()='%s']";
    public static final String CODENAME_CHECK_BOX = "//input[@name='pageForm:codeNameCheck']";
    public static final String LAW_TRACKING_TEXT = "//input[@name='pageForm:lawTrackingText']";

    @FindBy(how = How.XPATH, using = "//input[@name='pageForm:effEndDate']")
    public static WebElement effectiveEndDateTextBox;

    @FindBy(how = How.XPATH, using = "//input[@name='pageForm:effStartDate']")
    public static WebElement effectiveStartDateTextBox;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadButton;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement updateButton;

    @FindBy(how = How.ID, using = "pageForm:nodeUuid")
    public static WebElement nodeUuid;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @id='inputAsForm:cancelButton' or @value='Cancel'] | //button[@id='cancelButton-button' or @id='b_cancel']")
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:codeName']/option[@selected]")
    public static WebElement selectedCodeNameOption;

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:nodeType']/option[@selected]")
    public static WebElement selectedNodeTypeOption;

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:keyword']/option[@selected]")
    public static WebElement selectedKeywordOption;

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:codeLevel']/option[@selected]")
    public static WebElement selectedCodeLevelOption;

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:isVersioned']/option[@selected]")
    public static WebElement selectedVersionedOption;

    @FindBy(how = How.ID, using = "pageForm:codeName")
    public static WebElement codeNameDropdown;

    @FindBy(how = How.ID, using = "pageForm:nodeType")
    public static WebElement nodeTypeDropdown;

    @FindBy(how = How.ID, using = "pageForm:keyword")
    public static WebElement keywordDropdown;

    @FindBy(how = How.ID, using = "pageForm:codeLevel")
    public static WebElement codeLevelDropdown;

    @FindBy(how = How.ID, using = "pageForm:isVersioned")
    public static WebElement versionedDropdown;

    @FindBy(how = How.ID, using = "pageForm:status")
    public static WebElement statusDropdown;

    @FindBy(how = How.ID, using = "pageForm:codeNameCheck")
    public static WebElement codeNameCheckbox;

    @FindBy(how = How.ID, using = "pageForm:value")
    public static WebElement valueTextBox;

    @FindBy(how = How.ID, using = "pageForm:C2012SetLawTracking")
    public static WebElement setLawTrackingButton;

    @FindBy(how = How.XPATH, using = "//input[@name='pageForm:effectiveStartDateCheck']")
    public static WebElement effectiveStartDateCheckbox;

    public static final String EFFECTIVE_START_DATE_CHECK_BOX = "//input[@name='pageForm:effectiveStartDateCheck']";

    @FindBy(how = How.XPATH, using = "//input[@name='pageForm:effectiveEndDateCheck']")
    public static WebElement effectiveEndDateCheckbox;

    public static final String EFFECTIVE_END_DATE_CHECK_BOX = "//input[@name='pageForm:effectiveEndDateCheck']";

    @FindBy(how = How.XPATH, using = "//input[@name='pageForm:versionedCheck']")
    public static WebElement versionedCheckbox;

    public static final String VERSIONED_CHECK_BOX = "//input[@name='pageForm:versionedCheck']";

    @FindBy(how = How.ID, using = "pageForm:statusCheck")
    public static WebElement statusCheckbox;

    public static final String STATUS_CHECK_BOX = "//input[@id='pageForm:statusCheck']";

    @FindBy(how = How.ID, using = "pageForm:contentUuid")
    public static WebElement contentUuid;
}

