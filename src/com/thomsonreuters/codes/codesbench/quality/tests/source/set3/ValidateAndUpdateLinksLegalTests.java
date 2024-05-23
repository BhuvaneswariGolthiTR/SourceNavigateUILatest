package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class ValidateAndUpdateLinksLegalTests extends TestService
{
    public void filter()
    {
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2019");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFiltersAndSortsPage().setFilterDocType("H");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("96");
        sourceNavigateFooterToolsPage().refreshTheGrid();
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Verifies that a validate and update links workflow starts and completes on a BILL document <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validateAndUpdateLinksBillTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: Iowa (Development), Filter Year: 2019, Filter Doc Type: H, Filter Content Type: BILL, Filter Doc Number: 96
        filter();

        //Right-click on a BILL rendition and choose Validate and Update Links
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().rightClickFirstRendition();
        String workflowID = renditionContextMenu().validateValidateAndUpdateLinks();
        Assertions.assertFalse(workflowID.isEmpty(), "Workflow ID is empty or first alert did not appear/was not accepted");

        toolsMenu().goToWorkflowReportingSystem();

        workflowSearchPage().setWorkflowID(workflowID);
        workflowSearchPage().clickFilterButton();
        boolean workflowCompleted = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowCompleted, String.format("The workflow %s did finish", workflowID));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Verifies that you cannot validate and update links on a BILLTRACK document<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validateAndUpdateLinksBilltrackTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter Content Set: Iowa (Development), Filter Year: 2019, Filter Doc Type: H, Filter Content Type: BILL, Filter Doc Number: 96
        filter();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILLTRACK");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean billTrackNotValid = renditionContextMenu().validateAndUpdateLinksFirstRenditionBillTrack();

        Assertions.assertTrue(billTrackNotValid, "We should not be able to validate & update links on a Billtrack document");
    }

}
