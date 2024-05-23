package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddBillRenditionWithManualDataEntryToolTest extends TestService
{
    String renditionUuid;
    String username;
    /**
     * STORY: na <br>
     * SUMMARY: Test to verify the user can add a Bill document with the manual data entry tool in source navigate <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addBillRenditionWithManualDataEntryToolTest()
    {
        String deleted = "Not Deleted";
        String sourceFilterContentSet = "Iowa (Development)";
        String year = "2019";
        String session = "1RG";
        String docType = "H";
        String docNumber = "710";
        String contentType = "BILL";

        String addContentContentSet = "Iowa";
        String chamber = "HOUSE";
        String documentLegislationType = "DRAFT";
        String addContentDocumentTitle = "THIS Adding a Bill";
        String sponsors = "Gumby and Pokey";
        String documentRenditionStatus = "DFT";
        String documentSourceVersionDate = "05/31/2019";
        String documentSelectedFile = "EO102.pdf";
        String documentReason = "MANUAL_ACQUISITION_REQUIRED";

        String workflowType = "LcprBillTextSuperquick";
        String contentSet = "LCPR.IA";
        String description = "LCPR Bill Text";
        username = user().getUsername().toUpperCase();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared, "The source navigate window did not appear.");

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceFilterContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //If a rendition exists, delete it so the test may create the chosen rendition.
        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            boolean deleteConfirmationWindowAppeared = renditionContextMenu().modifyDeleteRendition();
            Assertions.assertTrue(deleteConfirmationWindowAppeared, "The delete confirmation window did not appear.");
        }

        clamshellPage().openSideBarCreate();
        boolean addContentPageWindowAppeared = renditionTabCreateClamshellPage().clickAddContent(true, false);
        Assertions.assertTrue(addContentPageWindowAppeared, "The add content page window did not appear.");

        manualDataEntryPage().setJurisdictionDropdown(addContentContentSet);
        manualDataEntryPage().setYear(year);
        manualDataEntryPage().setSessionDropdown(session);
        manualDataEntryPage().setChamberDropdown(chamber);
        manualDataEntryPage().setContentTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTypeDropdown(docType);
        manualDataEntryPage().setDocumentNumber(docNumber);
        manualDataEntryPage().setLegislationTypeDropdown(documentLegislationType);
        manualDataEntryPage().setDocumentTitle(addContentDocumentTitle);
        manualDataEntryPage().setSponsors(sponsors);
        manualDataEntryPage().checkManualContentDataEntryCheckBox();
        manualDataEntryPage().setRenditionStatusDropdown(documentRenditionStatus);
        manualDataEntryPage().setSourceVersionDate(documentSourceVersionDate);
        manualDataEntryPage().setSelectedFile(documentSelectedFile);
        manualDataEntryPage().setReasonDropdown(documentReason);
        manualDataEntryPage().clickAddContent();

        boolean workflowReportingWindowAppeared =  toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowReportingWindowAppeared,"The workflow reporting window did not appear.");

        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, contentSet, description, username);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();
        //Filter to create stability
        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceFilterContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean newRenditionIsCreated = sourceNavigateGridPage().firstRenditionExists();
        renditionUuid = sourceNavigateGridPage().getUUID();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(workflowCompleted, String.format("The workflow %s was not completed.", workflowID)),
            () -> Assertions.assertTrue(newRenditionIsCreated, "The new rendition wasn't created.")
        );
    }

    @AfterEach
    public void deleteRendition()
    {
        sourceNavigateGridPage().setRenditionDeletedWithDatabase(renditionUuid, username);
    }
}
