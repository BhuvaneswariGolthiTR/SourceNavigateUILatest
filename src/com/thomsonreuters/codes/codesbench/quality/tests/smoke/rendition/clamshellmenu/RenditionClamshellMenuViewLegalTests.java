package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionClamshellMenuViewLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    private void filterRendition(String dupl, String year, String status, String docNum)
    {
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate(dupl);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNum);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(status);
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks View Baselines window appears and Common Editor window appears when using clamshell <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void baselinesLegalTest()
    {
        // Log in and go to Source Navigate
        //Filter for the document created before: Validation: None, Multiple/Duplicate: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), year: 2012, Rendition Status: APV, Doc Number: 0020
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click View -> Baselines in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarView();
        boolean viewBaselinesWindowAppears = renditionTabViewClamshellPage().clickBaselines(true, false);
        Assertions.assertTrue(viewBaselinesWindowAppears, "View Baselines Window appears");

        //right click baseline number 0, select view baseline, and check Common Editor window appears
        viewBaselinesNavigatePage().clickNumberSort();
        viewBaselinesNavigatePage().rightClickOriginalBaseline();
        boolean editorWindowAppears = viewBaselinesContextMenu().clickViewBaseline(true, false);
        editorPage().closeDocumentWithNoChanges();
        Assertions.assertTrue(editorWindowAppears, "Editor window appears");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks proper message appears after clicking View -> Multiples and Duplicates in the clamshell <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void multiplesAndDuplicatesLegalTest()
    {
        //TODO Need a prep with multiple flag, probably will need to mock another rendition so that they are actually multiples

        // Log in and go to Source Navigate
        //Filter for the document created before: Validation: None, Multiple/Duplicate: Multiple, Deleted: Not Deleted,
        // Content Set: Iowa(Development), year: 2012, Rendition Status: PREP, Doc Number: 2092
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        filterRendition("Multiple", "2012", "PREP", "2092");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("LAW");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click View -> Multiples and Duplicates in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarView();
        boolean messageAppears = renditionTabViewClamshellPage().clickMultiplesAndDuplicates(true, false);
        Assertions.assertTrue(messageAppears, "'Displaying all results regardless of filters. Clear all filters to continue.' appears");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks XML Extract window appears when using clamshell and workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void xmlExtractLegalTest()
    {
        // Log in and go to Source Navigate
        // Filter for the document created before: Validation: None, Multiple/Duplicate: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), year: 2012, Rendition Status: APV, Doc Number: 0020
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click View -> XML Extract in clamshell for first rendition and check window appears
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarView();
        boolean xmlExtractWindowAppears = renditionTabViewClamshellPage().clickXmlExtract(true, false);
        Assertions.assertTrue(xmlExtractWindowAppears, "XML Extract Window appears");

        //Enter a test file name and click ok
        xmlExtractSourcePage().addFileName("TEST");
        xmlExtractSourcePage().clickOk();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();

        //check workflow finishes
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbRenditionXmlExtract", "", "Xml Extract", user().getUsername().toUpperCase());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowId != null && workflowCompleted, String.format("Rendition XML Extract workflow: %s completed", workflowId));
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Tabular Print Preview workflow finishes when using clamshell <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularPrintPreviewLegalTest()
    {
        // Log in and go to Source Navigate
        //Filter for the document created before: Validation: None, Multiple/Duplicate: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), year: 2012, Rendition Status: APV, Doc Number: 0020
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click View -> Tabular Print Preview in clamshell for first rendition and check window appears
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarView();
        boolean tabularPrintPreviewWindowAppears = renditionTabViewClamshellPage().clickTabularPrintPreview(true, false);
        Assertions.assertTrue(tabularPrintPreviewWindowAppears, "Tabular Print Preview Window appears");
        String workflowId = printPreviewPage().getWorkflowId();
        printPreviewPage().clickOK();
        Assertions.assertFalse(workflowId.isEmpty(), "Workflow ID is empty");

        //check workflow finishes
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
     * SUMMARY - Opens Delta Navigate (affecting same target) tab from clamshell and checks grid shows correct number of renditions <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltasAffectingSameTargetLegalTest()
    {
        //TODO same as RenditionContextMenuViewLegalTests for this test

        // Log in and go to Source Navigate
        //Filter for the document created before: Validation: None, Multiple/Duplicate: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), year: 2013, Rendition Status: PREP, Content Type: LAW, Doc Number: 340
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        filterRendition("None", "2013", "PREP", "340");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("LAW");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //get delta count in grid
        sourceNavigateGridPage().clickFirstRendition();
        int deltaCountFromRendition = Integer.parseInt(sourceNavigateGridPage().getDeltaCountInRenditionGrid());

        //click View -> Deltas Affecting Same Target in clamshell for first rendition, check deltas in grid match delta count
        clamshellPage().openSideBarView();
        boolean deltaNavigateTabAppears = renditionTabViewClamshellPage().clickDeltasAffectingSameTarget(true);
        Assertions.assertTrue(deltaNavigateTabAppears, "Delta Navigate (affecting same target) tab appears");
        int deltaCountFromDelta = sourceNavigateGridPage().getNumberOfDeltas();
        Assertions.assertEquals(deltaCountFromRendition, deltaCountFromDelta, "Number of deltas in Delta Navigate tab match delta count from grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Rendition XML Editor window appears when using clamshell and verifies text cannot be added <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void renditionXmlLegalTest()
    {
        String test = "<!-- This is test abracadabra -->";

        // Log in and go to Source Navigate
        //Filter for the document created before: Validation: None, Multiple/Duplicate: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), year: 2012, Rendition Status: APV, Doc Number: 0020
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click View -> Rendition XML in clamshell for first rendition and check window appears
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarView();
        boolean xmlExtractWindowAppears = renditionTabViewClamshellPage().clickRenditionXml(true, false);
        Assertions.assertTrue(xmlExtractWindowAppears, "XML Editor Window appears");

        //Send text to editor, check if it appears, and close editor
        sourceRawXmlEditorPage().sendTextToXmlEditor(test);
        boolean testTextAppearsInEditor = sourceRawXmlEditorPage().isGivenTextInEditor(test);
        sourceRawXmlEditorPage().clickClose();
        Assertions.assertFalse(testTextAppearsInEditor, "Inserted text appears in XML Editor");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Rendition Print Preview workflow finishes when using clamshell <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void printPreviewLegalTest()
    {
        // Log in and go to Source Navigate
        //Filter for the document created before: Validation: None, Multiple/Duplicate: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), year: 2012, Rendition Status: APV, Doc Number: 0020
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click View -> Rendition Print Preview in clamshell for first rendition and check window appears
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarView();
        boolean renditionPrintPreviewWindowAppears = renditionTabViewClamshellPage().clickRenditionPrintPreview(true, false);
        Assertions.assertTrue(renditionPrintPreviewWindowAppears, "Rendition Print Preview Window appears");
        String workflowId = printPreviewPage().getWorkflowId();
        printPreviewPage().clickOK();
        Assertions.assertFalse(workflowId.isEmpty(), "Workflow ID is empty");

        //check workflow finishes
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("Workflow: %s finished successfully", workflowId));
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
