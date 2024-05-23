package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PublishBillTrackWithAssociatedBillTextErrorTests extends TestService
{
    /**
     * STORY: NA <br>
     * SUMMARY: checks if a bill track with a bill text error syncs correctly.<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishBillTrackWithAssociatedBillTextError()
    {
        String contentSet = "Iowa (Development)";
        String contentType = "BILLTRACK";
        String docType = "H";
        String docNumber = "SB557";

        String lineageContentType = "BILL";

        String workflowType = "CwbSyncProcessing";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        sourceNavigateFiltersAndSortsPage().setFilterContentType(lineageContentType);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean billTextHasError = sourceNavigateGridPage().errorFlagIsPresent();
        sourceNavigateTabsPage().goToRenditionTab();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", "");
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(billTextHasError, "The latest bill text should have an error."),
            () -> Assertions.assertTrue(workflowFinished, String.format("The sync workflow %s should finish.", workflowID))
        );
    }
}