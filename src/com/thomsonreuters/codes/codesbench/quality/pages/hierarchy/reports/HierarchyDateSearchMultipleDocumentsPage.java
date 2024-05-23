package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyDateSearchMultipleDocumentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class HierarchyDateSearchMultipleDocumentsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public HierarchyDateSearchMultipleDocumentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    { PageFactory.initElements(driver, HierarchyDateSearchMultipleDocumentsPageElements.class); }

    //Here and below we create any method we want for the hierarchy date search page


    //Button clicker methods
    public void selectStartDateSingleEntryRadioButton()
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.startDateSingleEntryRadioButton);
        checkCheckbox(HierarchyDateSearchMultipleDocumentsPageElements.startDateSingleEntryRadioButton);
    }

    public void selectEndDateSingleEntryRadioButton()
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.endDateSingleEntryRadioButton);
        checkCheckbox(HierarchyDateSearchMultipleDocumentsPageElements.endDateSingleEntryRadioButton);
    }

    public void selectDateRangeStartDateRadioButton()
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeStartDateRadioButton);
        checkCheckbox(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeStartDateRadioButton);
    }

    public void selectDateRangeEndDateRadioButton()
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeEndDateRadioButton);
        checkCheckbox(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeEndDateRadioButton);
    }

    public void selectModifiedByDateRadioButton()
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.modifiedByDateRadioButton);
        checkCheckbox(HierarchyDateSearchMultipleDocumentsPageElements.modifiedByDateRadioButton);
    }

    public void selectModifiedByUserRadioButton()
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.modifiedByUserRadioButton);
        checkCheckbox(HierarchyDateSearchMultipleDocumentsPageElements.modifiedByUserRadioButton);
    }

    //Setter methods
    public void setFileReportName(String fileReportName)
    {
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.fileReportNameTextEntryField, fileReportName);
    }

    public void setStartDateSingleEntryDateEntryField(String startDate)
    {
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.startDateSingleEntryDateEntryField, startDate);
    }

    public void setEndDateSingleEntryDateEntryField(String endDate)
    {
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.endDateSingleEntryDateEntryField, endDate);
    }

    public void setDateRangeStartDateEntryFields(String fromDate, String toDate)
    {
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeStartDateFromDateEntryField, fromDate);
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeStartDateToDateEntryField, toDate);
    }

    public void setDateRangeEndDateEntryFields(String fromDate, String toDate)
    {
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeEndDateFromDateEntryField, fromDate);
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeEndDateToDateEntryField, toDate);
    }

    public void setModifiedByDateEntryFields(String fromDate, String toDate)
    {
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.modifiedByDateFromDateEntryField, fromDate);
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.modifiedByDateToDateEntryField, toDate);
    }

    public void setModifiedByUserEntryField(String userName)
    {
        sendKeysToElement(HierarchyDateSearchMultipleDocumentsPageElements.modifiedByUserNameEntryField, userName);
    }

    public void clickSubmitButton()
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.submitButton);
        click(HierarchyDateSearchMultipleDocumentsPageElements.submitButton);
    }

    public void clickMultipleJurisdictionsCheckbox()
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.multipleJurisdictionsCheckbox);
        checkCheckbox(HierarchyDateSearchMultipleDocumentsPageElements.multipleJurisdictionsCheckbox);
    }

    public void selectMultipleContentSets(String... jurisdictionList)
    {
        waitForElementToBeClickable(HierarchyDateSearchMultipleDocumentsPageElements.sourceAvailableContentSetsMenu);
        Actions builder = new Actions(driver);

        builder.keyDown(Keys.CONTROL);
        for(String contentSet : jurisdictionList)
        {
            selectDropdownOption(HierarchyDateSearchMultipleDocumentsPageElements.sourceAvailableContentSetsMenu, contentSet);
        }
        builder.keyUp(Keys.CONTROL);
        Action toPerform = builder.build();
        toPerform.perform();
    }

    public ArrayList<String> getWorkflowIDs()
    {
        List<WebElement> workflowElements = getElements(HierarchyDateSearchMultipleDocumentsPageElements.WORKFLOW_ID_LINK);
        ArrayList<String> workflowIDs = new ArrayList<>();
        for(WebElement element : workflowElements)
        {
            workflowIDs.add(getElementsText(element));
            logger.information("Got workflowID: " + getElementsText(element));
        }
        closeCurrentWindowIgnoreDialogue();
        return workflowIDs;
    }

    public void clearTextEntryField(WebElement textField)
    {
        clear(textField);
    }
}
