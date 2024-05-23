package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyDateSearchMultipleDocumentsPageElements
{
    //Note: This page has no official title, it is empty and we use the switchToLastWindow method from the Base Page to get to it
    public static final String WORKFLOW_ID_LINK = "//a[contains(@href, workflowId)]";

    @FindBy(how = How.ID, using = "pageForm:reportName")
    public static WebElement fileReportNameTextEntryField;

    @FindBy(how = How.XPATH, using = "//input[@value='startDate']")
    public static WebElement startDateSingleEntryRadioButton;

    @FindBy(how = How.ID, using = "pageForm:startDateSingleText")
    public static WebElement startDateSingleEntryDateEntryField;

    @FindBy(how = How.XPATH, using = "//input[@value='endDate']")
    public static WebElement endDateSingleEntryRadioButton;

    @FindBy(how = How.ID, using = "pageForm:endDateSingleText")
    public static WebElement endDateSingleEntryDateEntryField;

    @FindBy(how = How.XPATH, using = "//input[@value='dateRangeStart']")
    public static WebElement dateRangeStartDateRadioButton;

    @FindBy(how = How.ID, using = "pageForm:dateRangeStartFromText")
    public static WebElement dateRangeStartDateFromDateEntryField;

    @FindBy(how = How.ID, using = "pageForm:dateRangeStartToText")
    public static WebElement dateRangeStartDateToDateEntryField;

    @FindBy(how = How.XPATH, using = "//input[@value='dateRangeEnd']")
    public static WebElement dateRangeEndDateRadioButton;

    @FindBy(how = How.ID, using = "pageForm:dateRangeEndFromText")
    public static WebElement dateRangeEndDateFromDateEntryField;

    @FindBy(how = How.ID, using = "pageForm:dateRangeEndToText")
    public static WebElement dateRangeEndDateToDateEntryField;

    @FindBy(how = How.XPATH, using = "//input[@value='modifiedByDate']")
    public static WebElement modifiedByDateRadioButton;

    @FindBy(how = How.ID, using = "pageForm:modifiedByDateFromText")
    public static WebElement modifiedByDateFromDateEntryField;

    @FindBy(how = How.ID, using = "pageForm:modifiedByDateToText")
    public static WebElement modifiedByDateToDateEntryField;

    @FindBy(how = How.XPATH, using = "//input[@value='modifiedByUser']")
    public static WebElement modifiedByUserRadioButton;

    @FindBy(how = How.ID, using = "pageForm:modifiedByUser")
    public static WebElement modifiedByUserNameEntryField;

    @FindBy(how = How.ID, using = "submitButton-button")
    public static WebElement submitButton;

    @FindBy(how = How.ID, using = "cancelButton-button")
    public static WebElement cancelButton;

    @FindBy(how = How.ID, using = "pageForm:multipleJurisdictionsCheckbox")
    public static WebElement multipleJurisdictionsCheckbox;

    @FindBy(how = How.ID, using = "pageForm:sourceAvailableContentSets")
    public static WebElement sourceAvailableContentSetsMenu;
}
