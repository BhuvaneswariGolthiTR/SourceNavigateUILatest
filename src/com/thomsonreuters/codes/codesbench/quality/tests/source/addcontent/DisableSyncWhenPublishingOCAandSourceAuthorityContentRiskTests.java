package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

import java.util.ArrayList;

public class DisableSyncWhenPublishingOCAandSourceAuthorityContentRiskTests extends TestService
{
    ArrayList<String> renditionUuids;
    String username;
    /**
     * STORY/BUG - JETS-10969 <br>
     * SUMMARY - Tests that the sync option is not enabled for the renditions created <br>
     * USER - Risk <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void disableSyncWhenPublishingOCAandSourceAuthorityContentRiskTest()
    {
        renditionUuids = new ArrayList<>();
        username = user().getUsername().toUpperCase();
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Create a unique doc number
        String docNumber = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        docNumber = docNumber.substring(4, 12);

        //Create a rendition with Content Set: FINRA, Doc Type: NOT
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("FINRA");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Financial Industry Regulatory Authority");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear("2013");
        addShellPage().setTextBoxDocumentTitle("10969-doc1-" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        addShellPage().selectFile("EO102.pdf");
        addShellPage().clickSubmit();

        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean firstWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprShellSuperquick", "LCPR.US", "LCPR Shell Doc");
        String firstWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(firstWorkflowCompleted, String.format("The workflow %s did complete.", firstWorkflowID));

        //Create a rendition with Content Set: FINRA, Doc Type: LAW
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("FINRA");
        addShellPage().setDropdownDocumentType("Law");
        addShellPage().setTextBoxOrganization("Financial Industry Regulatory Authority");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear("2013");
        addShellPage().setTextBoxDocumentTitle("10969-doc2-" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        addShellPage().selectFile("EO102.pdf");
        addShellPage().clickSubmit();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_MINUTE);

        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean secondWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprShellSuperquick", "LCPR.US", "LCPR Shell Doc");
        String secondWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(secondWorkflowCompleted, String.format("The workflow %s did complete.", secondWorkflowID));

        //Filter for the rendition with the specific doc number
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation("Financial Industry Regulatory Authority");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2013");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateFooterToolsPage().clickSelectAllOnPage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        boolean firstSyncIsNotAvailable = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
        clamshellPage().openSideBarSync();
        clamshellPage().clickSideBarSyncSync();
        boolean firstExpectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept
                (true, "Selected rows do not support this function.");
        clamshellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);

        sourceNavigateGridPage().deleteRenditions();
        docNumber = DateAndTimeUtils.getCurrentDateAndTimeddHHmmss();

        //Create a rendition with Content Set: FINRA, Doc Type: NOT
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("FINRA");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Financial Industry Regulatory Authority");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear("2013");
        addShellPage().setTextBoxDocumentTitle("10969-doc3-" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        addShellPage().selectFile("EO102.pdf");
        addShellPage().clickSubmit();

        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean thirdWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprShellSuperquick", "LCPR.US", "LCPR Shell Doc");
        String thirdWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(thirdWorkflowCompleted, String.format("The workflow %s did complete.", thirdWorkflowID));

        //Create a rendition with Content Set: Crown Dependencies, Doc Type: NOT
        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet("Crown Dependencies");
        addShellPage().setDropdownDocumentType("Notice");
        addShellPage().setTextBoxOrganization("Financial Industry Regulatory Authority");
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType("Public Notices");
        addShellPage().setTextBoxYear("2013");
        addShellPage().setTextBoxDocumentTitle("10969-doc4-" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        addShellPage().selectFile("EO102.pdf");
        addShellPage().clickSubmit();

        addShellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        boolean fourthWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                ("LcprShellSuperquick", "LCPR.UK", "LCPR Shell Doc");
        String fourthWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        workflowSearchPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        Assertions.assertTrue(fourthWorkflowCompleted, String.format("The workflow %s did complete.", fourthWorkflowID));

        //Filter for rendition with specific doc number
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuids.add(sourceNavigateGridPage().getUUID());
        sourceNavigateGridPage().clickRowByIndex(2);
        renditionUuids.add(sourceNavigateGridPage().getSelectedUuid());
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateFooterToolsPage().clickSelectAllOnPage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        boolean secondSyncIsNotAvailable = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
        clamshellPage().openSideBarSync();
        clamshellPage().clickSideBarSyncSync();
        boolean secondExpectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept
                (true, "Selected rows do not support this function.");
        clamshellPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);

        Assertions.assertAll
        (
            () ->Assertions.assertTrue(firstSyncIsNotAvailable, "First sync is not available"),
            () ->Assertions.assertTrue(firstExpectedAlertAppeared, "The first alert we expected after clicking sync in the clamshell did appear."),
            () ->Assertions.assertTrue(secondSyncIsNotAvailable, "Second Sync is not available."),
            () ->Assertions.assertTrue(secondExpectedAlertAppeared, "The second alert we expected after clicking sync in the clamshell did appear.")
        );
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
