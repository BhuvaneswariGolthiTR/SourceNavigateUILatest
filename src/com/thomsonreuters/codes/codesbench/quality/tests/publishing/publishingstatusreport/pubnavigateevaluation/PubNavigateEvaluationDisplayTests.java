package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.pubnavigateevaluation;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PubNavigateEvaluationDisplayTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY/BUG - HALCYONST-7728, 15401<br>
     * SUMMARY - The test is essentially testing whether edits made in Hierarchy will remove the nodes from the Publishing In Progress and Load Complete UI.
     * What we expect to see is that the nodes will stay in the UIs despite making updates in Hierarchy Navigate. <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void wipChangesAffectingPubTest()
    {
        String nodeUuid = "ID43DBA6014F111DA8AC5CD53670E6B4E";

        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String yesterdaysDate = DateAndTimeUtils.getYesterdayDateMMddyyyy();
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Copy node as sibling
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        String nodeStartDate = siblingMetadataPage().getSelectedNodeStartDate();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsCopyBinAsSibling();
        copyPage().setCreateCopyOfNODs();
        copyPage().clickQuickLoadOk();

        //Check new node has correct data for test
        boolean copiedNodeIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean copiedNodeHasNoEndDate = siblingMetadataPage().checkSelectedNodesEndDateEqualsNoDate();
        boolean copiedNodeHasNoStartDate = siblingMetadataPage().getSelectedNodeStartDate().equals("no date");

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(copiedNodeIsSetToNotPublished,"The copied node should have a publishing status of 'Not Published' but does not"),
            () -> Assertions.assertTrue(copiedNodeHasNoEndDate,"The copied node should have no end date but does"),
            () -> Assertions.assertTrue(copiedNodeHasNoStartDate,"The copied node should have no start date but does")
        );

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean updateMetadataWindowOpens = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataWindowOpens,"The 'Update Metadata' page did not appear when it should");

        //Set start date for copied node to get rid of error flag
        String copiedNodeUuid = updateMetadataPage().getNodeUuid().toUpperCase();
        updateMetadataPage().clearAndEnterStartDate(currentDate);
        updateMetadataPage().clickQuickLoadOk();

        //Set end date for original node to get a green checkmark validation flag for copied node
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, nodeStartDate);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean updateMetadataWindowOpens2 = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataWindowOpens2,"The 'Update Metadata' page did not appear when it should");

        updateMetadataPage().enterEffectiveEndDate(yesterdaysDate);
        updateMetadataPage().clickQuickLoadOk();
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, currentDate);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorToolbarPage().clickPubtagRefreshButton();
        boolean documentIsValid = !editorToolbarPage().clickValidate();

        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        Assertions.assertTrue(documentIsValid,"The document is invalid when it should be valid"); //This assertion is put here because if it was any earlier, test would fail out and node would be locked

        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, nodeStartDate);
        siblingMetadataPage().selectSelectedNodesNextNode();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean updateMetadataWindowOpens3 = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataWindowOpens3,"The 'Update Metadata' page did not appear when it should");

        String copiedNodeContentUuid = updateMetadataPage().getContentUuid().toUpperCase();
        updateMetadataPage().clickCancel();

        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(copiedNodeContentUuid, contentSetIowa, uatConnection);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        String validationOfSelectedNode = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean copiedNodeHasGreenCheckmark = validationOfSelectedNode.equals("check");
        Assertions.assertTrue(copiedNodeHasGreenCheckmark,"The copied node should have a flag status of a green checkmark, but does not");

        //Set copied node to Approved status
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setPublishApproved();

        //Check that workflow finishes
        yourWorkflowHasBeenCreatedPage().clickLink();
        String workflowId = workflowDetailsPage().getWorkflowID();
        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(workflowFinished,"The workflow did not finish: " + workflowId);

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        yourWorkflowHasBeenCreatedPage().enterTheInnerFrame();
        yourWorkflowHasBeenCreatedPage().clickClose();
        yourWorkflowHasBeenCreatedPage().breakOutOfFrame();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Copied node publishing status is set to 'Published to Pub' and set status to 'Hold' in Pub Navigate page
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, currentDate);
        boolean copiedNodeIsSetToPubComplete = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyNavigate();
        Assertions.assertTrue(copiedNodeIsSetToPubComplete,"The copied node should have a publishing status of 'Published to Pub'");

        hierarchyMenu().goToPubNavigate();
        String selectedNodeValue = siblingMetadataPage().getSelectedNodeValue();
        String selectedNodeStartDate = siblingMetadataPage().getSelectedNodeStartDate();
        boolean pubNavigateGoesToCopiedNode = selectedNodeValue.equals(nodeValue) && selectedNodeStartDate.equals(currentDate);
        Assertions.assertTrue(pubNavigateGoesToCopiedNode,"Going to Hierarchy > Pub Navigate does not bring us to the copied node when it should");

        boolean copiedNodeIsSetToPubCompleteInPubNavigate = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyPubNavigate();
        Assertions.assertTrue(copiedNodeIsSetToPubCompleteInPubNavigate,"The copied node should have a publishing status of 'Published to Pub'");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setHoldNode();
        boolean copiedNodeIsSetToHoldInPubNavigate = siblingMetadataPage().isSelectedNodeHoldNodeStatus();
        Assertions.assertTrue(copiedNodeIsSetToHoldInPubNavigate,"The copied node should have a publishing status of 'Hold'");

        //Open Publishing UI and check that copied node appears
        boolean toolboxPageAppears = publishingMenu().goToPublishingStatusReportsPubNavigateEvaluation();
        Assertions.assertTrue(toolboxPageAppears,"The Toolbox page does not appear when it should have");

        gridPage().waitForGridLoaded();
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(copiedNodeUuid);
        boolean copiedNodeAppears = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue);
        Assertions.assertTrue(copiedNodeAppears,"The copied node did not appear in the grid when it should have");

        //Check copied node is still set to 'Published to Pub', then set it to 'Not Published' and verify the 'Hold' status remains
        hierarchyPubNavigatePage().switchToPubNavigatePage();
        hierarchyMenu().goToNavigate();
        boolean copiedNodeIsSetToPubComplete2 = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyNavigate();
        Assertions.assertTrue(copiedNodeIsSetToPubComplete2,"The copied node should have a publishing status of 'Published to Pub'");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();

        boolean copiedNodeIsSetToNotPublished2 = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertTrue(copiedNodeIsSetToNotPublished2,"The copied node should have a publishing status of 'Not Published'");

        hierarchyMenu().goToPubNavigate();
        boolean copiedNodeIsSetToHoldInPubNavigate2 = siblingMetadataPage().isSelectedNodeHoldNodeStatus();
        Assertions.assertTrue(copiedNodeIsSetToHoldInPubNavigate2,"The copied node should have a publishing status of 'Hold'");

        //Check that the copied node still appears in the Publishing UI
        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickRequeryAndReloadFromDatabase();
        toolbarPage().clickRefreshCurrentGrid();
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(copiedNodeUuid);
        boolean copiedNodeAppears2 = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue);
        Assertions.assertTrue(copiedNodeAppears2,"The copied node did not appear in the grid when it should have");

        hierarchyPubNavigatePage().switchToPubNavigatePage();
        hierarchyMenu().goToNavigate();

        //Set copied node to 'Ready' and verify the 'Hold' status remains
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setReadyPublishingStatus();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean copiedNodeIsSetToReady = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(copiedNodeIsSetToReady,"The copied node should be set to 'Ready' publishing status but is not");

        hierarchyMenu().goToPubNavigate();
        boolean copiedNodeIsSetToHoldInPubNavigate3 = siblingMetadataPage().isSelectedNodeHoldNodeStatus();
        Assertions.assertTrue(copiedNodeIsSetToHoldInPubNavigate3,"The copied node should have a publishing status of 'Hold'");

        //Check that the copied node still appears in the Publishing UI
        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickRequeryAndReloadFromDatabase();
        toolbarPage().clickRefreshCurrentGrid();
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(copiedNodeUuid);
        boolean copiedNodeAppears3 = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue);
        Assertions.assertTrue(copiedNodeAppears3,"The copied node did not appear in the grid when it should have");

        //Release hold on copied node and verify that the node no longer appears in the Publishing UI
        hierarchyPubNavigatePage().switchToPubNavigatePage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().releaseHold();

        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickRequeryAndReloadFromDatabase();
        toolbarPage().clickRefreshCurrentGrid();
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(copiedNodeUuid);
        boolean copiedNodeDoesNotAppear = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue);      //Halcyonst-15401 add pub complete nodes to the Pub Navigate ui. changed this to reflect that
        Assertions.assertTrue(copiedNodeDoesNotAppear,"The copied node did not appear in the grid when it should not have");

        //Clean up data by deleting copied node
        mainHeaderPage().closeCurrentWindowIgnoreDialogue();
        hierarchyPubNavigatePage().switchToPubNavigatePage();
        hierarchyMenu().goToNavigate();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();
        deletePage().clickQuickLoad();
        deletePage().clickSubmit();

        //Continue to clean up data by clearing end date for original node
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, nodeStartDate);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().clearEffectiveEndDate();
        updateMetadataPage().clickQuickLoadOk();
    }

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void validationFlagCheck()
    {
        String greenCheckmarkStatus = "check";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String newChapterNodeValue = "TEST" + currentDate;
        String nodeUuuid = "IC659BCC014EF11DA8AC5CD53670E6B4E";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuuid);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();

        insertNewNodesPage().setValueByRowNumber(newChapterNodeValue, 1);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow(currentDate, 1);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();

        String newChapterUuid = updateMetadataPage().getNodeUuid();
        updateMetadataPage().clickCancel();

        hierarchySearchPage().searchNodeUuid(newChapterUuid.toUpperCase());
        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlagWithGivenNodeUuid(newChapterUuid.toUpperCase(), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        //Check that section node is set to 'Not Published' and has a green checkmark validation flag
        String validationFlagOfSelectedSectionNode = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean doesNewSectionNodeHaveAGreenCheckmarkFlag = validationFlagOfSelectedSectionNode.equals(greenCheckmarkStatus);
        Assertions.assertTrue(doesNewSectionNodeHaveAGreenCheckmarkFlag, "The newly created section node should have a green checkmark validation flag but does not");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();

        deletePage().clickQuickLoad();
        deletePage().clickSubmit();
        hierarchyNavigatePage().switchToHierarchyEditPage();
    }


    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
