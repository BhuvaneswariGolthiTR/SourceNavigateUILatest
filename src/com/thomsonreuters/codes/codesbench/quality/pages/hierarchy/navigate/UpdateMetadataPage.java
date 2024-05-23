package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.UpdateMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class UpdateMetadataPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public UpdateMetadataPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UpdateMetadataPageElements.class);
    }

    public String getNodeUuid()
    {
        return getElementsText(UpdateMetadataPageElements.nodeUuid).toLowerCase();
    }

    public String getContentUuid()
    {
        return getElementsText(UpdateMetadataPageElements.contentUuid).toLowerCase();
    }

    public void clickCancel()
    {
        click(UpdateMetadataPageElements.cancelButton);
        waitForWindowGoneByTitle(UpdateMetadataPageElements.UPDATE_METADATA_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void clickUpdate()
    {
        click(UpdateMetadataPageElements.updateButton);
    }

    public void enterEffectiveEndDate(String endDate)
    {
        if(isElementDisplayed(UpdateMetadataPageElements.EFFECTIVE_END_DATE_CHECK_BOX))
        {
            click(UpdateMetadataPageElements.effectiveEndDateCheckbox);
        }
        clearAndSendKeysToElement(UpdateMetadataPageElements.effectiveEndDateTextBox,endDate);
    }

    public void clearEffectiveEndDate()
    {
        UpdateMetadataPageElements.effectiveEndDateTextBox.clear();
    }

    public String getEffectiveStartDate()
    {
        return getElementsAttribute(UpdateMetadataPageElements.effectiveStartDateTextBox, "value");
    }

    public String getEffectiveEndDate()
    {
        return getElementsAttribute(UpdateMetadataPageElements.effectiveEndDateTextBox, "value");
    }

    public void clickQuickLoadOk()
    {
        click(UpdateMetadataPageElements.quickLoadButton);
        waitForGridRefresh();
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        click(UpdateMetadataPageElements.updateButton);
        waitForWindowGoneByTitle(UpdateMetadataPageElements.UPDATE_METADATA_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public void clickSetLawTracking()
    {
        sendEnterToElement(UpdateMetadataPageElements.setLawTrackingButton);
    }

    public void clearEffectiveStartDate()
    {
        clear(UpdateMetadataPageElements.effectiveStartDateTextBox);
    }

    public void clearAndEnterStartDate(String startDate)
    {
        clearAndSendKeysToElement(UpdateMetadataPageElements.effectiveStartDateTextBox, startDate);
    }

    public void clearEndDate()
    {
        UpdateMetadataPageElements.effectiveEndDateTextBox.clear();
    }

    public List<String> getListOfKeywordOptions()
    {
        return getElementsTextList(UpdateMetadataPageElements.LIST_OF_KEYWORD_OPTIONS_XPATH);
    }

    public void setKeywordDropdown(String keyword)
    {
        selectDropdownOption(UpdateMetadataPageElements.keywordDropdown,keyword);
    }

    public void setStatusTo(String status)
    {
        if (isElementDisplayed(UpdateMetadataPageElements.STATUS_CHECK_BOX))
        {
            click(UpdateMetadataPageElements.statusCheckbox);
        }
        selectDropdownOption(UpdateMetadataPageElements.statusDropdown,status);
    }

    public String getSelectedCodeNameOption()
    {
        return getElementsText(UpdateMetadataPageElements.selectedCodeNameOption);
    }

    public String getSelectedNodeTypeOption()
    {
        return getElementsText(UpdateMetadataPageElements.selectedNodeTypeOption);
    }

    public String getSelectedKeywordOption()
    {
        return getElementsText(UpdateMetadataPageElements.selectedKeywordOption);
    }

    public String getSelectedCodeLevelOption()
    {
        return getElementsText(UpdateMetadataPageElements.selectedCodeLevelOption);
    }

    public String getSelectedVersionedOption()
    {
        return getElementsText(UpdateMetadataPageElements.selectedVersionedOption);
    }

    public void setCodeName(String valueToSet)
    {
        if(isElementDisplayed(UpdateMetadataPageElements.CODENAME_CHECK_BOX))
        {
            click(UpdateMetadataPageElements.codeNameCheckbox);
        }
        selectDropdownOption(UpdateMetadataPageElements.codeNameDropdown,valueToSet);
    }

    public void setNodeType(String valueToSet)
    {
        selectDropdownOption(UpdateMetadataPageElements.nodeTypeDropdown,valueToSet);
    }

    public void setCodeLevel(String valueToSet)
    {
        selectDropdownOption(UpdateMetadataPageElements.codeLevelDropdown,valueToSet);
    }

    public void setVersioned(String valueToSet)
    {
        if(isElementDisplayed(UpdateMetadataPageElements.VERSIONED_CHECK_BOX))
        {
            click(UpdateMetadataPageElements.versionedCheckbox);
        }
        selectDropdownOption(UpdateMetadataPageElements.versionedDropdown,valueToSet);
    }

    public void enterEffectiveStartDate(String startDate)
    {
        if(isElementDisplayed(UpdateMetadataPageElements.EFFECTIVE_START_DATE_CHECK_BOX))
        {
            click(UpdateMetadataPageElements.effectiveStartDateCheckbox);
        }
        clearAndSendKeysToElement(UpdateMetadataPageElements.effectiveStartDateTextBox,startDate);
    }

    public void clearAndInsertTextToValueTextBox(String textToAdd)
    {
        click(UpdateMetadataPageElements.valueTextBox);
        clearAndSendKeysToElement(UpdateMetadataPageElements.valueTextBox,textToAdd);
    }

    public String getLawTrackingText()
    {
        return getElementsAttribute(UpdateMetadataPageElements.LAW_TRACKING_TEXT, "value");
    }
}
