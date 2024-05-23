package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class AddPDFMetadataAddFileNumberRiskTests extends TestService
{
    String renditionUuid;
    String username;
/**
     * STORY: JETS-9817, JETS-10915 and JETS-14896 <br>
     * SUMMARY: checks adding file numbers to the shells<br>
     * USER:  RISK <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void verifyAddFileNumberWithAddAndEditPDFRiskTest()
    {
        username = user().getUsername().toUpperCase();
        String renditionTitle = "Verify Add File Number Document";
        String docNumber = "123TESTABCD";
        String docType = "EN";
        String contentType = "PUBNOT";
        String renditionStatus = "APVRS";
        String year = (Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy())-1)+"";
        String organisation = "Australian Prudential Regulation Authority";
        String contentSet = "USCA(Development)";
        String deleted = "Not Deleted";

        String shellDocType = "Election Notice";
        String shellContentType = "Public Notices";
        String selectedFile = "EO102.pdf";
        String firstFileNumber = "0000";
        String secondFileNumber = "1111";
        String thirdFileNumber = "2222";
        String fourthFileNumber = "3333";

        String workflowType = "LcprShellSuperquick";
        String workflowContentSet = "LCPR.US";
        String workflowDescription = "LCPR Shell Doc";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation(organisation);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            renditionContextMenu().modifyDeleteRendition();
            sourcePage().switchToSourceNavigatePage();
        }

        clamshellPage().openCreatePdfMetadata();
        addShellPage().setDropdownContentSet(contentSet);
        addShellPage().setDropdownDocumentType(shellDocType);
        addShellPage().setTextBoxOrganization(organisation);
        addShellPage().setTextBoxDocumentNumber(docNumber);
        addShellPage().setDropdownContentType(shellContentType);
        addShellPage().setTextBoxYear(year);
        addShellPage().setTextBoxDocumentTitle(renditionTitle);
        addShellPage().selectFile(selectedFile);
        addShellPage().typeNewFileNumber(firstFileNumber);
        addShellPage().clickAddNewFileNumber();
        addShellPage().typeNewFileNumber(secondFileNumber);
        boolean alertAppears = addShellPage().clickSubmit();

        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowContentSet, workflowDescription);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateFooterToolsPage().clickTheViewManager();
        viewManagementPage().selectView("all cols");

        sourceNavigateFooterToolsPage().waitForGridRefresh();
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterOrganisation(organisation);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //old test used the menu to go to Source Navigate. I don't know why they do this. I've left it out.

        boolean documentNumberCorrectlyAppearsInSourceNav = sourceNavigateGridPage().getDocNumber().equals(docNumber);
        boolean initialFileNumber = sourceNavigateGridPage().getFileNumber().equals(firstFileNumber);

        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openEditPdfMetadata();

        boolean documentNumberCorrectlyAppearsInEditPDF = editShellPage().getDocumentNumber().equals(docNumber);

        editShellPage().deleteAllFiles();
        editShellPage().typeNewFileNumber(thirdFileNumber);
        editShellPage().clickAddNewFileNumber();
        editShellPage().typeNewFileNumber(fourthFileNumber);
        editShellPage().clickSave();
        AutoITUtils.verifyAlertTextContainsAndAccept(true, "PDF/Metadata has been Updated.");

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();
        boolean editFileNumber = sourceNavigateGridPage().getFileNumber().equals(thirdFileNumber);

        Assertions.assertAll
        (
           () -> Assertions.assertTrue(alertAppears, "The Alert for adding pdfMetadata appeared"),
           () -> Assertions.assertTrue(workflowCompleted, String.format("The workflow %s finished", workflowID)),
           () -> Assertions.assertTrue(documentNumberCorrectlyAppearsInSourceNav,"document number appeared in source Navigate"),
           () -> Assertions.assertTrue(initialFileNumber, "the file number is correct in source navigate"),
           () -> Assertions.assertTrue(documentNumberCorrectlyAppearsInEditPDF, "document number appeared in Edit PDF Metadata"),
           () -> Assertions.assertTrue(editFileNumber, "the file number is correct when edited")
        );
    }

    @AfterEach
    public void deleteRendition()
    {
        sourceNavigateGridPage().setRenditionDeletedWithDatabase(renditionUuid, username);
    }
}
