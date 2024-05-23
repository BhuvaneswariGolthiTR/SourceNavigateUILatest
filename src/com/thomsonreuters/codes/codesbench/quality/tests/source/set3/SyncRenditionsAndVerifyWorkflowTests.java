package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

public class SyncRenditionsAndVerifyWorkflowTests extends TestService
{
    public void filter(String contentSet, String contentType)
    {
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFooterToolsPage().refreshTheGrid();
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for a BILL content type document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void billSyncTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: Alabama (Development), Content Type: BILL, Multiple/Duplicate: None, Filter Validation: None
        filter("Alabama (Development)", "BILL");

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for a VETOMESSAGE content type document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void vetoMessageSyncTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: California (Development), Content Type: VETOMESSAGE, Multiple/Duplicate: None, Filter Validation: None
        filter("California (Development)", "VETOMESSAGE");

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for a COMMITTEEANALYSIS content type document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void committeeAnalysisSyncTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: California (Development), Content Type: COMMITTEEANALYSIS, Multiple/Duplicate: None, Filter Validation: None
        filter("California (Development)", "COMMITTEEANALYSIS");

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for a COMMITTEEREPORT content type document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void committeeReportSyncTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: Iowa (Development), Content Type: COMMITTEEREPORT, Multiple/Duplicate: None, Filter Validation: None
        filter("Iowa (Development)", "COMMITTEEREPORT");

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for a EXECUTIVEORDER content type document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void executiveOrderSyncTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: Alabama (Development), Content Type: EXECUTIVEORDER, Multiple/Duplicate: None, Filter Validation: None
        filter("Alabama (Development)", "EXECUTIVEORDER");

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for a FISCALNOTE content type document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void fiscalNoteSyncTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: Alabama (Development), Content Type: FISCALNOTE, Multiple/Duplicate: None, Filter Validation: None
        filter("Alabama (Development)", "FISCALNOTE");

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for a BILLTRACK content type document<br>
     * USER - Legal <br>
     */

    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void billTrackSyncTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: USCA(Development), Content Type: BILLTRACK, DocNumber: 200, Multiple/Duplicate: None, Filter Validation: None
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILLTRACK");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("200");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for an AMENDMENTTEXT content type document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void amendmentTextSyncTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: Iowa (Development), Content Type: AMENDMENTTEXT, Multiple/Duplicate: None, Filter Validation: None
        filter("Iowa (Development)", "AMENDMENTTEXT");

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test that a user can sync and verify the workflow finishes for an APVRS Rendition Status document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void syncRenditionAndVerifyWorkflowRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: USCA(Development), Rendition Status: APVRS, Filter Year: 2015
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowInTimeFrameAndFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertTrue(workflowInTimeFrameAndFinished, String.format("The workflow %s was created at the time that was specified.", workflowId));
    }

}
