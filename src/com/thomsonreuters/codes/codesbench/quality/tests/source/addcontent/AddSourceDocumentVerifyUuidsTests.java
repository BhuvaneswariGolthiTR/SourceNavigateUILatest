package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AddSourceDocumentVerifyUuidsTests extends TestService
{
    ArrayList<String> renditionUuids;
    String username;
/**
     * STORY: JETS-8660 <br>
     * SUMMARY: checks adding file numbers to the shells<br>
     * USER:  RISK <br>
     */

    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void addSourceDocumentVerifyUuidsTest()
    {
        username = user().getUsername().toUpperCase();
        renditionUuids = new ArrayList<>();
        String renditionTitle = "Verify Different File UUIDs";
        String docType = "EN";
        String contentType = "PUBNOT";
        String renditionStatus = "APVRS";
        String year = "2016";
        String docNumber = "998";
        String firstOrganisation = "Australian Prudential Regulation Authority";
        String secondOrganisation = "BOX Options Exchange (BOX)";
        String contentSet = "USCA(Development)";
        String deleted = "Not Deleted";

        String addedDocumentType = "Election Notice";
        String addedContentType = "Public Notices";
        String firstFile = "EO102.pdf";
        String secondFile = "EO103.pdf";

        String workflowType = "LcprShellSuperquick";
        String workflowContentSet = "LCPR.US";
        String workflowDescription = "LCPR Shell Doc";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation(firstOrganisation);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            renditionContextMenu().modifyDeleteRendition();
            sourcePage().switchToSourceNavigatePage();
            sourcePage().waitForGridRefresh();
        }

        sourceNavigateFiltersAndSortsPage().setFilterOrganisation(secondOrganisation);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            renditionContextMenu().modifyDeleteRendition();
            sourcePage().switchToSourceNavigatePage();
            sourcePage().waitForGridRefresh();
        }

        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet(contentSet);
        addShellPage().setDropdownDocumentType(addedDocumentType);
        addShellPage().setTextBoxOrganization(firstOrganisation);
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType(addedContentType);
        addShellPage().setTextBoxYear(year);
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile(firstFile);
        boolean firstAlertAppear = addShellPage().clickSubmit();

        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();
        boolean firstWorkflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowContentSet, workflowDescription);
        String firstWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateFiltersAndSortsPage().setFilterOrganisation(firstOrganisation);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        /*
        For some reason the rendition isn't in the grid after the workflow. This just waits for it.
         */

        Assertions.assertTrue(waitForRenditionToAppear(), "Rendition was in the grid.");
        String firstUUID = sourceNavigateGridPage().getUUID();
        renditionUuids.add(firstUUID);

        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet(contentSet);
        addShellPage().setDropdownDocumentType(addedDocumentType);
        addShellPage().setTextBoxOrganization(secondOrganisation);
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType(addedContentType);
        addShellPage().setTextBoxYear(year);
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile(secondFile);
        boolean secondAlertAppear = addShellPage().clickSubmit();

        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();
        boolean secondWorkflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowContentSet, workflowDescription);
        String secondWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateFiltersAndSortsPage().setFilterOrganisation(secondOrganisation);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        /*
        For some reason the rendition isn't in the grid after the workflow. This just waits for it.
         */

        Assertions.assertTrue(waitForRenditionToAppear(), "Rendition was in the grid.");
        String secondUUID = sourceNavigateGridPage().getUUID();
        renditionUuids.add(secondUUID);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(firstAlertAppear, "The first alert appeared for creating the first document"),
            () -> Assertions.assertTrue(firstWorkflowFinished, String.format("The first workflow %s finished for creating the first document", firstWorkflowID)),
            () -> Assertions.assertTrue(secondAlertAppear, "The second alert appeared for creating the second document"),
            () -> Assertions.assertTrue(secondWorkflowFinished, String.format("The second workflow %s finished for creating the second document", secondWorkflowID)),
            () -> Assertions.assertNotEquals(firstUUID, secondUUID, "These renditions are not equal to one another")
        );
    }

    private boolean waitForRenditionToAppear()
    {
        int timeLimit = DateAndTimeUtils.FIVE_MINUTES;
        int timeStep = DateAndTimeUtils.TEN_SECONDS;
        int timePassed = 0;
        while(timePassed < timeLimit)
        {
            sourceNavigateFooterToolsPage().refreshTheGrid();
            if(sourceNavigateGridPage().firstRenditionExists())
            {
                return true;
            }
            DateAndTimeUtils.takeNap(timeStep);
            timePassed += timeStep;
        }
        return false;
    }

    @AfterEach
    public void deleteRenditions()
    {
        for (String rendUuid : renditionUuids)
        {
            sourceNavigateGridPage().setRenditionDeletedWithDatabase(rendUuid, username);
        }
    }
}
