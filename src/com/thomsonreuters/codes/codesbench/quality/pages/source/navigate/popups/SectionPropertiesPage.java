package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.PropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.SectionPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractPropertiesPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionPropertiesPage extends AbstractPropertiesPage
{
    private WebDriver driver;

    @Autowired
    public SectionPropertiesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionPropertiesPageElements.class);
    }

    public void clickSave(){
        sendEnterToElement(SectionPropertiesPageElements.saveButton);
        waitForWindowGoneByTitle(SectionPropertiesPageElements.SECTION_PROPERTIES_PAGE_TITLE);
    }

    public void clickCancel() {
        super.clickCancel();
        waitForWindowGoneByTitle(PropertiesPageElements.SECTION_PROPERTIES_TITLE);
        switchToWindow(SourceNavigatePageElements.SOURCE_NAVIGATE_SECTION_PAGE_TITLE);
        waitForGridRefresh();
    }

    public void clickClose() {

    }

    //--------------------------------------------------------//
    //---------------Section Properties Methods---------------//
    //--------------------------------------------------------//

    public void openSectionPropertiesTab()
    {
        click(SectionPropertiesPageElements.sectionPropertiesTab);
    }

    public boolean setAssignedDate(String date)
    {
        sendTextToTextbox(SectionPropertiesPageElements.assignedDateInput, date);
        return getAssignedDate().equals(date);
    }

    public String getAssignedDate()
    {
        return SectionPropertiesPageElements.assignedDateInput.getAttribute("value");
    }

    public boolean setAssignedUser(String user)
    {
        selectDropdownOption(SectionPropertiesPageElements.assignedUserInput, user);
        return getAssignedUser().equals(user);
    }

    public String getAssignedUser()
    {
        return getSelectedDropdownOptionText(SectionPropertiesPageElements.assignedUserInput);
    }

    public boolean clearAssignedUserAndDate()
    {
        return setAssignedDate("") && setAssignedUser("");
    }

    public boolean clearSectionEffectiveDate()
    {
        sendTextToTextbox(SectionPropertiesPageElements.effectiveDateInput, "");
        return SectionPropertiesPageElements.effectiveDateInput.getAttribute("value").isEmpty();
    }

    //-------------------------------------------------------//
    //---------Proposed/Approved Tracking Information--------//
    //-------------------------------------------------------//

    public void openProposedApprovedTrackingInfoTab()
    {
        click(SectionPropertiesPageElements.proposedTrackingInfoTab);
    }

    public String getImagesSentOut() {
        return SectionPropertiesPageElements.imagesSentOut.getAttribute("value");
    }

    public String getImagesCompleted()
    {
        return SectionPropertiesPageElements.imagesCompleted.getAttribute("value");
    }

    public String getTabularRequested()
    {
        return SectionPropertiesPageElements.tabularRequested.getAttribute("value");
    }

    public String getTabularStarted()
    {
        return SectionPropertiesPageElements.tabularStarted.getAttribute("value");
    }

    public String getTabularCompleted()
    {
        return SectionPropertiesPageElements.tabularCompleted.getAttribute("value");
    }


    //-------------------------------------------------------//
    //---------------PREP Tracking Information---------------//
    //-------------------------------------------------------//
    public void openTrackingInfoTab()
    {
        click(SectionPropertiesPageElements.trackingInfoTab);
    }

    public String getAuditRequestedDate()
    {
        return SectionPropertiesPageElements.auditRequestedDate.getAttribute("value");
    }

    public String getAuditReviewStartedDate()
    {
        return SectionPropertiesPageElements.auditReviewStartedDate.getAttribute("value");
    }

    public String getAuditReviewCompletedDate()
    {
        return SectionPropertiesPageElements.auditReviewCompletedDate.getAttribute("value");
    }

    public String getPREPStartedDate()
    {
        return SectionPropertiesPageElements.PREPStartedDate.getAttribute("value");
    }

    public String getPREPCompletedDate()
    {
        return SectionPropertiesPageElements.PREPCompletedDate.getAttribute("value");
    }

    public String getReadyForIntegrationDate(){
        return SectionPropertiesPageElements.readyForIntegrationDate.getAttribute("value");
    }

    public String getCorrectionsAuditor() {
        return getSelectedDropdownOptionText(SectionPropertiesPageElements.correctionsAuditor);
    }

    public String getIntegrationStartedDate()
    {
        return SectionPropertiesPageElements.integrationStartedDate.getAttribute("value");
    }

    public String getIntegrationCompletedDate()
    {
        return SectionPropertiesPageElements.integrationCompletedDate.getAttribute("value");
    }

    public String getAttorneyWorkStartedDate()
    {
        return SectionPropertiesPageElements.attorneyWorkStartedDate.getAttribute("value");
    }

    public String getAttorneyWorkCompletedDate()
    {
        return SectionPropertiesPageElements.attorneyWorkCompletedDate.getAttribute("value");
    }

    public String getVersioningWorkStartedDate()
    {
        return SectionPropertiesPageElements.versioningWorkStartedDate.getAttribute("value");
    }

    public String getVersioningWorkCompletedDate()
    {
        return SectionPropertiesPageElements.versioningWorkCompletedDate.getAttribute("value");
    }

    public String getAuditCorrectionsStartedDate()
    {
        return SectionPropertiesPageElements.auditCorrectionsStartedDate.getAttribute("value");
    }

    public String getAuditCorrectionsCompletedDate()
    {
        return SectionPropertiesPageElements.auditCorrectionsCompletedDate.getAttribute("value");
    }

    public String getIntegration2StartedDate()
    {
        return SectionPropertiesPageElements.integration2StartedDate.getAttribute("value");
    }

    public String getIntegration2CompletedDate()
    {
        return SectionPropertiesPageElements.integration2CompletedDate.getAttribute("value");
    }

    public void unCheckIntegrationQueryStartedCheckbox()
    {
        uncheckCheckbox(SectionPropertiesPageElements.integrationQueryStartedCheckbox);
    }

    public boolean isIntegrationQueryStartedCheckboxChecked()
    {
        return isCheckboxChecked(SectionPropertiesPageElements.integrationQueryStartedCheckbox);
    }

    public boolean isIntegrationQueryStartedOwner(String owner){
        return SectionPropertiesPageElements.integrationQueryStartedOwner.getAttribute("value").equals(owner);
    }

    public void unCheckIntegrationQueryCompletedCheckbox()
    {
        uncheckCheckbox(SectionPropertiesPageElements.integrationQueryCompletedCheckbox);
    }

    public boolean isIntegrationQueryCompletedCheckboxChecked()
    {
        return isCheckboxChecked(SectionPropertiesPageElements.integrationQueryCompletedCheckbox);
    }

    public boolean isIntegrationQueryCompletedOwner(String owner){
        return SectionPropertiesPageElements.integrationQueryCompletedOwner.getAttribute("value").equals(owner);
    }

    public String getCorrectionsAuditStartedDate()
    {
        return SectionPropertiesPageElements.correctionsAuditStartedDate.getAttribute("value");
    }

    public String getCorrectionsAuditCompletedDate()
    {
        return SectionPropertiesPageElements.correctionsAuditCompletedDate.getAttribute("value");
    }

    public String getCorrectionsAuditRequestedDate()
    {
        return SectionPropertiesPageElements.correctionsAuditRequestedDate.getAttribute("value");
    }

    public String getCleanDate()
    {
        return SectionPropertiesPageElements.cleanDate.getAttribute("value");
    }

    public String getReleasedToWestLawDate()
    {
        return SectionPropertiesPageElements.releasedToWestLawDate.getAttribute("value");
    }
}
