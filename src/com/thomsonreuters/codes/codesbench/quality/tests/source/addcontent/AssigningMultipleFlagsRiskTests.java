package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import java.util.ArrayList;

public class AssigningMultipleFlagsRiskTests extends TestService
{
    ArrayList<String> renditionUuids;
    String username;
    /**
     * STORY/BUG - JETS-11718 <br>
     * SUMMARY -  Verify that creating renditions with PDF documents workflows finish successfully and
     *            multiple flags appear after two renditions with the same attributes are created <br>
     * USER - Risk <br>
     */

    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void assigningMultipleFlagsRiskTest()
    {
        renditionUuids = new ArrayList<>();
        username = user().getUsername().toUpperCase();
        String renditionTitle = "Multiple Flag Test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String docNumber = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        docNumber = docNumber.substring(4, 12);
        String year = DateAndTimeUtils.getCurrentYearyyyy();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Create the first rendition
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("Crown Dependencies");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Australian Prudential Regulation Authority");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear(year);
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile("EO102.pdf");
        addShellPage().clickSubmit();

        //Go to workflow reporting and validate the first workflow finished
        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean firstWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprShellSuperquick", "LCPR.UK", "LCPR Shell Doc");
        String firstWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(firstWorkflowCompleted, String.format("The first workflow %s did not complete", firstWorkflowID));

        //Filter for the first rendition and get it's uuid
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation("Australian Prudential Regulation Authority");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuids.add(sourceNavigateGridPage().getUUID());

        //Create the second rendition
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("Crown Dependencies");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Australian Securities and Investments Commission");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear(year);
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile("EO102.pdf");
        addShellPage().clickSubmit();

        //Go to workflow reporting and validate the second workflow finishes
        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean secondWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprShellSuperquick", "LCPR.UK", "LCPR Shell Doc");
        String secondWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(secondWorkflowCompleted, String.format("The second workflow %s did not complete", secondWorkflowID));

        //Filter for the second rendition and get it's uuid
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation("Australian Securities and Investments Commission");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        waitForRenditionToAppear();
        renditionUuids.add(sourceNavigateGridPage().getUUID());

        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("Multiple");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean noMultipleFlagsAppear = sourceNavigateGridPage().getNumberOfRenditions() >= 1;

        //Create the third rendition
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("Crown Dependencies");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Australian Securities and Investments Commission");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear(year);
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile("EO102.pdf");
        addShellPage().clickSubmit();

        //Go to workflow reporting and validate the third workflow finishes
        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean thirdWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprShellSuperquick", "LCPR.UK", "LCPR Shell Doc");
        String thirdWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(thirdWorkflowCompleted, String.format("The third workflow %s did not complete.", thirdWorkflowID));

        //Refresh the grid and wait for the rendition with the updated flag of Multiple, should be two renditions in grid
        boolean renditionsAppeared = waitForRenditionToAppear();
        sourceNavigateGridPage().clickRowByIndex(2);
        renditionUuids.add(sourceNavigateGridPage().getSelectedUuid());
        boolean multipleFlagsAppear = sourceNavigateGridPage().getNumberOfRenditions() == 2;

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(noMultipleFlagsAppear, "Uh-oh.. We have multiple flags appearing. This is not what we want."),
            () -> Assertions.assertTrue(renditionsAppeared, "The multiple renditions did not show up in the grid"),
            () -> Assertions.assertTrue(multipleFlagsAppear, "We aren't seeing any renditions with Multiple flags. We should be seeing two.")
        );
    }

    /**
     * STORY/BUG - JETS-11718 <br>
     * SUMMARY -  Verify that creating renditions with text documents workflows finish successfully and
     *            multiple flags appear after two renditions with the same attributes are created <br>
     * USER - Risk <br>
     */

    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void assigningMultipleFlagsFullTextDocumentRiskTest()
    {
        renditionUuids = new ArrayList<>();
        username = user().getUsername().toUpperCase();
        String renditionTitle = "Multiple Flag Test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String docNumber = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        docNumber = docNumber.substring(4, 12);

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Create the first rendition
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("Crown Dependencies");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Australian Prudential Regulation Authority");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear("2017");
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile("ex_valid_3.txt");
        addShellPage().clickSubmit();

        //Go to workflow reporting and validate the workflow finished
        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean firstWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprRiskSourceQuickKeyed", "LCPR.UK", "LCPR Risk Source");
        String firstWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(firstWorkflowCompleted, String.format("The first workflow %s did not complete", firstWorkflowID));

        //Filter for the first rendition and get it's UUID
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation("Australian Prudential Regulation Authority");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuids.add(sourceNavigateGridPage().getUUID());

        //Create the second rendition
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("Crown Dependencies");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Australian Securities and Investments Commission");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear("2017");
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile("ex_valid_3.txt");
        addShellPage().clickSubmit();

        //Go to workflow reporting and validate the second workflow finished
        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean secondWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprRiskSourceQuickKeyed", "LCPR.UK", "LCPR Risk Source");
        String secondWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(secondWorkflowCompleted, String.format("The second workflow %s did not complete.", secondWorkflowID));

        //Filter for the second rendition and get the UUID
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation("Australian Securities and Investments Commission");
        waitForRenditionToAppear();
        renditionUuids.add(sourceNavigateGridPage().getUUID());

        //Filter for the created renditions with Multiple tag, should have no renditions in grid
        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("Multiple");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean noMultipleFlagsAppear = sourceNavigateGridPage().getNumberOfRenditions() >= 1;
        Assertions.assertFalse(noMultipleFlagsAppear, "Uh-oh.. We have multiple flags appearing. This is not what we want.");

        //Change filter so there is None for Multiple/Duplicate to see the created renditions
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation("Australian Securities and Investments Commission");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        int originalFileSize = Integer.parseInt(sourceNavigateGridPage().getElement(SourceNavigatePageElements.SOURCE_FILE_SIZE_IN_GRID + "/div").getText().trim());

        //Create the third rendition
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("Crown Dependencies");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Australian Securities and Investments Commission");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear("2017");
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile("ex_valid_3.txt");
        addShellPage().clickSubmit();

        //Go to workflow reporting and validate the third workflow finished
        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_MINUTE);
        boolean thirdWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprRiskSourceQuickKeyed", "LCPR.UK", "LCPR Risk Source");
        String thirdWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(thirdWorkflowCompleted, String.format("The third workflow %s did not complete", thirdWorkflowID));

        sourceNavigateFooterToolsPage().refreshTheGrid();
        int updatedFileSize = Integer.parseInt(sourceNavigateGridPage().getElement(SourceNavigatePageElements.SOURCE_FILE_SIZE_IN_GRID + "/div").getText().trim());
        boolean differentFileSizes = originalFileSize < updatedFileSize;
        Assertions.assertTrue(differentFileSizes, "The file size did not change after adding the txt file.");

        String documentStart = "ESMA Risk Dashboard TEST DOCUMENT";

        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean instanceCountOfDocumentStart = editorTextPage().countInstancesOfTermInParagraphs(documentStart) == 2;
        editorPage().closeDocumentWithNoChanges();
        editorPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);

        Assertions.assertTrue(instanceCountOfDocumentStart, "The rendition isn't showing that the documents contents are being added twice.");
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
