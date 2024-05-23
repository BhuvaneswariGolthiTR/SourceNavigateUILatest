package com.thomsonreuters.codes.codesbench.quality.tests.smoke.section.clamshellmenu;

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

public class SectionClamshellMenuTrackLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Audit Requested' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Audit Requested' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditRequestedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Audit Requested from Track clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAuditRequested(true, false);

        //Open Section Properties and verify Audit Requested Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean auditRequestedDateIsToday = sectionPropertiesPage().getAuditRequestedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(auditRequestedDateIsToday, "Audit Requested Date is today's date");
    }


    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Audit Review Started' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Audit Review Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditReviewStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Audit Review Started from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAuditReviewStarted(true, false);

        //Open Section Properties and verify Audit Review Started Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean auditStartedDateIsToday = sectionPropertiesPage().getAuditReviewStartedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(auditStartedDateIsToday, "Audit Started Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Audit Review Completed' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Audit Review Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditReviewCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Audit Review Completed from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAuditReviewCompleted(true, false);

        //Open Section Properties and verify Audit Review Completed Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean auditCompletedDateIsToday = sectionPropertiesPage().getAuditReviewCompletedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(auditCompletedDateIsToday, "Audit Completed Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'PREP Started' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'PREP Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void prepStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select PREP Started from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickPrepStarted(true, false);

        //Open Section Properties and verify PREP Started Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean prepStartedDateIsToday = sectionPropertiesPage().getPREPStartedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(prepStartedDateIsToday, "PREP Started Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'PREP Completed' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'PREP Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void prepCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select PREP Completed from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickPrepCompleted(true, false);

        //Open Section Properties and verify PREP Completed Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean prepCompletedDateIsToday = sectionPropertiesPage().getPREPCompletedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(prepCompletedDateIsToday, "PREP Completed Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Ready for Integration' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Ready for Integration' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void readyForIntegrationLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Ready For Integration from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickReadyForIntegration(true, false);

        //Open Section Properties and verify Ready For Integration Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean readyForIntegrationDateIsToday = sectionPropertiesPage().getReadyForIntegrationDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(readyForIntegrationDateIsToday, "Ready For Integration Date is today's date");
    }

    /**
     * STORY/BUG - HALCYONST-288 <br>
     * SUMMARY - For a Section, the test selects 'Audit Corrections To Be Done By' from the Track clamshell menu.
     *           The 'Audit Corrections' pop up appears, an Auditor is selected and saved.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           The test then verifies that the correct Auditor was set.
     *           Finally, the Auditor is cleared and verified to have been cleared<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditCorrectionsToBeDoneLegalTest()
    {
        String auditor = "EAGAN";

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Audit Correction To Be Done By from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAuditCorrectionsToBeDoneBy(true, false);

        //Set Auditor and save
        auditCorrectionsPage().selectCorrectionsAuditor(auditor);
        auditCorrectionsPage().clickSave();

        //Open Section Properties and verify Auditor was set
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().waitForPageLoaded();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean correctionsAuditor = sectionPropertiesPage().getCorrectionsAuditor().equals(auditor);
        sectionPropertiesPage().clickCancel();

        //Click first section and select Audit Correction To Be Done By from clamshell menu
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAuditCorrectionsToBeDoneBy(true, false);

        //Clear Auditor and save
        auditCorrectionsPage().selectCorrectionsAuditor("");
        auditCorrectionsPage().clickSave();

        //Open Section Properties and verify Auditor was cleared
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().waitForPageLoaded();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean correctionsAuditorCleared = sectionPropertiesPage().getCorrectionsAuditor().equals("");
        sectionPropertiesPage().clickCancel();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(correctionsAuditor, "Corrections Auditor was set"),
            () -> Assertions.assertTrue(correctionsAuditorCleared, "Corrections Auditor was cleared")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Integration Started' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Integration Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationStartedLegalTest()
     {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

         sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
         sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Integration Started from clamshell menu
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickIntegrationStarted(true, false);

        //Open Section Properties and verify Integration Started Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean integrationStartedDateIsToday = sectionPropertiesPage().getIntegrationStartedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(integrationStartedDateIsToday, "Integration Start Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Integration Completed' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Integration Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Integration Completed from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickIntegrationCompleted(true, false);

        //Open Section Properties and verify Integration Completed Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean integrationCompletedDateIsToday = sectionPropertiesPage().getIntegrationCompletedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(integrationCompletedDateIsToday, "Integration Completed Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Attorney Work Started' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Attorney Work Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void attorneyWorkStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Attorney Work Started from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAttorneyWorkStarted(true, false);

        //Open Section Properties and verify Attorney Work Started Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean attorneyWorkStartedDateIsToday = sectionPropertiesPage().getAttorneyWorkStartedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(attorneyWorkStartedDateIsToday, "Attorney Work Started Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Attorney Work Completed' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Attorney Work Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void attorneyWorkCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Attorney Work Completed from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAttorneyWorkCompleted(true, false);

        //Open Section Properties and verify Attorney Work Completed Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean attorneyWorkCompletedDateIsToday = sectionPropertiesPage().getAttorneyWorkCompletedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(attorneyWorkCompletedDateIsToday, "Attorney Work Completed Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Versioning Work Started' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Versioning Work Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void versioningWorkStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Versioning Work Started from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickVersioningWorkStarted(true, false);

        //Open Section Properties and verify Versioning Work Started Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean versioningWorkStartedDateIsToday = sectionPropertiesPage().getVersioningWorkStartedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(versioningWorkStartedDateIsToday, "Versioning Work Started Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Versioning Work Completed' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Versioning Work Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void versioningWorkCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Versioning Work Completed from clamshell menu
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickVersioningWorkCompleted(true, false);

        //Open Section Properties and verify Versioning Work Completed Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean versioningWorkCompletedDateIsToday = sectionPropertiesPage().getVersioningWorkCompletedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(versioningWorkCompletedDateIsToday, "Versioning Work Completed Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Audit Corrections Started' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Audit Corrections Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditCorrectionsStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Audit Corrections Started from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAuditCorrectionStarted(true, false);

        //Open Section Properties and verify Audit Corrections Started Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean auditCorrectionsStartedDateIsToday = sectionPropertiesPage().getAuditCorrectionsStartedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(auditCorrectionsStartedDateIsToday, "Audit Corrections Started Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Audit Corrections Completed' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Audit Corrections Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditCorrectionsCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Audit Corrections Completed from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickAuditCorrectionsCompleted(true, false);

        //Open Section Properties and verify Audit Corrections Completed Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean auditCorrectionsCompletedDateIsToday = sectionPropertiesPage().getAuditCorrectionsCompletedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(auditCorrectionsCompletedDateIsToday, "Audit Corrections Completed Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Integration 2 Started' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Integration 2 Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integration2StartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Integration 2 Started from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickIntegration2Started(true, false);

        //Open Section Properties and verify Integration 2 Started Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean integration2StartedDateIsToday = sectionPropertiesPage().getIntegration2StartedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(integration2StartedDateIsToday, "Integration 2 Started Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Integration 2 Completed' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Integration 2 Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integration2CompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Integration 2 Completed from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickIntegration2Completed(true, false);

        //Open Section Properties and verify Integration 2 Completed Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean integration2CompletedDateIsToday = sectionPropertiesPage().getIntegration2CompletedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(integration2CompletedDateIsToday, "Integration 2 Completed Date is today's date");
    }

    /**
     * STORY/BUG - HALCYONST-283 <br>
     * SUMMARY - For a Section, the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           The test then unchecks the 'Integration Query Started' checkbox.
     *           The closes Section Properties and selects 'Integration Query Started' from the Track clamshell menu.
     *           Next the test returns to Section Properties, and reopens the PREP Tracking Information tab.
     *           The test then verifies that the 'Integration Query Started' checkbox is checked and the 'Integration Query Started' date is today's date.<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationQueryStartedLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open Section Properties and uncheck Integration Query Started checkbox and save
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();
        sectionPropertiesPage().unCheckIntegrationQueryStartedCheckbox();
        sectionPropertiesPage().clickSave();

        //Select Integration Query Started from clamshell menu
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickIntegrationQueryStarted(true, false);

        //Open Section Properties and verify Integration Query Started checkbox is checked and Query Started Owner is user
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean isIntegrationQueryStartedCheckboxChecked = sectionPropertiesPage().isIntegrationQueryStartedCheckboxChecked();

        String username = String.format("%s %s", user().getFirstname(), user().getLastname());
        boolean isIntegrationQueryStartedOwnerCorrect = sectionPropertiesPage().isIntegrationQueryStartedOwner(username);
        sectionPropertiesPage().clickCancel();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isIntegrationQueryStartedCheckboxChecked, "Integration Query Started checkbox was checked"),
            () -> Assertions.assertTrue(isIntegrationQueryStartedOwnerCorrect, "Integration Query Started owner was the correct user")
        );
    }

    /**
     * STORY/BUG - HALCYONST-284 N/A <br>
     * SUMMARY - For a Section, the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           The test then unchecks the 'Integration Query Completed' checkbox.
     *           The closes Section Properties and selects 'Integration Query Completed' from the Track clamshell menu.
     *           Next the test returns to Section Properties, and reopens the PREP Tracking Information tab.
     *           The test then verifies that the 'Integration Query Completed' checkbox is checked and the 'Integration Query Completed' date is today's date.<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationQueryCompleteLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open Section Properties and uncheck Integration Query Completed checkbox and save
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();
        sectionPropertiesPage().unCheckIntegrationQueryCompletedCheckbox();
        sectionPropertiesPage().clickSave();

        //Select Integration Query Started from clamshell menu
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickIntegrationQueryCompleted(true, false);

        //Open Section Properties and verify Integration Query Completed checkbox is checked and Query Completed Owner is user
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean isIntegrationQueryCompletedCheckboxChecked = sectionPropertiesPage().isIntegrationQueryCompletedCheckboxChecked();
        String username = String.format("%s %s", user().getFirstname(), user().getLastname());

        boolean isIntegrationQueryCompletedOwnerCorrect = sectionPropertiesPage().isIntegrationQueryCompletedOwner(username);
        sectionPropertiesPage().clickCancel();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isIntegrationQueryCompletedCheckboxChecked, "Integration Query Completed checkbox was checked"),
            () -> Assertions.assertTrue(isIntegrationQueryCompletedOwnerCorrect, "Integration Query Completed owner was the correct user")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Corrections Audit Started' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Corrections Audit Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void correctionsAuditStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Corrections Audit Started from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickCorrectionsAuditStarted(true, false);

        //Open Section Properties and verify Corrections Audit Started Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean correctionAuditStartedDateIsToday = sectionPropertiesPage().getCorrectionsAuditStartedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(correctionAuditStartedDateIsToday, "Corrections Audit Started Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Corrections Audit Completed' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Corrections Audit Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void correctionsAuditCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Corrections Audit Completed from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickCorrectionsAuditCompleted(true, false);

        //Open Section Properties and verify Corrections Audit Completed Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean correctionAuditCompletedDateIsToday = sectionPropertiesPage().getCorrectionsAuditCompletedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(correctionAuditCompletedDateIsToday, "Corrections Audit Completed Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Corrections Audit Requested' from the Track clamshell menu.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           Finally, the test verifies the 'Corrections Audit Requested' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void correctionsAuditRequestedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Corrections Audit Requested from clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickCorrectionsAuditRequested(true, false);

        //Open Section Properties and verify Corrections Audit Requested Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean correctionAuditRequestedDateIsToday = sectionPropertiesPage().getCorrectionsAuditRequestedDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(correctionAuditRequestedDateIsToday, "Corrections Audit Requested Date is today's date");
    }


    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Clean' from the Track clamshell menu.
     *           The 'Clean' pop up appears, a Mark as Clean date is set to today's date and saved.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           The test then verifies that the Clean date was set to today's date.<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cleanLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Clean from clamshell menu, set Mark As Clean Date to today
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickClean(true, false);
        cleanPage().setMarkAsCleanDate(currentDate);
        cleanPage().clickSave();

        //Open Section Properties and verify Clean Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean cleanDateIsToday = sectionPropertiesPage().getCleanDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(cleanDateIsToday, "Clean Date is today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Released to Westlaw' from the Track clamshell menu.
     *           The 'Released to Westlaw' pop up appears, a Released to Westlaw date is set to today's date and saved.
     *           Next the test enters the Section Properties, opens the PREP Tracking Information tab.
     *           The test then verifies that the Released to Westlaw date was set to today's date.<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void releasedToWestlawLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and select Released To Westlaw from clamshell menu, set Released To Westlaw Date to today
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarTrack();
        sectionTabTrackClamshellPage().clickReleasedToWestlaw(true, false);
        releasedToWestlawPage().setReleasedToWestlawDate(currentDate);
        releasedToWestlawPage().clickSave();

        //Open Section Properties and verify Released To Westlaw Date is today's date
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openTrackingInfoTab();

        boolean releasedToWestLawDateIsToday = sectionPropertiesPage().getReleasedToWestLawDate().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(releasedToWestLawDateIsToday, "Released To Westlaw Date is today's date");
    }

    @BeforeEach
    public void mockData()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.PREP.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(datapodObject != null)
        {
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
