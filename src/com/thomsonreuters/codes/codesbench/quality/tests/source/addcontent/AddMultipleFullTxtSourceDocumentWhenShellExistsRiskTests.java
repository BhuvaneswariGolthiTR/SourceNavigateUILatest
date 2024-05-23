package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddMultipleFullTxtSourceDocumentWhenShellExistsRiskTests extends TestService
{
    String renditionUuid;
    String username;
    /**
     * STORY: JETS-6426 and JETS-12875 <br>
     * SUMMARY: Checks the ability to edit a document when a shell exists<br>
     * USER:  RISK<br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void addMultipleFullTxtSourceDocumentWhenShellExists()
    {
        username = user().getUsername().toUpperCase();
        String renditionTitle = "Add Multiple Source Files" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String contentSet = "USCA(Development)";
        String documentType = "Election Notice";
        String organization = "Australian Prudential Regulation Authority";
        String documentNumber = "345";
        String contentType = "Public Notices";
        String year = "2017";
        String selectedFile = "EO102.pdf";

        final String firstEditContent = "Following its 3Q14 increase, systemic stress displayed higher volatility than in the previous reporting period";
        final String secondEditContent = "The recent negative economic news and mounting uncertainty concerning the effectiveness of policy measures depressed market confidence";

        String workflowType = "LcprShellSuperquick";
        String workflowDescription = "LCPR Shell Doc";
        String workflowContentSet = "LCPR.US";

        String workflowEditType = "LcprRiskSourceQuickKeyed";
        String workflowEditDescription = "LCPR Risk Source";

        String sourceDocType = "EN";
        String sourceContentType = "PUBNOT";
        String renditionStatus = "APVRS";
        String deleted = "Not Deleted";

        String firstFileName = "ex_valid_3.txt";
        String secondFileName = "ex_valid_4.txt";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourcePage().waitForGridRefresh();

        clamshellPage().openSideBarCreate();
        renditionTabCreateClamshellPage().clickAddPDFMetadata(true, false);

        addShellPage().setDropdownContentSet(contentSet);
        addShellPage().setDropdownDocumentType(documentType);
        addShellPage().setTextBoxOrganization(organization);
        addShellPage().setTextBoxDocumentNumber(documentNumber);
        addShellPage().setDropdownContentType(contentType);
        addShellPage().setTextBoxYear(year);
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile(selectedFile);
        boolean alertAppeared = addShellPage().clickSubmit();

        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();
        boolean firstWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowContentSet, workflowDescription);
        String firstWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(documentNumber);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(sourceDocType);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(sourceContentType);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation(organization);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean firstOnlyOneRenditionAppeared = sourceNavigateGridPage().getDocumentCount() == 1;
        boolean firstSuperQuickWestlawLoad = sourceNavigateGridPage().doesElementExist(SourceNavigatePageElements.WESTLAW_LOAD_PREFIX_XPATH + "[text()='Super Quick']");

        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarEdit();
        clamshellPage().openEditPdfMetadata();

        editPDFMetadataPage().setTextBoxSelectedFile(firstFileName);
        editPDFMetadataPage().clickSaveButton();
        AutoITUtils.verifyAlertTextContainsAndAccept(true, "PDF/Metadata has been Updated.");

        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();
        boolean secondWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                (workflowEditType, "", workflowEditDescription);
        String secondWorkflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean secondOnlyOneRenditionAppeared = sourceNavigateGridPage().getDocumentCount() == 1;
        boolean secondQuickWestlawLoad = sourceNavigateGridPage().doesElementExist(SourceNavigatePageElements.WESTLAW_LOAD_PREFIX_XPATH + "[text()='Quick']");

        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();

        boolean secondContentAddedToShell = editorTextPage().doesElementExist(String.format(EditorTextPageElements.TEXT_PARAGRAPH_CONTAINS_TEXT_GIVEN,firstEditContent));

        editorPage().closeDocumentWithNoChanges();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarEdit();
        clamshellPage().openEditPdfMetadata();
        editPDFMetadataPage().setTextBoxSelectedFile(secondFileName);
        editPDFMetadataPage().clickSaveButton();
        AutoITUtils.verifyAlertTextContainsAndAccept(true, "PDF/Metadata has been Updated.");

        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();
        boolean thirdWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                (workflowEditType, "", workflowEditDescription);
        String thirdWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean thirdOnlyOneRenditionAppeared = sourceNavigateGridPage().getDocumentCount() == 1;
        boolean thirdQuickWestlawLoad = sourceNavigateGridPage().doesElementExist(SourceNavigatePageElements.WESTLAW_LOAD_PREFIX_XPATH + "[text()='Quick']");

        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();

        boolean thirdContentAddedToShell = editorTextPage().doesElementExist(String.format(EditorTextPageElements.TEXT_PARAGRAPH_CONTAINS_TEXT_GIVEN,secondEditContent));

        editorPage().closeDocumentWithNoChanges();
        sourcePage().switchToSourceNavigatePage();
        renditionUuid = sourceNavigateGridPage().getUUID();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(firstWorkflowCompleted, String.format("The first workflow %s finished", firstWorkflowID)),
            () -> Assertions.assertTrue(alertAppeared, "the add pdf metadata alert appeared"),
            () -> Assertions.assertTrue(firstOnlyOneRenditionAppeared, "one rendition appeared in the grid"),
            () -> Assertions.assertTrue(firstSuperQuickWestlawLoad, "there is the first super quick westlaw loaded in the grid"),
            () -> Assertions.assertTrue(secondWorkflowCompleted, String.format("The second workflow %s finished", secondWorkflowId)),
            () -> Assertions.assertTrue(secondOnlyOneRenditionAppeared, "only one rendition appeared in the grid"),
            () -> Assertions.assertTrue(secondQuickWestlawLoad, "there is the second super quick westlaw loaded in the grid"),
            () -> Assertions.assertTrue(secondContentAddedToShell, "Second Content was added to the shell"),
            () -> Assertions.assertTrue(thirdWorkflowCompleted, String.format("The third workflow %s completed", thirdWorkflowID)),
            () -> Assertions.assertTrue(thirdOnlyOneRenditionAppeared, "Only one rendition appeared in the grid"),
            () -> Assertions.assertTrue(thirdQuickWestlawLoad, "there is the third super quick westlaw loaded in the grid"),
            () -> Assertions.assertTrue(thirdContentAddedToShell, "Third Content was added to the shell")
        );
    }

    @AfterEach
    public void deleteRendition()
    {
        sourceNavigateGridPage().setRenditionDeletedWithDatabase(renditionUuid, username);
    }
}
