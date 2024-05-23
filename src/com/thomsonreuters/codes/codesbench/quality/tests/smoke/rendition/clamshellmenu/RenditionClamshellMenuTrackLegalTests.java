package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionClamshellMenuTrackLegalTests extends TestService
{
    SourceDatapodObject apv;
    SourceDatapodObject prep;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Sync To Westlaw on first rendition using clamshell menu and verifies rendition tracking status is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void syncToWestlawLegalTest()
    {
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2017");
        sourceNavigateFiltersAndSortsPage().setFilterSession("2RG");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("DRA");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFiltersAndSortsPage().setFilterDocType("HF");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("150210");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle("TITLE: Sync To Westlaw");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().deleteRenditions();

        //go to Create -> Add Content in the clamshell, fill in the information, click Add Content, accept alert
        clamshellPage().openSideBarCreate();
        boolean manualDataEntryWindowAppears = renditionTabCreateClamshellPage().clickAddContent(true, false);
        Assertions.assertTrue(manualDataEntryWindowAppears, "Manual Data Entry window appears");
        manualDataEntryPage().setJurisdictionDropdown("Iowa");
        manualDataEntryPage().setYear("2017");
        manualDataEntryPage().setSessionDropdown("2RG");
        manualDataEntryPage().setChamberDropdown("HOUSE");
        manualDataEntryPage().setContentTypeDropdown("BILL");
        manualDataEntryPage().setDocumentTypeDropdown("HF");
        manualDataEntryPage().setDocumentNumber("150210");
        manualDataEntryPage().setLegislationTypeDropdown("BILL");
        manualDataEntryPage().setDocumentTitle("Sync To Westlaw");
        manualDataEntryPage().setSponsors("Bananas in Pajamas");
        manualDataEntryPage().checkManualContentDataEntryCheckBox();
        manualDataEntryPage().setRenditionStatusDropdown("DFT");
        manualDataEntryPage().setSourceVersionDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        manualDataEntryPage().setSelectedFile("EO102.pdf");
        manualDataEntryPage().setReasonDropdown("MANUAL_ACQUISITION_REQUIRED");
        boolean dataIsSubmitted = manualDataEntryPage().clickAddContent();
        Assertions.assertTrue(dataIsSubmitted, "Data has been submitted for input");

        //verify workflow finishes and refresh source nav grid
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus("LcprBillTextSuperquick", "LCPR.IA", "LCPR Bill Text", user().getUsername().toUpperCase());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowId != null && workflowCompleted, String.format("Workflow: %s completed", workflowId));
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForPageLoaded();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Sync To Westlaw Completed in clamshell for first rendition
        String renditionUuid = sourceNavigateGridPage().getUUID();
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickSyncToWestlawCompleted(true, false);

        //check approved date is today and delete first rendition
        sourceNavigateGridPage().clickFirstRendition();
        boolean renditionStatusIsSyncToWestlaw = sourceNavigateGridPage().getRenditionTrackingStatus().equals("Sync to Westlaw Completed");
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean approvedDateIsToday = renditionPropertiesPage().getSyncToWestlawCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();

        SourceDatabaseUtils.setRenditionDeleted(connection, renditionUuid, user().getUsername());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(renditionStatusIsSyncToWestlaw, "Rendition tracking status is 'Sync to Westlaw Completed'"),
            () -> Assertions.assertTrue(approvedDateIsToday, "Approved Date is today")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Chapter/Approval Received on first rendition using clamshell menu and checks chapter/approval date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void chapterApprovalReceivedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Chapter/Approval Received in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickChapterApprovalReceived(true, false);

        //check approval date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean chapterApprovalReceivedDateIsToday = renditionPropertiesPage().getChapterApprovalReceivedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(chapterApprovalReceivedDateIsToday, "Chapter/Approval Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs APV Started on first rendition using clamshell menu and checks APV started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void apvStartedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> APV Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickApvStarted(true, false);

        //check APV started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean apvStartedDateIsToday = renditionPropertiesPage().getApvStartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(apvStartedDateIsToday, "APV Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs APV Completed on first rendition using clamshell menu and checks APV Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void apvCompletedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> APV Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickApvCompleted(true, false);

        //check APV Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean apvCompletedDateIsToday = renditionPropertiesPage().getApvCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(apvCompletedDateIsToday, "APV Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Topical Heading Needed on first rendition using clamshell menu and checks Topical Heading Needed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void topicalHeadingNeededLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Topical Heading Needed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickTopicalHeadingNeeded(true, false);

        //check Topical Heading Needed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean topicalHeadingNeededDateIsToday = renditionPropertiesPage().getTopicalHeadingNeededDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(topicalHeadingNeededDateIsToday, "Topical Heading Needed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Topical Heading Completed on first rendition using clamshell menu and checks Topical Heading Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void topicalHeadingCompletedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Topical Heading Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickTopicalHeadingCompleted(true, false);

        //check Topical Heading Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean topicalHeadingCompletedDateIsToday = renditionPropertiesPage().getTopicalHeadingCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(topicalHeadingCompletedDateIsToday, "Topical Heading Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Images Sent Out on first rendition using clamshell menu and checks Images Sent Out date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void imagesSentOutLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Images Sent Out in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickImagesSentOut(true, false);

        //check Images Sent Out date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean imagesSentOutDateIsToday = renditionPropertiesPage().getImageSentOutDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(imagesSentOutDateIsToday, "Images Sent Out Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Images Completed on first rendition using clamshell menu and checks Images Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void imagesCompletedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Images Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickImagesCompleted(true, false);

        //check Images Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean imagesCompletedDateIsToday = renditionPropertiesPage().getImagesCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(imagesCompletedDateIsToday, "Images Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Tabular Requested on first rendition using clamshell menu and checks Tabular Requested date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularRequestedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Tabular Requested in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickTabularRequested(true, false);

        //check Tabular Requested date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean tabularRequestedDateIsToday = renditionPropertiesPage().getTabularRequestedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(tabularRequestedDateIsToday, "Tabular Requested Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Tabular Received Hardcopy on first rendition using clamshell menu and checks Tabular Received Hardcopy date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularReceivedHardcopyLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Tabular Received Hardcopy in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickTabularReceivedHardcopy(true, false);

        //check Tabular Received Hardcopy date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean tabularReceivedHardcopyDateIsToday = renditionPropertiesPage().getTabularReceivedHardcopyDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(tabularReceivedHardcopyDateIsToday, "Tabular Received Hardcopy Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Tabular Started on first rendition using clamshell menu and checks Tabular Started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularStartedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Tabular Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickTabularStarted(true, false);

        //check Tabular Started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean tabularStartedDateIsToday = renditionPropertiesPage().getTabularStartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(tabularStartedDateIsToday, "Tabular Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Tabular Completed on first rendition using clamshell menu and checks Tabular Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularCompletedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Tabular Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickTabularCompleted(true, false);

        //check Tabular Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean tabularCompletedDateIsToday = renditionPropertiesPage().getTabularCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(tabularCompletedDateIsToday, "Tabular Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs APV Review Started on first rendition using clamshell menu and checks APV Review Started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void apvReviewStartedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> APV Review Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickApvReviewStarted(true, false);

        //check APV Review Started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean apvReviewStartedDateIsToday = renditionPropertiesPage().getApvReviewStartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(apvReviewStartedDateIsToday, "APV Review Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs APV Review Completed on first rendition using clamshell menu and checks APV Review Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void apvReviewCompletedLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> APV Review Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickApvReviewCompleted(true, false);

        //check APV Review Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean apvReviewCompletedDateIsToday = renditionPropertiesPage().getApvReviewCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(apvReviewCompletedDateIsToday, "APV Review Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Ready For Advance Sheet on first rendition using clamshell menu and checks Ready For Advance Sheet date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void readyForAdvanceSheetLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Ready For Advance Sheet in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickReadyForAdvanceSheet(true, false);

        //check Ready For Advance Sheet date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean readyForAdvanceSheetDateIsToday = renditionPropertiesPage().getReadyForAdvanceSheetDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(readyForAdvanceSheetDateIsToday, "Ready For Advance Sheet Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Published In Advance Sheet on first rendition using clamshell menu and checks Published In Advance Sheet date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishedInAdvanceSheetLegalTest()
    {
        renditionUuid = createAPVDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Published In Advance Sheet in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickPublishedInAdvanceSheet(true, false);

        //check Published In Advance Sheet date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean publishedInAdvanceSheetDateIsToday = renditionPropertiesPage().getPublishedInAdvanceSheetDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(publishedInAdvanceSheetDateIsToday, "Published In Advance Sheet Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Attorney Work Started on first rendition using clamshell menu and checks Attorney Work Started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void attorneyWorkStartedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Attorney Work Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickAttorneyWorkStarted(true, false);

        //check Attorney Work Started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean attorneyWorkStartedDateIsToday = renditionPropertiesPage().getAttorneyWorkStartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(attorneyWorkStartedDateIsToday, "Attorney Work Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Attorney Work Completed on first rendition using clamshell menu and checks Attorney Work Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void attorneyWorkCompletedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Attorney Work Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickAttorneyWorkCompleted(true, false);

        //check Attorney Work Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean attorneyWorkCompletedDateIsToday = renditionPropertiesPage().getAttorneyWorkCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(attorneyWorkCompletedDateIsToday, "Attorney Work Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Versioning Work Started on first rendition using clamshell menu and checks Versioning Work Started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void versioningWorkStartedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Versioning Work Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickVersioningWorkStarted(true, false);

        //check Versioning Work Started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean versioningWorkStartedDateIsToday = renditionPropertiesPage().getVersioningWorkStartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(versioningWorkStartedDateIsToday, "Versioning Work Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Versioning Work Completed on first rendition using clamshell menu and checks Versioning Work Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void versioningWorkCompletedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Versioning Work Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickVersioningWorkCompleted(true, false);

        //check Versioning Work Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean versioningWorkCompletedDateIsToday = renditionPropertiesPage().getVersioningWorkCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(versioningWorkCompletedDateIsToday, "Versioning Work Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Prep Started on first rendition using clamshell menu and checks Prep Started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void prepStartedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Prep Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickPrepStarted(true, false);

        //check Prep Started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean prepStartedDateIsToday = renditionPropertiesPage().getPrepStartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(prepStartedDateIsToday, "Prep Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Prep Completed on first rendition using clamshell menu and checks Prep Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void prepCompletedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Prep Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickPrepCompleted(true, false);

        //check Prep Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean prepCompletedDateIsToday = renditionPropertiesPage().getPrepCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(prepCompletedDateIsToday, "Prep Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Ready For Integration on first rendition using clamshell menu and checks Ready For Integration date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void readyForIntegrationLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Ready For Integration in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickReadyForIntegration(true, false);

        //check Ready For Integration date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean readyForIntegrationDateIsToday = renditionPropertiesPage().getReadyForIntegrationDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(readyForIntegrationDateIsToday, "Ready For Integration Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Integration Started on first rendition using clamshell menu and checks Integration Started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationStartedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Integration Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickIntegrationStarted(true, false);

        //check Integration Started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean integrationStartedDateIsToday = renditionPropertiesPage().getIntegrationStartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(integrationStartedDateIsToday, "Integration Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Integration Completed on first rendition using clamshell menu and checks Integration Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationCompletedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Integration Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickIntegrationCompleted(true, false);

        //check Integration Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean integrationCompletedDateIsToday = renditionPropertiesPage().getIntegrationCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(integrationCompletedDateIsToday, "Integration Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Integration 2 Started on first rendition using clamshell menu and checks Integration 2 Started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integration2StartedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Integration 2 Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickIntegration2Started(true, false);

        //check Integration 2 Started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean integration2StartedDateIsToday = renditionPropertiesPage().getIntegration2StartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(integration2StartedDateIsToday, "Integration 2 Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Integration 2 Completed on first rendition using clamshell menu and checks Integration 2 Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integration2CompletedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Integration 2 Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickIntegration2Completed(true, false);

        //check Integration 2 Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean integration2CompletedDateIsToday = renditionPropertiesPage().getIntegration2CompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(integration2CompletedDateIsToday, "Integration 2 Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Audit Review Requested on first rendition using clamshell menu and checks Audit Review Requested date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditReviewRequestedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Audit Review Requested in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickAuditRequested(true, false);

        //check Audit Review Requested date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean auditReviewRequestedDateIsToday = renditionPropertiesPage().getAuditReviewRequestedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(auditReviewRequestedDateIsToday, "Audit Review Requested Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Audit Review Started on first rendition using clamshell menu and checks Audit Review Started date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditReviewStartedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Audit Review Started in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickAuditReviewStarted(true, false);

        //check Audit Review Started date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean auditReviewStartedDateIsToday = renditionPropertiesPage().getAuditReviewStartedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(auditReviewStartedDateIsToday, "Audit Review Started Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Audit Review Completed on first rendition using clamshell menu and checks Audit Review Completed date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditReviewCompletedLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Audit Review Completed in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickAuditReviewCompleted(true, false);

        //check Audit Review Completed date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean auditReviewCompletedDateIsToday = renditionPropertiesPage().getAuditReviewCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(auditReviewCompletedDateIsToday, "Audit Review Completed Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Audit Corrections To Be Done on first rendition using clamshell menu and checks Audit Corrections To Be Done By is correct <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditCorrectionToBeDoneByLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Audit Corrections To Be Done By in clamshell for first rendition and set corrections auditor to EAGAN
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickAuditCorrectionsToBeDoneBy(true, false);
        auditCorrectionsPage().selectCorrectionsAuditor("EAGAN");
        auditCorrectionsPage().clickSave();

        //check Audit Corrections To Be Done By is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean auditCorrectionsToBeDoneByIsCorrect = renditionPropertiesPage().getCorrectionsAuditor().equals("EAGAN");
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(auditCorrectionsToBeDoneByIsCorrect, "Audit Corrections To Be Done By is 'EAGAN'");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Clean on first rendition using clamshell menu and checks Clean date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cleanLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Clean in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickClean(true, false);
        cleanPage().setMarkAsCleanDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        cleanPage().clickSave();

        //check Clean date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean cleanDateIsToday = renditionPropertiesPage().getPrepCleanDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(cleanDateIsToday, "Clean Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Released To Westlaw on first rendition using clamshell menu and checks Released To Westlaw date is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void releasedToWestlawLegalTest()
    {
        renditionUuid = createPrepDoc();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Track -> Released To Westlaw in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarTrack();
        renditionTabTrackClamshellPage().clickReleasedToWestlaw(true, false);
        releasedToWestlawPage().setReleasedToWestlawDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        releasedToWestlawPage().clickSave();

        //check Released To Westlaw date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        renditionPropertiesPage().clickPrepTrackingInformationTab();
        boolean releasedToWestlawDateIsToday = renditionPropertiesPage().getReleasedToWestlawDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(releasedToWestlawDateIsToday, "Released To Westlaw Date is today");
    }

    private String createAPVDoc()
    {
        apv = SourceDataMockingNew.Iowa.Small.APV.insert();
        String renditionUuid = apv.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);

        return renditionUuid;
    }

    private String createPrepDoc()
    {
        prep = SourceDataMockingNew.Iowa.Small.PREP.insert();
        String renditionUuid = prep.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);

        return renditionUuid;
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(apv != null)
        {
            apv.delete();
        }
        if(prep != null)
        {
            prep.delete();
        }
        disconnect(connection);
    }
}
