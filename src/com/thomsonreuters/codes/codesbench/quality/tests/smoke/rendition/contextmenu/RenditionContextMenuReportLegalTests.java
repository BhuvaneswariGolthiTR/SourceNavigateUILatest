package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionContextMenuReportLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    private void filterRendition(String docNumber)
    {
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2013");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Add Index Report on first rendition using context and checks workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addIndexReportLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //right click first rendition and select Report -> Add Index Report
        sourceNavigateGridPage().rightClickFirstRendition();
        String workflowId = renditionContextMenu().reportAddIndexReport();
        Assertions.assertFalse(workflowId.isEmpty(),"Workflow ID is empty");

        //verify workflow finishes
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("Add Index Report Workflow: %s finished successfully", workflowId));
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Compare and Markup Report on first rendition using context and checks workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void compareAndMarkupReportLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //right click first rendition and select Report -> Run Compare and Markup Report
        sourceNavigateGridPage().rightClickFirstRendition();
        String workflowId = renditionContextMenu().reportCompareAndMarkupReport();
        Assertions.assertFalse(workflowId.isEmpty(),"Workflow ID is empty");

        //verify workflow finishes
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("Compare and Markup Report Workflow: %s finished successfully", workflowId));
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Repeal Index Report on first rendition using context and checks workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void repealIndexReportLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Report -> Repeal Index Report through context for first rendition. Click default checkbox
        sourceNavigateGridPage().rightClickFirstRendition();
        String workflowId = renditionContextMenu().reportRepealIndexReportSingleSelect();
        Assertions.assertNotEquals("", workflowId, "Index Report workflow was empty");

        //get workflow id from alert and verify workflow finishes
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("Repeal Index Report Workflow: %s finished successfully", workflowId));
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Combined Index Report on first two renditions using context and checks workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void combinedReportLegalTest()
    {
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

        //run Report -> Combined Index Report through context for first two renditions
        sourceNavigateGridPage().clickFirstXRenditions(2);
        sourceNavigateGridPage().onlyRightClickFirstItem();
        boolean indexReportWindowAppears = renditionContextMenu().reportCombinedIndexReport();
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
        Assertions.assertTrue(workflowFinished, String.format("Repeal Index Report Workflow: %s finished successfully", workflowId));

        newApv.delete();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Compare Report on first rendition using context and checks workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void compareReportLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //right click first rendition and select Report -> Run Compare Report
        sourceNavigateGridPage().rightClickFirstRendition();
        String workflowId = renditionContextMenu().reportCompareReport();
        Assertions.assertFalse(workflowId.isEmpty(),"Workflow ID is empty");

        //verify workflow finishes
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("Compare And Markup Workflow: %s finished successfully", workflowId));
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
