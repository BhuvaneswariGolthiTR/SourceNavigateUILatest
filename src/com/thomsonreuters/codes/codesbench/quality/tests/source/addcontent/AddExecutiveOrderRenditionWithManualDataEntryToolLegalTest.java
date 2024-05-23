package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddExecutiveOrderRenditionWithManualDataEntryToolLegalTest extends TestService
{
    String renditionUuid;
    String username;
    /**
     * STORY:  na <br>
     * SUMMARY: Test to verify the user can add an Executive Order document with the manual data entry tool in source navigate <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addExecutiveOrderRenditionWithManualDataEntryToolLegalTest()
    {
        String deleted = "Not Deleted";
        String sourceContentSet = "Alabama (Development)";
        String year = "2016";
        String session = "1RG";
        String contentType = "EXECUTIVEORDER";
        String docType = "EO";
        String docNumber = "1";
        String sourceFilterRenditionTitle = "TITLE: MECKLENBURG COUNTY";

        String jurisdiction = "Alabama";
        String chamber = "LEGISLATURE";
        String legislationType = "EXECUTIVE_ORDER";
        String addContentDocumentTitle = "MECKLENBURG COUNTY";
        String sponsors = "Governor";
        String renditionStatus = "GN";
        String sourceVersionDate = "08/17/2013";
        String selectedFile = "EO102.pdf";
        String reason = "MANUAL_ACQUISITION_REQUIRED";

        String workflowType = "LcprExecutiveOrderPublish";
        String workflowContentSet = "LCPR.AL";
        String description = "LCPR Executive Order";
        username = user().getUsername().toUpperCase();

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppears = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppears, "The source navigate window did not appear");

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(sourceFilterRenditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            boolean deleteConfirmationWindowAppeared = renditionContextMenu().modifyDeleteRendition();
            Assertions.assertTrue(deleteConfirmationWindowAppeared, "The delete confirmation window did not appear.");
        }
        clamshellPage().openSideBarCreate();
        boolean addContentPageAppeared = renditionTabCreateClamshellPage().clickAddContent(true, false);
        Assertions.assertTrue(addContentPageAppeared);

        manualDataEntryPage().setJurisdictionDropdown(jurisdiction);
        manualDataEntryPage().setYear(year);
        manualDataEntryPage().setSessionDropdown(session);
        manualDataEntryPage().setChamberDropdown(chamber);
        manualDataEntryPage().setContentTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTypeDropdown(docType);
        manualDataEntryPage().setDocumentNumber(docNumber);
        manualDataEntryPage().setLegislationTypeDropdown(legislationType);
        manualDataEntryPage().setDocumentTitle(addContentDocumentTitle);
        manualDataEntryPage().setSponsors(sponsors);
        manualDataEntryPage().checkManualContentDataEntryCheckBox();

        manualDataEntryPage().setRenditionStatusDropdown(renditionStatus);
        manualDataEntryPage().setSourceVersionDate(sourceVersionDate);
        manualDataEntryPage().setSelectedFile(selectedFile);
        manualDataEntryPage().setReasonDropdown(reason);
        manualDataEntryPage().clickAddContent();

        sourcePage().switchToSourceNavigatePage();
        boolean workflowReportingWindowAppeared =  toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowReportingWindowAppeared, "The workflow reporting window did not appear");
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowContentSet, description, username);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        boolean newRenditionIsCreated = sourceNavigateGridPage().firstRenditionExists();
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(workflowCompleted, String.format("The workflow %s wasn't completed", workflowID)),
            () -> Assertions.assertTrue(newRenditionIsCreated, "The new rendition wasn't found in source navigate")
        );
    }

    @AfterEach
    public void deleteRendition()
    {
        sourceNavigateGridPage().setRenditionDeletedWithDatabase(renditionUuid, username);
    }
}
