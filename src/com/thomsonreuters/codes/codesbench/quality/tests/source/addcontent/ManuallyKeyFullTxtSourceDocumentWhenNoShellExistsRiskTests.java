package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

public class ManuallyKeyFullTxtSourceDocumentWhenNoShellExistsRiskTests extends TestService
{
    String username;
    String renditionUuid;

    /**
     * STORY/BUG - JETS-2427 <br>
     * SUMMARY - Verifies that a full text source document is created and successfully added to the source navigate grid
     *           Also makes sure that the document has a westlaw load of Quick  <br>
     * USER - Risk <br>
     */

    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void keyFullTxtSourceDocumentWhenNoShellExists()
    {
        username = user().getUsername().toUpperCase();
        String renditionTitle = "Test doc " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the rendition and delete it
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation("Federal Reserve");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2017");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("PUBNOT");
        sourceNavigateFiltersAndSortsPage().setFilterDocType("EN");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("851");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().deleteFirstRendition();

        //Create new rendition with the following attributes
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("USCA(Development)");
        addShellPage().setDropdownDocumentType("Election Notice");
        addShellPage().setTextBoxOrganization("Federal Reserve");
        addShellPage().setTextBoxDocumentNumber("851");
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear("2017");
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile("ex_valid_3.txt");
        addShellPage().clickSubmit();

        sourcePage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();

        //Verify that the workflow completed
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprRiskSourceQuickKeyed", "LCPR.US", "LCPR Risk Source");
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);

        //Refresh the grid and get the count of renditions and verify that the rendition has a westload of Quick
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        int renditionCount = sourceNavigateGridPage().getNumberOfRenditions();
        boolean quickWestLoad = sourceNavigateGridPage().doesElementExist(SourceNavigateGridPageElements.WESTLOAD_QUICK);

        Assertions.assertAll
        (
            () ->Assertions.assertTrue(workflowCompleted, String.format("Full text source document workflow %s didn't finish", workflowID)),
            () ->Assertions.assertTrue(renditionCount >= 1, "Full text source document was not added to the source navigate grid"),
            () ->Assertions.assertTrue(quickWestLoad, "The full txt document does not have a westlaw load of Quick")
        );
    }
    @AfterEach
    public void deleteRendition()
    {
        sourceNavigateGridPage().setRenditionDeletedWithDatabase(renditionUuid, username);
    }

}
