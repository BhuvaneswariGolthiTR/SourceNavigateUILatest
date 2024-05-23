package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
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

public class RenditionClamshellMenuReportLegalTests extends TestService
{
    SourceDatapodObject apv;
    SourceDatapodObject prep;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Add Index Report on first rendition using clamshell and checks workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addIndexReportLegalTest()
    {
        renditionUuid = createAPVDocReturnRenditionUUID();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Add Index Report through clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarReport();
        String workflowId = renditionTabReportClamshellPage().clickAddIndexReport(true);
        Assertions.assertFalse(workflowId.isEmpty(),"Workflow ID is empty");

        //verify workflow finishes
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("Add Index Report workflow: %s finished successfully", workflowId));
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Repeal Index Report on first rendition using clamshell and checks workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void repealReportLegalTest()
    {
        renditionUuid = createAPVDocReturnRenditionUUID();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Repeal Index Report through clamshell for first rendition. Click default checkbox
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarReport();
        String workflowId = renditionTabReportClamshellPage().clickRepealIndexReportSingleSelect(true, false);
        Assertions.assertNotEquals("", workflowId, "Index Report workflow was empty");

        //get workflow id from alert and verify workflow finishes
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("Workflow: %s finished successfully", workflowId));
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Combined Index Report on first two renditions using clamshell and checks workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void combinedReportLegalTest()
    {
        renditionUuid = createAPVDocReturnRenditionUUID();
        SourceDatapodObject newApv = SourceDataMockingNew.Iowa.Small.APV.insert();
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2020");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterLegislationType("Bill");
        sourceNavigateFiltersAndSortsPage().setFilterDocType("HF");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Combined Index Report through clamshell for first two renditions
        sourceNavigateGridPage().clickFirstXRenditions(2);
        clamshellPage().openSideBarReport();
        boolean indexReportWindowAppears = renditionTabReportClamshellPage().clickCombinedIndexReport(true, false);
        Assertions.assertTrue(indexReportWindowAppears, "Index Report window appears");
        indexReportSortOrderPage().clickDefaultSortOrder();
        String workflowId = indexReportSortOrderPage().clickOk();

        //get workflow id from alert and verify workflow finishes
        Assertions.assertFalse(workflowId.isEmpty(),"Workflow ID is empty");
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("Repeal Index Report workflow: %s finished successfully", workflowId));

        newApv.delete();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Prep Report on first rendition using clamshell menu and checks proper window opens <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void prepReportLegalTest()
    {
        // Log in and go to Source Navigate
        renditionUuid = createPrepDocReturnRenditionUUID();
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Prep Report through clamshell for first rendition and check proper window opens
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarReport();
        boolean reportWindowAppears = renditionTabReportClamshellPage().clickPrepReport(true, false);
        Assertions.assertTrue(reportWindowAppears, "Report window appears");
        boolean reportTitleAppears = reportPage().titleIsWorksheetReport();
        reportPage().clickClose();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(reportWindowAppears, "Report Window appears"),
                () -> Assertions.assertTrue(reportTitleAppears, "Worksheet Report title appears")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Unused Report on first rendition using clamshell menu and checks proper window opens and closes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void unusedReportLegalTest()
    {
        // Log in and go to Source Navigate
        renditionUuid = createPrepDocReturnRenditionUUID();
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Unused Report through clamshell for first rendition and check proper window opens
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarReport();
        boolean reportWindowAppears = renditionTabReportClamshellPage().clickUnusedReport(true, false);
        Assertions.assertTrue(reportWindowAppears, "Report window appears");
        boolean reportTitleAppears = reportPage().titleIsUnusedReport();
        reportPage().clickClose();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(reportWindowAppears, "Report Window appears"),
                () -> Assertions.assertTrue(reportTitleAppears, "Unused Report title appears")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Integration Summary Report on first rendition using clamshell menu and checks proper window opens and closes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationSummaryReportLegalTest()
    {
        // Log in and go to Source Navigate
        renditionUuid = createPrepDocReturnRenditionUUID();
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Integration Summary Report through clamshell for first rendition and check proper window opens
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarReport();
        boolean reportWindowAppears = renditionTabReportClamshellPage().clickIntegrationSummary(true, false);
        Assertions.assertTrue(reportWindowAppears, "Report window appears");
        boolean reportTitleAppears = reportPage().titleIsIntegrationReport();
        reportPage().clickClose();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(reportWindowAppears, "Report Window appears"),
                () -> Assertions.assertTrue(reportTitleAppears, "Integration Summary Report title appears")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Integration Results Report on first rendition using clamshell menu and checks proper window opens and closes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationResultsLegalTest()
    {
        // Log in and go to Source Navigate
        renditionUuid = createPrepDocReturnRenditionUUID();
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Integration Results through clamshell for first rendition and check proper window opens and closes
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarReport();
        boolean reportWindowAppears = renditionTabReportClamshellPage().clickIntegrationResults(true, false);
        Assertions.assertTrue(reportWindowAppears, "Integration Results window appears");
        integrationResultsPage().clickClose();
        sourcePage().switchToSourceNavigatePage();
        boolean resultsWindowCloses = integrationResultsPage().checkIntegrationResultsWindowIsClosed();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(reportWindowAppears, "Integration Results window appears"),
            () -> Assertions.assertTrue(resultsWindowCloses, "Integration Results window closes")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Audits on first rendition using clamshell menu and checks proper window opens and closes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditsLegalTest()
    {
        // Log in and go to Source Navigate
        renditionUuid = createPrepDocReturnRenditionUUID();
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Audits through clamshell for first rendition and check proper window opens and closes
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarReport();
        boolean auditsWindowAppears = renditionTabReportClamshellPage().clickAudits(true, false);
        boolean auditsWindowCloses = auditBySourcePage().closeAuditsBySourceWindow();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(auditsWindowAppears, "Audits By Source window appears"),
            () -> Assertions.assertTrue(auditsWindowCloses, "Audits By Source window closes")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Stage Check on first rendition using clamshell menu and checks the search function works  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void stageCheckReportLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //run Report -> Stage Check through clamshel and check search function works
        clamshellPage().openSideBarReport();
        boolean stageCheckReportWindowAppears = renditionTabReportClamshellPage().clickStageCheckReport(true, false);
        Assertions.assertTrue(stageCheckReportWindowAppears, "Stage Check Report window appears");
        stageCheckReportsPage().selectYearDropdown(DateAndTimeUtils.getCurrentYearyyyy());
        stageCheckReportsPage().selectLegislationDropdown("Prefiled");
        if (!stageCheckReportsPage().addContentSet("Iowa (Development)"))
        {
            AutoITUtils.verifyAlertTextAndAccept(true, "You must select at least one content set.");
        }
        stageCheckReportsPage().clickSearch();
        stageCheckReportsPage().waitForPageLoaded();
        stageCheckReportsPage().breakOutOfFrame();
        stageCheckReportsPage().enterGridFrame();
        boolean resultsPopulate = stageCheckReportsPage().searchResultsPopulate();
        Assertions.assertTrue(resultsPopulate, "Grid appears and populates with search results");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Index Report on first rendition using clamshell menu, run a report, and verify workflow finishes  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void indexReportLegalTest()
    {
        // Log in and go to Source Navigate
        renditionUuid = createPrepDocReturnRenditionUUID();
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //run Report -> Index Report through clamshell for first rendition, fill in settings and click next
        clamshellPage().openSideBarReport();
        boolean indexReportsWindowAppears = renditionTabReportClamshellPage().clickIndexReport(true, false);
        Assertions.assertTrue(indexReportsWindowAppears, "Index Report window appears");
        indexReportPage().setReportType("COMBINED");
        indexReportPage().setSortOrderType("default");
        indexReportPage().setYear(Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 2 + "");
        indexReportPage().setContentSet("Iowa (Development)");
        indexReportPage().setSession("2RG");
        indexReportPage().clickNext();

        //get number of renditions available to move, move one over, and run report
        int numberOfRenditionsToMove = indexReportPage().getNumberOfRenditionsToMove();
        Assertions.assertTrue(numberOfRenditionsToMove > 0, "Renditions To Move options are available");
        indexReportPage().selectRenditionByIndex(0);
        indexReportPage().moveRenditionToReport();
        indexReportPage().clickRunReport();

        //check workflow completed
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbIndexReport", "", "IndexReport", user().getUsername().toUpperCase());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowId != null && workflowCompleted, String.format("Index Report workflow: %s completed", workflowId));
    }

    private String createAPVDocReturnRenditionUUID()
    {
        apv = SourceDataMockingNew.Iowa.Small.APV.insert();
        String renditionUuid = apv.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);

        return renditionUuid;
    }

    private String createPrepDocReturnRenditionUUID()
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
