package com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PropertiesPageElements
{
    public static final String DELTA_PROPERTIES_PAGE_TITLE = "Delta Properties";
    public static final String RENDITION_PROPERTIES_TITLE = "Rendition Properties";
    public static final String SECTION_PROPERTIES_TITLE = "Section Properties";
    public static final String PROPERTIES_TITLE = "Properties";

    public static final String PROPERTIES_CALENDAR_TODAY = "//td[contains(@class, 'today')]";

    @FindBy(how = How.ID, using = "pageForm:effectiveDate")
    public static WebElement effectiveDateInput;

    @FindBy(how = How.ID, using = "pageForm:assignedToDate")
    public static WebElement assignedDate;

    @FindBy(how = How.ID, using = "pageForm:approvalDate")
    public static WebElement approvalDate;

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement saveButton;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;

    //Proposed/Approved Tracking Information Tab
    public static final String PROPOSED_APPROVED_TRACKING_INFORMATION_BLIND_BODY = "//div[@id='ProposedTrackingInfoBlind_body']";

    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Proposed/Approved Tracking Information')]")
    public static WebElement proposedApprovedTrackingInformationTab;

    @FindBy(how = How.ID, using = "pageForm:chapterApprovalReceived")
    public static WebElement chapterApprovalReceivedDate;

    @FindBy(how = How.ID, using = "pageForm:apvStarted")
    public static WebElement apvStartedDate;

    @FindBy(how = How.ID, using = "pageForm:apvCompleted")
    public static WebElement apvCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:topicalHeadingNeeded") //Rendition: pageForm:topicalHeadingNeededDate
    public static WebElement topicalHeadingNeededDate;

    @FindBy(how = How.ID, using = "pageForm:topicalHeadingCompleted") // Rendition: pageForm:topicalHeadingCompletedDate
    public static WebElement topicalHeadingCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:imagesSentOut")
    public static WebElement imagesSentOutDate;

    @FindBy(how = How.ID, using = "pageForm:imagesCompleted")
    public static WebElement imagesCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:tabularRequestedDate")
    public static WebElement tabularRequestedDate;

    @FindBy(how = How.ID, using = "pageForm:tabularReceivedHardcopyDate")
    public static WebElement tabularReceivedHardcopyDate;

    @FindBy(how = How.ID, using = "pageForm:tabularStartedDate")
    public static WebElement tabularStartedDate;

    @FindBy(how = How.ID, using = "pageForm:tabularCompletedDate")
    public static WebElement tabularCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:apvReviewStarted")
    public static WebElement apvReviewStartedDate;

    @FindBy(how = How.ID, using = "pageForm:apvReviewCompleted")
    public static WebElement apvReviewCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:advanceSheetReady")
    public static WebElement advanceSheetReadyDate;

    @FindBy(how = How.ID, using = "pageForm:advanceSheetPublished")
    public static WebElement advanceSheetPublishedDate;

    @FindBy(how = How.ID, using = "pageForm:proposedTaskCompletedDate")
    public static WebElement syncToWestlawCompletedDate;

    //PREP Tracking Information Tab
    public static final String PREP_TRACKING_INFORMATION_TAB_BLIND_BODY = "//div[@id='TrackingInfoBlind_body']";

    @FindBy(how = How.XPATH, using = "//div[contains(text(),'PREP Tracking Information')]")
    public static WebElement prepTrackingInformationTab;

    @FindBy(how = How.ID, using = "pageForm:attorneyWorkStartedDate")
    public static WebElement attorneyWorkStartedDate;

    @FindBy(how = How.ID, using = "pageForm:attorneyWorkCompletedDate")
    public static WebElement attorneyWorkCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:versioningWorkStartedDate")
    public static WebElement versioningWorkStartedDate;

    @FindBy(how = How.ID, using = "pageForm:versioningWorkCompletedDate")
    public static WebElement versioningWorkCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:worksheetStartedDate")
    public static WebElement prepStartedDate;

    @FindBy(how = How.ID, using = "pageForm:worksheetCompletedDate")
    public static WebElement prepCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:readyForIntegrationDate")
    public static WebElement readyForIntegrationDate;

    @FindBy(how = How.ID, using = "pageForm:integrationStartedDate")
    public static WebElement integrationStartedDate;

    @FindBy(how = How.ID, using = "pageForm:integrationCompletedDate")
    public static WebElement integrationCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:integration2StartedDate")
    public static WebElement integration2StartedDate;

    @FindBy(how = How.ID, using = "pageForm:integration2CompletedDate")
    public static WebElement integration2CompletedDate;

    @FindBy(how = How.ID, using = "pageForm:auditRequestedDate")
    public static WebElement auditReviewRequestedDate;

    @FindBy(how = How.ID, using = "pageForm:auditStartedDate")
    public static WebElement auditReviewStartedDate;

    @FindBy(how = How.ID, using = "pageForm:auditCompletedDate")
    public static WebElement auditReviewCompletedDate;

    @FindBy(how = How.ID, using = "pageForm:correctionsAuditor")
    public static WebElement correctionsAuditorDropdown;

    @FindBy(how = How.ID, using = "pageForm:cleanDate")
    public static WebElement prepCleanDate;

    @FindBy(how = How.ID, using = "pageForm:releasedToWestlawDate")
    public static WebElement releasedToWestlawDate;

    //Proposed/Approved Tracking Information
    @FindBy(how = How.ID, using  = "ProposedTrackingInfoBlind_header")
    public static WebElement proposedTrackingInfoTab;

    @FindBy(how = How.ID, using  = "pageForm:tabularRequestedDate")
    public static WebElement tabularRequested;

    @FindBy(how = How.ID, using  = "pageForm:tabularStartedDate")
    public static WebElement tabularStarted;

    @FindBy(how = How.ID, using  = "pageForm:apvReviewStarted")
    public static WebElement apvReviewStarted;

    @FindBy(how = How.ID, using  = "pageForm:advanceSheetReady")
    public static WebElement readyForAdvancedSheet;

    @FindBy(how = How.ID, using  = "pageForm:apvCompleted")
    public static WebElement apvCompleted;

    @FindBy(how = How.ID, using  = "pageForm:chapterApprovalReceived")
    public static WebElement chapterApprovalReceived;

    @FindBy(how = How.ID, using  = "pageForm:topicalHeadingCompleted")
    public static WebElement topicalHeadingCompleted;

    @FindBy(how = How.ID, using  = "pageForm:imagesCompleted")
    public static WebElement imagesCompleted;

    @FindBy(how = How.ID, using  = "pageForm:tabularReceivedHardcopyDate")
    public static WebElement tabularReceivedHardcopy;

    @FindBy(how = How.ID, using  = "pageForm:tabularCompletedDate")
    public static WebElement tabularCompleted;

    @FindBy(how = How.ID, using  = "pageForm:apvReviewCompleted")
    public static WebElement apvReviewCompleted;

    @FindBy(how = How.ID, using  = "pageForm:advanceSheetPublished")
    public static WebElement publishedInAdvancedSheet;

    //System Properties Tab
    public static final String SYSTEM_PROPERTIES_BLIND_BODY = "//div[@id='SystemPropsBlind_body']";

    @FindBy(how = How.XPATH, using = "//div[contains(text(),'System Properties')]")
    public static WebElement systemPropertiesTab;
}
