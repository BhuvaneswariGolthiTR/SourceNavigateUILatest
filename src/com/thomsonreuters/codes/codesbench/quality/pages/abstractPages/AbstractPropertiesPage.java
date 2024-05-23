package com.thomsonreuters.codes.codesbench.quality.pages.abstractPages;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.PropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.RenditionPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AbstractPropertiesPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AbstractPropertiesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PropertiesPageElements.class);
    }

    public void clearEffectiveDateInProperties()
    {
        clear(PropertiesPageElements.effectiveDateInput);
    }

    public boolean clickCalendarDay(int day)
    {
        click(String.format(RenditionPropertiesPageElements.CALENDAR_DAY,day));
        return !isElementDisplayed(RenditionPropertiesPageElements.CALCONTAINER_BLOCK);
    }

    public boolean openSectionEffectiveDateCalendar()
    {
        return openCalendar("showSectionEffectiveDateCalendar");
    }

    public boolean openShowAssignedToDateCalendar()
    {
        return openCalendar("showAssignedToDateCalendar");
    }

    public boolean openTabularRequestedDateCalendar()
    {
        return openCalendar("showTabularRequestedDateCalendar");
    }

    private boolean openCalendar(String calendarType)
    {
        click(String.format(RenditionPropertiesPageElements.RENDITION_PROPERTIES_CALENDAR,calendarType));
        return isElementDisplayed(RenditionPropertiesPageElements.CALCONTAINER_BLOCK);
    }

    public boolean clearAssignedUser()
    {
        assignUserPage().selectAssignedUserDropdown("");
        return assignUserPage().getAssignedUserDropdownText().equals("");
    }

    public boolean clearAssignedDate()
    {
        clear(PropertiesPageElements.assignedDate);
        return getAssignedUserDate().equals("");
    }

    public String getAssignedUserDate()
    {
        return getElementsAttribute(PropertiesPageElements.assignedDate, "value");
    }

    public String getApprovalDate()
    {
        return getElementsAttribute(PropertiesPageElements.approvalDate, "value");
    }

    public void goToProposedApprovedTrackingInformationTab()
    {
        click(PropertiesPageElements.proposedApprovedTrackingInformationTab);
        click(PropertiesPageElements.proposedApprovedTrackingInformationTab);
        waitForVisibleElement(PropertiesPageElements.PROPOSED_APPROVED_TRACKING_INFORMATION_BLIND_BODY);
    }

    public void goToSystemPropertiesTab()
    {
        click(PropertiesPageElements.systemPropertiesTab);
        click(PropertiesPageElements.systemPropertiesTab);
        waitForVisibleElement(PropertiesPageElements.SYSTEM_PROPERTIES_BLIND_BODY);
    }

    public void clickPrepTrackingInformationTab()
    {
        click(PropertiesPageElements.prepTrackingInformationTab);
        click(PropertiesPageElements.prepTrackingInformationTab);
        waitForVisibleElement(PropertiesPageElements.PREP_TRACKING_INFORMATION_TAB_BLIND_BODY);
    }

    public String getSyncToWestlawCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.syncToWestlawCompletedDate, "value");
    }

    public String getChapterApprovalReceivedDate()
    {
        return getElementsAttribute(PropertiesPageElements.chapterApprovalReceivedDate, "value");
    }

    public String getApvStartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.apvStartedDate, "value");
    }

    public String getApvCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.apvCompletedDate, "value");
    }

    public String getTopicalHeadingNeededDate()
    {
        return getElementsAttribute(PropertiesPageElements.topicalHeadingNeededDate, "value");
    }

    public String getTopicalHeadingCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.topicalHeadingCompletedDate, "value");
    }

    public String getImageSentOutDate()
    {
        return getElementsAttribute(PropertiesPageElements.imagesSentOutDate, "value");
    }

    public void clearImageSentOutDate()
    {
        clear(PropertiesPageElements.imagesSentOutDate);
    }

    public String getImagesCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.imagesCompletedDate, "value");
    }

    public void clearImagesCompletedDate()
    {
        clear(PropertiesPageElements.imagesCompletedDate);
    }

    public String getTabularRequestedDate()
    {
        return getElementsAttribute(PropertiesPageElements.tabularRequestedDate, "value");
    }

    public String getTabularReceivedHardcopyDate()
    {
        return getElementsAttribute(PropertiesPageElements.tabularReceivedHardcopyDate, "value");
    }

    public void clearTabularRequestedDate()
    {
        clear(PropertiesPageElements.tabularRequestedDate);
    }

    public String getTabularStartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.tabularStartedDate, "value");
    }

    public void clearTabularStartedDate()
    {
        clear(PropertiesPageElements.tabularStartedDate);
    }

    public String getTabularCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.tabularCompletedDate, "value");
    }

    public String getApvReviewStartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.apvReviewStartedDate, "value");
    }

    public String getApvReviewCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.apvReviewCompletedDate, "value");
    }

    public String getReadyForAdvanceSheetDate()
    {
        return getElementsAttribute(PropertiesPageElements.advanceSheetReadyDate, "value");
    }

    public String getPublishedInAdvanceSheetDate()
    {
        return getElementsAttribute(PropertiesPageElements.advanceSheetPublishedDate, "value");
    }

    public String getAttorneyWorkStartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.attorneyWorkStartedDate, "value");
    }

    public String getAttorneyWorkCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.attorneyWorkCompletedDate, "value");
    }

    public String getVersioningWorkStartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.versioningWorkStartedDate, "value");
    }

    public String getVersioningWorkCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.versioningWorkCompletedDate, "value");
    }

    public String getPrepStartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.prepStartedDate, "value");
    }

    public String getPrepCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.prepCompletedDate, "value");
    }

    public String getReadyForIntegrationDate()
    {
        return getElementsAttribute(PropertiesPageElements.readyForIntegrationDate, "value");
    }

    public String getIntegrationStartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.integrationStartedDate, "value");
    }

    public String getIntegrationCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.integrationCompletedDate, "value");
    }

    public String getIntegration2StartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.integration2StartedDate, "value");
    }

    public String getIntegration2CompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.integration2CompletedDate, "value");
    }

    public String getAuditReviewRequestedDate()
    {
        return getElementsAttribute(PropertiesPageElements.auditReviewRequestedDate, "value");
    }

    public String getAuditReviewStartedDate()
    {
        return getElementsAttribute(PropertiesPageElements.auditReviewStartedDate, "value");
    }

    public String getAuditReviewCompletedDate()
    {
        return getElementsAttribute(PropertiesPageElements.auditReviewCompletedDate, "value");
    }

    public String getCorrectionsAuditor()
    {
        return getSelectedDropdownOptionText(PropertiesPageElements.correctionsAuditorDropdown);
    }

    public String getPrepCleanDate()
    {
        return getElementsAttribute(PropertiesPageElements.prepCleanDate, "value");
    }

    public String getReleasedToWestlawDate()
    {
        return getElementsAttribute(PropertiesPageElements.releasedToWestlawDate, "value");
    }

    public void clearTabularCompletedDate()
    {
        clear(PropertiesPageElements.tabularCompletedDate);
    }

    public void clickCancel()
    {
        click(CommonPageElements.CANCEL_BUTTON);
        waitForAlert();
        AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to cancel?");
    }

    public void closeProperties()
    {
        click(PropertiesPageElements.cancelButton);
        AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to cancel?");
        waitForWindowGoneByTitle(PropertiesPageElements.PROPERTIES_TITLE);
    }

    public void clickSave()
    {
        click(PropertiesPageElements.saveButton);
        waitForWindowGoneByTitle(PropertiesPageElements.PROPERTIES_TITLE);
    }
}
