package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.UpdateMetadataMultiplePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.UpdateMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class UpdateMetadataMultiplePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public UpdateMetadataMultiplePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UpdateMetadataMultiplePageElements.class);
    }

    public void clickQuickLoad()
    {
        sendEnterToElement(UpdateMetadataMultiplePageElements.quickLoadButton);
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public void clickUpdate(boolean alertExpected)
    {
        sendEnterToElement(UpdateMetadataMultiplePageElements.updateButton);
        if(!alertExpected)
        {
            waitForWindowGoneByTitle(UpdateMetadataPageElements.UPDATE_METADATA_PAGE_TITLE);
            switchToWindow(HierarchyPageElements.PAGE_TITLE);
        }
    }

    public void clickCancel()
    {
        sendEnterToElement(UpdateMetadataMultiplePageElements.cancelButton);
        waitForWindowGoneByTitle(UpdateMetadataPageElements.UPDATE_METADATA_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    /**
     * This method takes in a string that will be used as a code name. IF the clickCheckBox boolean is set to true, it will check the Code Name checkbox.
     * If the codeName string is not an empty string, it will be selected from the Code Name dropdown menu, otherwise no Code Name will be selected.
     * @param codeName
     * @param clickCheckBox
     */
    public void updateCodeName(String codeName, boolean clickCheckBox)
    {
        if(clickCheckBox)
        {
            checkCheckbox(UpdateMetadataMultiplePageElements.codeNameCheckBox);
        }
        if(codeName != "")
        {
            sendKeysToElement(UpdateMetadataMultiplePageElements.codeNameDropdownMenu, codeName);
        }
    }

    /**
     * This method takes in a string that will be used as an Effective Start Date. If the clickCheckBox boolean is set to true, it will check the Effective Start Date checkbox.
     * If the effectiveStartDate string is not an empty string, it will be entered into the Effective Start Date entry field, otherwise no Effective Start Date will be entered.
     * @param date
     * @param clickCheckBox
     */
    public void setEffectiveStartDate(String date, boolean clickCheckBox)
    {
        if(clickCheckBox)
        {
            click(UpdateMetadataMultiplePageElements.effectiveStartDateCheckBox);
        }
        if(date != "")
        {
            sendKeysToElement(UpdateMetadataMultiplePageElements.effectiveStartDateEntryField, date);
        }
    }

    /**
     * This method takes in a string that will be used as a versioned status. If the clickCheckBox boolean is set to true, it will check the Versioned checkbox.
     * If the versionedSelection string is not an empty string, it will be selected from the Versioned dropdown menu, otherwise no Versioned option will be selected.
     * @param versionedSelection
     * @param clickCheckBox
     */
    public void updateVersioned(String versionedSelection, boolean clickCheckBox)
    {
        if(clickCheckBox)
        {
            checkCheckbox(UpdateMetadataMultiplePageElements.versionedCheckBox);
        }
        if(versionedSelection != "")
        {
            sendKeysToElement(UpdateMetadataMultiplePageElements.versionedDropdownMenu, versionedSelection);
        }
    }

    /**
     * This method takes in a string that will be used as a Code Level status. If the clickCheckBox boolean is set to true, it will check the Code Level checkbox.
     * If the codeLevel string is not an empty string, it will be selected from the Code Level dropdown menu, otherwise no Code Level option will be selected.
     * @param codeLevel
     * @param clickCheckBox
     */
    public void updateCodeLevel(String codeLevel, boolean clickCheckBox)
    {
        if(clickCheckBox)
        {
            checkCheckbox(UpdateMetadataMultiplePageElements.codeLevelCheckBox);
        }
        if(codeLevel != "")
        {
            sendKeysToElement(UpdateMetadataMultiplePageElements.codeLevelDropdownMenu, codeLevel);
        }
    }
}