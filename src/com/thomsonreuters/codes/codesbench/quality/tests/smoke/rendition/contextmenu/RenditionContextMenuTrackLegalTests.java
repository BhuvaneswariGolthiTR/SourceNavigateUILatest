package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionContextMenuTrackLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Sync To Westlaw on first rendition using context menu and verifies rendition tracking status is correct  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void syncToWestlawLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
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

        //Add Content from clamshell menu
        clamshellPage().openSideBarCreate();
        boolean manualDataEntryWindowAppears = renditionTabCreateClamshellPage().clickAddContent(true, false);
        Assertions.assertTrue(manualDataEntryWindowAppears, "Manual Data Entry window appears");

        //Set values on add content page
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
        manualDataEntryPage().setSourceVersionDate(currentDate);
        manualDataEntryPage().setSelectedFile("EO102.pdf");
        manualDataEntryPage().setReasonDropdown("MANUAL_ACQUISITION_REQUIRED");

        //Add content and switch back to source navigate
        manualDataEntryPage().clickAddContent();

        //Navigate to workflow reporting system page and filter for workflow, then verify the workflow finished
        toolsMenu().goToWorkflowReportingSystem();

        //Verify workflow finished
        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("LcprBillTextSuperquick",
                "LCPR.IA", "LCPR Bill Text", user().getUsername().toUpperCase());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowFinished,  String.format("LcprBillTextSuperquick workflow: %s finished successfully", workflowId));
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Verifies tracking status is 'Sync to Westlaw Completed'
        String renditionUuid = sourceNavigateGridPage().getUUID();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackSyncToWestlawCompleted();
        sourceNavigateGridPage().clickFirstRendition();
        boolean syncToWestlawCompleted = sourceNavigateGridPage().getRenditionTrackingStatus().equals("Sync to Westlaw Completed");

        //Verifies sync to westlaw completed date is today
        clamshellPage().openSideBarEdit();
        renditionTabEditClamshellPage().clickProperties(true,false);
        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean syncToWestlawCompletedDateIsToday = renditionPropertiesPage().getSyncToWestlawCompletedDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        SourceDatabaseUtils.setRenditionDeleted(connection, renditionUuid, user().getUsername());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(syncToWestlawCompleted, "Rendition tracking status is 'Sync to Westlaw Completed'"),
            () -> Assertions.assertTrue(syncToWestlawCompletedDateIsToday, "Approved Date is today")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'APV Started' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'APV Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void apvStartedLegalTest()
    {
       String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'APV Started' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackAPVStarted();

        //Go to rendition properties page and verify 'APV Started' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getAPVStarted().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "APV Started Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Topical Heading Needed' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Topical Heading Needed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void topicalHeadingNeededLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Topical Heading Needed' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackTopicalHeadingNeeded();

        //Go to rendition properties page and verify 'Topical Heading Needed' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getTopicalHeadingNeeded().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Topical Heading Needed Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Images Sent Out' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Images Sent Out' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void imagesSentOutLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Images Sent Out' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackImagesSentOut();

        //Go to rendition properties page and verify 'Images Sent Out' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getImageSentOutDate().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Images Sent Out Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Tabular Requested' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Tabular Requested' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularRequestedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Tabular Requested' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackTabularRequested();

        //Go to rendition properties page and verify 'Tabular Requested' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getTabularRequested().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Tabular Requested Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Tabular Started' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Tabular Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Tabular Started' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackTabularStarted();

        //Go to rendition properties page and verify 'Tabular Started' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getTabularStarted().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Tabular Started Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'APV Review Started' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'APV Review Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void apvReviewStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'APV Review Started' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackAPVReviewStarted();

        //Go to rendition properties page and verify 'APV Review Started' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getAPVReviewStarted().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Tabular Review Started Date is correct");

        renditionPropertiesPage().clickCancel();
    }


    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Ready For Advanced Sheet' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Ready For Advanced Sheet' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void readyForAdvancedSheetLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Ready For Advanced Sheet' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackReadyForAdvancedSheet();

        //Go to rendition properties page and verify 'Ready For Advanced Sheet' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getReadyForAdvancedSheet().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Ready For Advanced Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Chapter Approval Received' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Chapter Approval Received' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void chapterApprovalReceivedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Chapter Approval Received' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackChapterApprovalReceived();

        //Go to rendition properties page and verify 'Chapter Approval Received' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getChapterApprovalReceived().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Chapter/Approval Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'APV Completed' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'APV Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void apvCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'APV Completed' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackAPVCompleted();

        //Go to rendition properties page and verify 'APV Completed' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getAPVCompleted().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "APV Completed Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Topical Heading Completed' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Topical Heading Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void topicalHeadingCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Topical Heading Completed' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackTopicalHeadingCompleted();

        //Go to rendition properties page and verify 'Topical Heading Completed' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getTopicalHeadingCompleted().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Topic Heading Completed Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Images Completed' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Images Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void imagesCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Images Completed' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackImagesCompleted();

        //Go to rendition properties page and verify 'Images Completed' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getImagesCompleted().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Image Completed Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Tabular Received Hardcopy' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Tabular Received Hardcopy' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularReceivedHardcopyLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Tabular Received Hardcopy' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackTabularReceivedHardcopy();

        //Go to rendition properties page and verify 'Tabular Received Hardcopy' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getTabularReceivedHardcopy().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Tabular Received Hardcopy Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Tabular Completed' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Tabular Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Tabular Completed' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackTabularCompleted();

        //Go to rendition properties page and verify 'Tabular Completed' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getTabularCompleted().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Tabular Completed Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'APV Review Completed' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'APV Review Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void apvReviewCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'APV Review Completed' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackAPVReviewCompleted();

        //Go to rendition properties page and verify 'APV Review Completed' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getAPVReviewCompleted().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "APV Review Completed Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Published In Advanced Sheet' from the Track context menu.
     *           Next the test enters the Rendition Properties, opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Published In Advanced Sheet' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishedInAdvancedSheetDateLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click 'Published In Advanced Sheet' from the track context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().trackPublishedInAdvancedSheetDate();

        //Go to rendition properties page and verify 'Published In Advanced Sheet' date is correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        renditionPropertiesPage().openProposedApprovedTrackingInfoTab();
        boolean apvStartedDate = renditionPropertiesPage().getPublishedInAdvancedSheetDate().equals(currentDate);
        Assertions.assertTrue(apvStartedDate, "Published In Advanced Sheet Date is correct");

        renditionPropertiesPage().clickCancel();
    }

    @BeforeEach
    public void mockData()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
