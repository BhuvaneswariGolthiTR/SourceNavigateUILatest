package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddPendingContentTests extends TestService
{
    String renditionUuid;
    String username;
    /**
     * STORY: na <br>
     * SUMMARY: Workflow should Finish adn rendition should become SF<br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sToSFlegalTest()
    {
        //int yarYear = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy());
        String docNumber = DateAndTimeUtils.getCurrentTimeHHmmss().replaceAll(":", "");
        docNumber = docNumber.charAt(0) == 0 ? docNumber.substring(1) : docNumber;

        String deleted = "Not Deleted";
        String sourceFilterContentSet = "Iowa (Development)";
        String year = "2021";//yarYear % 2 == 0 ? (yarYear-1) + "" : yarYear + "";
        String session = "1RG";
        String renditionStatus = "IN";
        String legislationType = "Bill";
        String contentType = "BILL";
        String sourceDocType = "SF";
        String sourceFilterRenditionTitle = "TITLE: This a S to SF QED test";

        String jurisdiction = "Iowa";
        String chamber = "SENATE";
        String documentType = "S";
        String addContentDocumentTitle = "This a S to SF QED test";
        String sponsors = "COMMITTEE ON JUDICIARY";
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String selectedFile = "EO102.pdf";

        String workflowType = "LcprBillTextSuperquick";
        String workflowContentSet = "LCPR.IA";
        String workflowDescription = "LCPR Bill Text";
        username = user().getUsername().toUpperCase();

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared,"The source navigate window didn't appear");

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceFilterContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterLegislationType(legislationType);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(sourceDocType);
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
        Assertions.assertTrue(addContentPageAppeared, "The add content window did not appear.");

        manualDataEntryPage().setJurisdictionDropdown(jurisdiction);
        manualDataEntryPage().setYear(year);
        manualDataEntryPage().setSessionDropdown(session);
        manualDataEntryPage().setChamberDropdown(chamber);
        manualDataEntryPage().setContentTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTypeDropdown(documentType);
        manualDataEntryPage().setDocumentNumber(docNumber);
        manualDataEntryPage().setLegislationTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTitle(addContentDocumentTitle);
        manualDataEntryPage().setSponsors(sponsors);
        manualDataEntryPage().checkManualContentDataEntryCheckBox();
        manualDataEntryPage().setRenditionStatusDropdown(renditionStatus);
        manualDataEntryPage().setSourceVersionDate(date);
        manualDataEntryPage().setSelectedFile(selectedFile);
        manualDataEntryPage().clickAddContent();

        sourcePage().switchToSourceNavigatePage();
        boolean workflowReportingWindowAppeared = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowReportingWindowAppeared);
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowContentSet, workflowDescription, username);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowCompleted, "The workflow did not finish: " + workflowID);

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();
        boolean renditionAppearedOnGrid = sourceNavigateGridPage().firstRenditionExists();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(workflowCompleted, String.format("The workflow %s did not finish.", workflowID)),
            () -> Assertions.assertTrue(renditionAppearedOnGrid, "The new rendition did not appear on the grid.")
        );
    }

    /**
     * STORY: na <br>
     * SUMMARY: Workflow should fail, but we should still see a rendition <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void workflowErrorWithRenditionLegalTest()
    {
        //int yarYear = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy());
        String docNumber = DateAndTimeUtils.getCurrentTimeHHmmss().replaceAll(":", "");
        docNumber = docNumber.charAt(0) == 0 ? docNumber.substring(1) : docNumber;

        String deleted = "Not Deleted";
        String sourceFilterContentSet = "Iowa (Development)";
        String year = "2021";//yarYear % 2 == 0 ? (yarYear-1) + "" : yarYear + "";
        String session = "1RG";
        String renditionStatus = "GN";
        String legislationType = "Bill";
        String contentType = "BILL";
        String docType = "LB";
        String sourceFilterRenditionTitle = "TITLE: This a workflow error with rendition QED test";

        String jurisdiction = "Iowa";
        String chamber = "LEGISLATURE";
        String addContentDocumentTitle = "This a workflow error with rendition QED test";
        String sponsors = "COMMITTEE ON JUDICIARY";
        String sourceVersionDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String selectedFile = "EO102.pdf";

        String workflowType = "LcprBillTextSuperquick";
        String workflowFilterContentSet = "LCPR.IA";
        String description = "LCPR Bill Text";
        username = user().getUsername().toUpperCase();

        String expectedWorkFlowError = "Failed to run novus conversion filter chain: RuntimeException: Cannot get pub number from PACE - jurisdiction: IA, first line cite template: IALBNS";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared, "The source navigate window did not appear.");

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceFilterContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterLegislationType(legislationType);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(sourceFilterRenditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            boolean deleteConfirmationWindowAppeared = renditionContextMenu().modifyDeleteRendition();
            Assertions.assertTrue(deleteConfirmationWindowAppeared, "The delete confirmation window did not appear.");
        }

        clamshellPage().openSideBarCreate();
        boolean addContentWindowAppeared = renditionTabCreateClamshellPage().clickAddContent(true, false);
        Assertions.assertTrue(addContentWindowAppeared, "The add content window did not appear.");

        manualDataEntryPage().setJurisdictionDropdown(jurisdiction);
        manualDataEntryPage().setYear(year);
        manualDataEntryPage().setSessionDropdown(session);
        manualDataEntryPage().setChamberDropdown(chamber);
        manualDataEntryPage().setContentTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTypeDropdown(docType);
        manualDataEntryPage().setDocumentNumber(docNumber);
        manualDataEntryPage().setLegislationTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTitle(addContentDocumentTitle);
        manualDataEntryPage().setSponsors(sponsors);
        manualDataEntryPage().checkManualContentDataEntryCheckBox();
        manualDataEntryPage().setRenditionStatusDropdown(renditionStatus);
        manualDataEntryPage().setSourceVersionDate(sourceVersionDate);
        manualDataEntryPage().setSelectedFile(selectedFile);
        manualDataEntryPage().clickAddContent();

        sourcePage().switchToSourceNavigatePage();
        boolean workflowReportingWindowAppeared = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowReportingWindowAppeared, "The workflow reporting window did not appear");
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowFilterContentSet, description, username);
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().openWorkflow(workflowId);
        workflowDetailsPage().expandWorkflowProperties();
        String logValue = workflowPropertiesPage().getWorkflowPropertyValueByName("_log");
        boolean logContainsExpectedError = logValue.contains(expectedWorkFlowError);

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        boolean renditionAppearedOnGrid = sourceNavigateGridPage().firstRenditionExists();

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(workflowCompleted, String.format("The workflow %s completed without an error.", workflowId)),
            () -> Assertions.assertTrue(logContainsExpectedError, "The workflow log didn't contain the expected error."),
            () -> Assertions.assertTrue(renditionAppearedOnGrid, "The new rendition did not appear on the source navigate grid.")
        );
    }

    /**
     * STORY: na <br>
     * SUMMARY: Workflow should fail, and we should not see a rendition <br>
     * USER:  LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void workflowErrorWithNoRenditionLegalTest()
    {
        //int yarYear = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy());
        String docNumber = DateAndTimeUtils.getCurrentTimeHHmmss().replaceAll(":", "");
        docNumber = docNumber.charAt(0) == 0 ? docNumber.substring(1) : docNumber;

        String deleted = "Not Deleted";
        String sourceFilterContentSet = "Iowa (Development)";
        String year = "2021";//yarYear % 2 == 0 ? (yarYear-1) + "" : yarYear + "";
        String session = "1RG";
        String renditionStatus = "FLD";
        String sourceFilterLegislationType = "Bill";
        String contentType = "BILL";
        String sourceFilterDocType = "SF";
        String renditionTitle = "TITLE: This a workflow error without rendition QED test";

        String jurisdiction = "Iowa";
        String addContentChamber = "SENATE";
        String addContentDocumentType = "S";
        String addContentLegislationType = "JOINT_MEMORIAL";
        String documentTitle = "This a workflow error without rendition QED test";
        String sponsors = "COMMITTEE ON JUDICIARY";
        String sourceVersionDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String selectedFile = "EO102.pdf";

        String workflowType = "LcprBillTextSuperquick";
        String workflowFilterContentSet = "LCPR.IA";
        String workflowDescription = "LCPR Bill Text";
        username = user().getUsername().toUpperCase();
        renditionUuid = "";

        String expectedWorkFlowErrorOne = "Failed create ampex file:";
        String expectedWorkFlowErrorTwo = "error: Failed to retrieve cite expansion entry - IA, SJM";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared,"The source navigate window did not appear");

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceFilterContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterLegislationType(sourceFilterLegislationType);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(sourceFilterDocType);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(renditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            renditionContextMenu().modifyDeleteRendition();
            sourceNavigateGridPage().waitForGridRefresh();
        }

        clamshellPage().openSideBarCreate();
        renditionTabCreateClamshellPage().clickAddContent(true, false);

        manualDataEntryPage().setJurisdictionDropdown(jurisdiction);
        manualDataEntryPage().setYear(year);
        manualDataEntryPage().setSessionDropdown(session);
        manualDataEntryPage().setChamberDropdown(addContentChamber);
        manualDataEntryPage().setContentTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTypeDropdown(addContentDocumentType);
        manualDataEntryPage().setDocumentNumber(docNumber);
        manualDataEntryPage().setLegislationTypeDropdown(addContentLegislationType);
        manualDataEntryPage().setDocumentTitle(documentTitle);
        manualDataEntryPage().setSponsors(sponsors);
        manualDataEntryPage().checkManualContentDataEntryCheckBox();
        manualDataEntryPage().setRenditionStatusDropdown(renditionStatus);
        manualDataEntryPage().setSourceVersionDate(sourceVersionDate);
        manualDataEntryPage().setSelectedFile(selectedFile);
        manualDataEntryPage().clickAddContent();

        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowFilterContentSet, workflowDescription, username);
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().openWorkflow(workflowId);
        workflowDetailsPage().expandWorkflowProperties();
        String logValue = workflowPropertiesPage().getWorkflowPropertyValueByName("_log");
        boolean logContainsExpectedError = logValue.contains(expectedWorkFlowErrorOne) && logValue.contains(expectedWorkFlowErrorTwo);

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean renditionAppearedOnGrid = sourceNavigateGridPage().firstRenditionExists();
        if(renditionAppearedOnGrid)
        {
            renditionUuid = sourceNavigateGridPage().getUUID();
        }

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(workflowCompleted, String.format("The workflow %s completed without an error.", workflowId)),
            () -> Assertions.assertTrue(logContainsExpectedError, "The workflow log didn't contain the expected error."),
            () -> Assertions.assertFalse(renditionAppearedOnGrid, "The new rendition did appear on the source navigate grid.")
        );
    }

    /**
     * STORY: na <br>
     * SUMMARY: Workflow should finish and rendition should become HF. workflow might take awhile <br>
     * USER:  Legal<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sfToHFlegalTest()
    {
        //int yarYear = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy());
        String docNumber = DateAndTimeUtils.getCurrentTimeHHmmss().replaceAll(":", "");
        docNumber = docNumber.charAt(0) == 0 ? docNumber.substring(1) : docNumber;

        String deleted = "Not Deleted";
        String sourceFilterContentSet = "Iowa (Development)";
        String year = "2021";//yarYear % 2 == 0 ? (yarYear-1) + "" : yarYear + "";
        String session = "1RG";
        String renditionStatus = "IN";
        String sourceFilterLegislationType = "Bill";
        String contentType = "BILL";
        String sourceFilterDocType = "HF";
        String sourceFilterRenditionTitle = "TITLE: This a SF to HF QED test";

        String jurisdiction = "Iowa";
        String chamber = "HOUSE";
        String addContentDocumentType = "SF";
        String addContentLegislationType = "BILL";
        String addContentDocumentTitle = "This a SF to HF QED test";
        String sponsors = "COMMITTEE ON JUDICIARY";
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String selectedFile = "EO102.pdf";

        String workflowType = "LcprBillTextSuperquick";
        String workflowFilterContentSet = "LCPR.IA";
        String workflowDescription = "LCPR Bill Text";
        username = user().getUsername().toUpperCase();

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared,"The source navigate window didn't appear");

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceFilterContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterLegislationType(sourceFilterLegislationType);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(sourceFilterDocType);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(sourceFilterRenditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            boolean deleteConfirmationWindowAppeared = renditionContextMenu().modifyDeleteRendition();
            Assertions.assertTrue(deleteConfirmationWindowAppeared, "The delete confirmation window did not appear.");
        }

        clamshellPage().openSideBarCreate();
        boolean addContentWindowAppeared = renditionTabCreateClamshellPage().clickAddContent(true, false);
        Assertions.assertTrue(addContentWindowAppeared, "The add content window did not appear");

        manualDataEntryPage().setJurisdictionDropdown(jurisdiction);
        manualDataEntryPage().setYear(year);
        manualDataEntryPage().setSessionDropdown(session);
        manualDataEntryPage().setChamberDropdown(chamber);
        manualDataEntryPage().setContentTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTypeDropdown(addContentDocumentType);
        manualDataEntryPage().setDocumentNumber(docNumber);
        manualDataEntryPage().setLegislationTypeDropdown(addContentLegislationType);
        manualDataEntryPage().setDocumentTitle(addContentDocumentTitle);
        manualDataEntryPage().setSponsors(sponsors);
        manualDataEntryPage().checkManualContentDataEntryCheckBox();
        manualDataEntryPage().setRenditionStatusDropdown(renditionStatus);
        manualDataEntryPage().setSourceVersionDate(date);
        manualDataEntryPage().setSelectedFile(selectedFile);
        manualDataEntryPage().clickAddContent();

        sourcePage().switchToSourceNavigatePage();
        boolean workflowReportingWindowAppeared = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowReportingWindowAppeared, "The workflow reporting window did not appear");
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowFilterContentSet, workflowDescription, username);
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowCompleted, "The workflow did not finish: " + workflowId);

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        boolean renditionAppearedOnGrid = sourceNavigateGridPage().firstRenditionExists();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(workflowCompleted, String.format("The workflow %s did not finish.", workflowId)),
            () -> Assertions.assertTrue(renditionAppearedOnGrid, "The new rendition did not appear on the grid.")
        );
    }

    /**
     * STORY: na <br>
     * SUMMARY: Workflow should Finish and rendition should become XXX. users aren't quite sure what rtf documents are like in the system. <br>
     * USER: Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sfToSFWithRtflegalTest()
    {
        //int yarYear = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy());
        String docNumber = DateAndTimeUtils.getCurrentTimeHHmmss().replaceAll(":", "");
        docNumber = docNumber.charAt(0) == 0 ? docNumber.substring(1) : docNumber;

        String deleted = "Not Deleted";
        String sourceFilterContentSet = "Iowa (Development)";
        String year = "2021";//yarYear % 2 == 0 ? (yarYear-1) + "" : yarYear + "";
        String session = "1RG";
        String renditionStatus = "IN";
        String sourceFilterLegislationType = "Bill";
        String contentType = "BILL";
        String sourceFilterDocType = "HF";
        String sourceFilterRenditionTitle = "TITLE: This a SF to SF with RTF QED test";

        String jurisdiction = "Iowa";
        String chamber = "SENATE";
        String addContentDocumentType = "SF";
        String addContentLegislationType = "BILL";
        String addContentDocumentTitle = "This a SF to SF with RTF QED test";
        String sponsors = "COMMITTEE ON JUDICIARY";
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String selectedFile = "This is a RTF file.rtf";

        String workflowType = "LcprBillTextSuperquick";
        String workflowFilterContentSet = "LCPR.IA";
        String workflowDescription = "LCPR Bill Text";
        username = user().getUsername().toUpperCase();

        String sourceFilterDocTypeAfterCreation = "SF";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared,"The source navigate window didn't appear");

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceFilterContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterLegislationType(sourceFilterLegislationType);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(sourceFilterDocType);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionTitle(sourceFilterRenditionTitle);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            boolean deleteConfirmationWindowAppeared = renditionContextMenu().modifyDeleteRendition();
            Assertions.assertTrue(deleteConfirmationWindowAppeared, "The delete confirmation window did not appear.");
        }
        clamshellPage().openSideBarCreate();
        boolean addContentWindowAppeared = renditionTabCreateClamshellPage().clickAddContent(true, false);
        Assertions.assertTrue(addContentWindowAppeared, "The add content window did not appear");

        manualDataEntryPage().setJurisdictionDropdown(jurisdiction);
        manualDataEntryPage().setYear(year);
        manualDataEntryPage().setSessionDropdown(session);
        manualDataEntryPage().setChamberDropdown(chamber);
        manualDataEntryPage().setContentTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTypeDropdown(addContentDocumentType);
        manualDataEntryPage().setDocumentNumber(docNumber);
        manualDataEntryPage().setLegislationTypeDropdown(addContentLegislationType);
        manualDataEntryPage().setDocumentTitle(addContentDocumentTitle);
        manualDataEntryPage().setSponsors(sponsors);
        manualDataEntryPage().checkManualContentDataEntryCheckBox();
        manualDataEntryPage().setRenditionStatusDropdown(renditionStatus);
        manualDataEntryPage().setSourceVersionDate(date);
        manualDataEntryPage().setSelectedFile(selectedFile);
        manualDataEntryPage().clickAddContent();

        sourcePage().switchToSourceNavigatePage();
        boolean workflowReportingWindowAppeared = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowReportingWindowAppeared, "Workflow reporting window failed to appear");
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowFilterContentSet, workflowDescription, username);
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowCompleted, "The workflow did not finish: " + workflowId);

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateFiltersAndSortsPage().setFilterDocType(sourceFilterDocTypeAfterCreation);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        boolean renditionAppearedOnGrid = sourceNavigateGridPage().firstRenditionExists();

        Assertions.assertAll
        (
            () ->  Assertions.assertTrue(workflowCompleted, String.format("The workflow %s did not finish.", workflowId)),
            () -> Assertions.assertTrue(renditionAppearedOnGrid, "The new rendition did not appear on the grid.")
        );
    }

    @AfterEach
    public void deleteRendition()
    {
        sourceNavigateGridPage().setRenditionDeletedWithDatabase(renditionUuid, username);
    }
}
