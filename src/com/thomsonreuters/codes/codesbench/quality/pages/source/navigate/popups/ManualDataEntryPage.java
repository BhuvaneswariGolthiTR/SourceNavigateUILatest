package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ManualDataEntryPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class ManualDataEntryPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ManualDataEntryPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ManualDataEntryPageElements.class);
    }

    public void setJurisdictionDropdown(String jurisdiction)
    {
        selectDropdownOption(ManualDataEntryPageElements.JURISDICTION_DROPDOWN, jurisdiction);
    }

    public void setYear(String year)
    {
        clearAndSendKeysToElement(getElement(ManualDataEntryPageElements.YEAR_TEXT_BOX), year);
    }

    public void setSessionDropdown(String session)
    {
        selectDropdownOption(ManualDataEntryPageElements.SESSION_DROPDOWN, session);
    }

    public void setChamberDropdown(String chamber)
    {
        selectDropdownOption(ManualDataEntryPageElements.CHAMBER_DROPDOWN, chamber);
    }

    public void setContentTypeDropdown(String contentType)
    {
        selectDropdownOption(ManualDataEntryPageElements.CONTENT_TYPE_DROPDOWN, contentType);
    }

    public void setDocumentTypeDropdown(String docType)
    {
        selectDropdownOption(ManualDataEntryPageElements.DOCUMENT_TYPE_DROPDOWN, docType);
    }

    public void setLegislationTypeDropdown(String legType)
    {
        selectDropdownOption(ManualDataEntryPageElements.LEGISLATION_TYPE_DROPDOWN, legType);
    }

    public void setDocumentNumber(String docNum)
    {
        clearAndSendKeysToElement(getElement(ManualDataEntryPageElements.DOCUMENT_NUMBER_TEXT_BOX), docNum);
    }

    public void setDocumentTitle(String docTitle)
    {
        clearAndSendKeysToElement(getElement(ManualDataEntryPageElements.DOCUMENT_TITLE_TEXT_BOX), docTitle);
    }

    public void setSponsors(String sponsors)
    {
        clearAndSendKeysToElement(getElement(ManualDataEntryPageElements.SPONSORS_TEXT_BOX), sponsors);
    }

    public void setSummary(String summary)
    {
        clearAndSendKeysToElement(getElement(ManualDataEntryPageElements.SUMMARY_TEXT_BOX), summary);
    }

    public void checkManualContentDataEntryCheckBox()
    {
        checkCheckbox(ManualDataEntryPageElements.MANUAL_CONTENT_DATA_ENTRY_CHECK_BOX);
    }

    public void setRenditionStatusDropdown(String renditionStatus)
    {
        selectDropdownOption(ManualDataEntryPageElements.RENDITION_STATUS_DROPDOWN, renditionStatus);
    }

    public void setSourceVersionDate(String sourceVersionDate)
    {
        sendKeysToElement(getElement(ManualDataEntryPageElements.SOURCE_VERSION_DATE_TEXT_BOX), sourceVersionDate);
    }

    public void setSelectedFile(String inputFile)
    {
        String filePath = new File("commonFiles\\TestFiles\\" + inputFile).getAbsolutePath();
        sendKeysToElement(ManualDataEntryPageElements.SELECTED_FILE_TEXT_BOX, filePath);
    }

    public void setReasonDropdown(String reason)
    {
        selectDropdownOption(ManualDataEntryPageElements.REASON_DROPDOWN, reason);
    }

    public void checkManualBillTrackDataEntryCheckBox()
    {
        checkCheckbox(ManualDataEntryPageElements.MANUAL_BILL_TRACK_DATA_ENTRY_CHECK_BOX);
    }

    public void setDateX(int dateX, String date)
    {
        sendKeysToElement(getElement(String.format(ManualDataEntryPageElements.BILL_TRACK_DATE_X, dateX)), date);
    }

    public void setActionX(int actionX, String action)
    {
        sendKeysToElement(getElement(String.format(ManualDataEntryPageElements.BILL_TRACK_ACTION_X, actionX)), action);
    }

    public void setStageX(int stageX, String stage)
    {
        selectDropdownOption(String.format(ManualDataEntryPageElements.BILL_TRACK_STAGE_X, stageX), stage);
    }

    public boolean clickAddContent()
    {
        click(ManualDataEntryPageElements.ADD_CONTENT_BUTTON);
        boolean firstAlert = AutoITUtils.verifyAlertTextContainsAndAccept(true, "No bill track was selected.  No new Bill track\nfile will be created.  OK to proceed?");
        boolean secondAlert = AutoITUtils.verifyAlertTextAndAccept(true, "Data has been submitted for input.");
        sourcePage().switchToSourceNavigatePage();
        return firstAlert && secondAlert;
    }

    public boolean switchToManualDataEntryWindow()
    {
        boolean windowAppears = switchToWindow(ManualDataEntryPageElements.MANUAL_DATA_ENTRY_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }
}
