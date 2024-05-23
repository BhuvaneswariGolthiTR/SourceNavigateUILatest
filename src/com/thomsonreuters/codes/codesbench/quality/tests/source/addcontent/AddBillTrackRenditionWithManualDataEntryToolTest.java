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

public class AddBillTrackRenditionWithManualDataEntryToolTest extends TestService
{
    String renditionUuid;
    String username;
    /**
     * STORY: na <br>
     * SUMMARY:  Test to verify the user can add an Bill Track document with the manual data entry tool in source navigate<br>
     * USER:  Legal<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addBillTrackRenditionWithManualDataEntryToolTest()
    {
        String deleted = "Not Deleted";
        String sourceContentSet = "Iowa (Development)";
        String year = "2021";
        String session = "1RG";
        String contentType = "BILLTRACK";
        String docType = "H";
        String docNumber = DateAndTimeUtils.getCurrentTimeHHmmss().substring(1);

        String jurisdiction = "Iowa";
        String chamber = "HOUSE";
        String legislationType = "BILL";
        String documentTitle = "Test: " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String sponsors = "Representative(s) gatto";
        String summary = "An Act relating to certain investments of the Alaska permanent fund, the state's retirement systems, the State of Alaska Supplemental Annuity Plan, and the deferred compensation program for state employees in certain companies that do business in Iran, and restricting those investments; and providing for an effective date.";

        String firstDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String firstAction = "Reading";
        String firstStage = "PREFILE";

        String secondDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String secondAction = "Testing";
        String secondStage = "INTRO";

        String thirdDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String thirdAction = "Analysis";

        String workflowType = "LcprBillTrackPublish";
        String workflowContentSet = "LCPR.IA";
        String workflowDescription = "LCPR Bill Track";
        username = user().getUsername().toUpperCase();

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared,"The source navigate window did not appear.");

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterSession(session);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType("HF");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        if(sourceNavigateGridPage().firstRenditionExists())
        {
            sourceNavigateGridPage().rightClickFirstRendition();
            boolean deleteConfirmationWindowAppeared = renditionContextMenu().modifyDeleteRendition();
            Assertions.assertTrue(deleteConfirmationWindowAppeared, "The delete confirmation window did not appear.");
        }

        clamshellPage().openSideBarCreate();
        boolean addContentWindowAppeared = renditionTabCreateClamshellPage().clickAddContent(true, false);
        Assertions.assertTrue(addContentWindowAppeared, "The add content window didn't appear.");

        manualDataEntryPage().setJurisdictionDropdown(jurisdiction);
        manualDataEntryPage().setYear(year);
        manualDataEntryPage().setSessionDropdown(session);
        manualDataEntryPage().setChamberDropdown(chamber);
        manualDataEntryPage().setContentTypeDropdown(contentType);
        manualDataEntryPage().setDocumentTypeDropdown(docType);
        manualDataEntryPage().setDocumentNumber(docNumber);
        manualDataEntryPage().setLegislationTypeDropdown(legislationType);
        manualDataEntryPage().setDocumentTitle(documentTitle);
        manualDataEntryPage().setSponsors(sponsors);
        manualDataEntryPage().setSummary(summary);
        manualDataEntryPage().checkManualBillTrackDataEntryCheckBox();

        manualDataEntryPage().setDateX(1,firstDate);
        manualDataEntryPage().setActionX(1,firstAction);
        manualDataEntryPage().setStageX(1,firstStage);

        manualDataEntryPage().setDateX(2,secondDate);
        manualDataEntryPage().setActionX(2,secondAction);
        manualDataEntryPage().setStageX(2,secondStage);

        manualDataEntryPage().setDateX(3,thirdDate);
        manualDataEntryPage().setActionX(3,thirdAction);

        manualDataEntryPage().clickAddContent();

        boolean workflowReportingWindowAppeared = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowReportingWindowAppeared, "the workflow reporting window did not appear.");

        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus
                (workflowType, workflowContentSet, workflowDescription, username);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean newRenditionIsCreated = sourceNavigateGridPage().firstRenditionExists();
        if(newRenditionIsCreated)
        {
            renditionUuid = sourceNavigateGridPage().getUUID();
            sourceNavigateGridPage().rightClickFirstRendition();
            renditionContextMenu().viewRenditionXML();

            boolean renditionUuidPresent = sourceRawXmlEditorPage().isGivenTextInEditor("ID=\"" + renditionUuid + "\"" );
            boolean authorPresent = sourceRawXmlEditorPage().isGivenTextInEditor("<text>AUTHOR: " + sponsors + "</text>");
            boolean firstDateAndActionPresent = sourceRawXmlEditorPage().isGivenTextInEditor("<text>" + DateAndTimeUtils.getCurrentDateMMddyyyy()
                    + "  " + firstAction +"</text>");
            boolean firstStagePresent = sourceRawXmlEditorPage().isGivenTextInEditor("<text>" + firstStage + "</text>");
            boolean secondDateAndActionPresent = sourceRawXmlEditorPage().isGivenTextInEditor("<text>" + DateAndTimeUtils.getCurrentDateMMddyyyy()
                    + "  " + secondAction + "</text>");
            boolean secondStagePresent = sourceRawXmlEditorPage().isGivenTextInEditor("<text>" + secondStage + "</text>");
            boolean thirdDateAndActionPresent = sourceRawXmlEditorPage().isGivenTextInEditor("<text>" + DateAndTimeUtils.getCurrentDateMMddyyyy()
                    + "  " + thirdAction + "</text>");
            sourceRawXmlEditorPage().clickClose();

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(renditionUuidPresent, "Rendition Uuid is not present"),
                () -> Assertions.assertTrue(authorPresent, "The author is not present"),
                () -> Assertions.assertTrue(firstDateAndActionPresent, "The first action and date are not present"),
                () -> Assertions.assertTrue(firstStagePresent, "The first stage is not present"),
                () -> Assertions.assertTrue(secondDateAndActionPresent, "The second date and action are not present"),
                () -> Assertions.assertTrue(secondStagePresent, "The second stage is not present"),
                () -> Assertions.assertTrue(thirdDateAndActionPresent, "The third date and action")
            );
        }
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(workflowCompleted,String.format("The workflow %s didn't complete", workflowID)),
            () -> Assertions.assertTrue(newRenditionIsCreated,"The new rendition wasn't found in source navigate")
        );
    }

    @AfterEach
    public void deleteRendition()
    {
        sourceNavigateGridPage().setRenditionDeletedWithDatabase(renditionUuid, username);
    }
}
