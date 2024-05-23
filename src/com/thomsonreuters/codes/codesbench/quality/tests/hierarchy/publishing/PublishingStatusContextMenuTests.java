package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

public class PublishingStatusContextMenuTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-6801 <br>
     * SUMMARY - This test verifies that when users interact with a deleted node that has previously been published,
     * the Set publish ready context menu option should be available. When users interact with a deleted node that has
     * never been published, the Set publish ready context menu option should not be available. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void setPublishReadyDisabledForDeletedNodesNeverPublishedTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);

        // Get node attributes
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        String nodeStartDate = siblingMetadataPage().getSelectedNodeStartDate();

        // Fake a change so that the modified date of the node is updated
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpace();
        editorTextPage().breakOutOfFrame();

        editorToolbarPage().clickToolbarClose();
        editorClosurePage().selectQuickLoad();
        editorClosurePage().clickCheckInChanges();

        // Get node modified date so that we are able to select the node when it is deleted
        String nodeModifiedDate = siblingMetadataPage().getSelectedNodeModifiedDateMMDDYYYY();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsCloneAfter();
        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean updateMetadataWindowAppeared = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataWindowAppeared, "Update Metadata window did not appear");

        updateMetadataPage().enterEffectiveStartDate(nodeStartDate);
        updateMetadataPage().clickQuickLoadOk();

        // Delink the cloned node to clear warning flag
        dispositionDerivationPage().clickExpandButton();
        dispositionDerivationPage().rightClickSelectedNode();
        boolean delinkPreviousNodeWindowAppeared = dispositionDerivationContextMenu().delinkPreviousNode();
        Assertions.assertTrue(delinkPreviousNodeWindowAppeared, "Delink Previous Node window did not appear");

        delinkPreviousNodePage().selectFirstNode();
        delinkPreviousNodePage().clickQuickLoadOk();
        dispositionDerivationPage().clickExpandButton();

        // Delete both nodes
        siblingMetadataPage().selectNodesByValue(nodeValue);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();

        deletePage().clickQuickLoad();
        deletePage().clickSubmit();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        // Uncheck hide deleted and go to the deleted nodes
        hierarchyTreePage().uncheckHideDeletedForNavigationTree();

        siblingMetadataPage().selectDeletedNodeByValueAndModifiedDate(nodeValue, nodeModifiedDate);

        // Verify Set Publish Ready is enabled for the node that has been previously published
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().expandPublishingStatus();
        boolean setPublishReadyIsEnabled = !siblingMetadataContextMenu().isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_READY);

        siblingMetadataContextMenu().deleteFunctionsUndelete();

        undeletePage().pressQuickLoad();
        undeletePage().pressSubmit();

        // Verify Set Publish Ready is disabled for the node that has not been previously published
        siblingMetadataPage().selectSelectedNodesNextNode();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().expandPublishingStatus();
        boolean setPublishReadyIsDisabled = siblingMetadataContextMenu().isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_READY);

        // Verify the original node is undeleted and appears in the sibling metadata pane after hiding the deleted nodes
        hierarchyTreePage().checkHideDeletedForNavigationTree();
        String nodeValueAfterHidingDeleted = siblingMetadataPage().getSelectedNodeValue();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(setPublishReadyIsEnabled, "Set Publish Ready is disabled and should be enabled"),
            () -> Assertions.assertTrue(setPublishReadyIsDisabled, "Set Published Ready is enabled and should be disabled"),
            () -> Assertions.assertEquals(nodeValue, nodeValueAfterHidingDeleted, "Node was not correctly undeleted")
        );
    }

    /**
     * STORY/BUG - HALCYONST-8491, HALCYONST-7275 <br>
     * SUMMARY - This test verifies that changes made to a node in Hierarchy Navigate update the publishing status in Hierarchy Navigate,
     * but do not update the node's publishing status in Hierarchy Pub Navigate <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void displayOfPublishingStatusWipAndPubTest()
    {
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();
        String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToPubNavigate();

        hierarchySearchPage().searchContentUuid(contentUuid);

        boolean selectedNodeIsLoadedToWestlawStatusInHierarchyPubNavigate = siblingMetadataPage().isSelectedNodeWestlawLoadedStatusInPub();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchContentUuid(contentUuid);

        String nodeStartDate = siblingMetadataPage().getSelectedNodeStartDate();
        boolean selectedNodeIsLoadedToWestlawStatusInHierarchyNavigate = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(selectedNodeIsLoadedToWestlawStatusInHierarchyNavigate, "Node is not set to Loaded to Westlaw in Hierarchy Navigate and should be"),
            () -> Assertions.assertTrue(selectedNodeIsLoadedToWestlawStatusInHierarchyPubNavigate, "Node is not set to Loaded to Westlaw in Hierarchy Pub Navigate and should be")
        );

        // HALCYONST-7275
        dispositionDerivationPage().setStartDate(todaysDate);
        dispositionDerivationPage().clickCommit();

        setLawTrackingPage().clickQuickLoadTrackingButton();
        setLawTrackingPage().clickOkButton();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        boolean selectedNodeUpdatedToNotPublishedAfterChangingStartDate = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchyMenu().goToPubNavigate();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        boolean selectedNodeIsLoadedToWestlawStatusInHierarchyPubNavigateAfterChangingStartDate = siblingMetadataPage().isSelectedNodeWestlawLoadedStatusInPub();

        // HALCYONST-8491
        hierarchyMenu().goToNavigate();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setReadyPublishingStatus();

        boolean selectedNodeIsReadyStatusAfterUpdate = siblingMetadataPage().isSelectedNodeReadyStatus();

        hierarchyMenu().goToPubNavigate();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        boolean selectedNodeIsLoadedToWestlawStatusInHierarchyPubNavigateAfterChangingStatus = siblingMetadataPage().isSelectedNodeWestlawLoadedStatusInPub();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(selectedNodeUpdatedToNotPublishedAfterChangingStartDate, "After changing the nodes start date in Hierarchy Edit, the node did not update to not published and should have"),
            () -> Assertions.assertTrue(selectedNodeIsLoadedToWestlawStatusInHierarchyPubNavigateAfterChangingStartDate, "After changing the nodes start date in Hierarchy Edit, the node's publishing status changed from Loaded to Westlaw in Pub Navigate"),
            () -> Assertions.assertTrue(selectedNodeIsReadyStatusAfterUpdate, "After changing the nodes publishing status in Hierarchy Edit, the node did not update to set ready status and should have"),
            () -> Assertions.assertTrue(selectedNodeIsLoadedToWestlawStatusInHierarchyPubNavigateAfterChangingStatus, "After changing the nodes publishing status in Hierarchy Edit, the node's publishing status changed from Loaded to Westlaw in Pub Navigate")
        );
    }

    /**
     * STORY: HALCYONST-7919 <br>
     * SUMMARY: Verifies that a user who is on the publish approvers list should see the Set Publish Approved sub context menu
     * option. <br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishApproverSetPublishApprovedContextMenuOptionTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet( user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().expandPublishingStatus();

        boolean setPublishApprovedIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_APPROVED);
        Assertions.assertTrue(setPublishApprovedIsDisplayed, "Set Publish Approved sub context menu option is displayed on the Sibling Metadata page for a user listed as a publish approver.");
    }

    /**
     * STORY: HALCYONST-7919 <br>
     * SUMMARY: Verifies that a user who is not on the publish approvers list should not see the Set Publish Approved sub context menu
     * option. <br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void nonPublishApproverSetPublishApprovedContextMenuOptionTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().expandPublishingStatus();

        boolean setPublishApprovedIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_APPROVED);
        Assertions.assertFalse(setPublishApprovedIsDisplayed, "Set Publish Approved sub context menu option is displayed on the Sibling Metadata page for a user listed as a publish approver.");
    }
    /**
     * STORY: Halcyonst-5571, Halyconst-7838 <br>
     * SUMMARY: setting of Publish Ready, Publish Approve, Publish Complete and Hold Node statuses should all set the
     * specific Central Time zone time and date that the status was set.  <br>
     * USER: legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishingStatusesSetDateDatabaseTest()
    {
        //HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getAllNodes().get(0).getNodeUUID();
        String contentUuid = datapodObject.getAllNodes().get(0).getContentUUID();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid, contentSetIowa, uatConnection);

        //add approver from approved list via database
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        //log in with own user
        homePage().goToHomePage();
        loginPage().logIn();

        //Hierarchy -> Navigate
        hierarchyMenu().goToNavigate();

        //find target section or grade node
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        siblingMetadataContextMenu().setReadyPublishingStatus();
        String dateAndTimeForPublishStatusReady = DateAndTimeUtils.getCurrentDateInTimeYYYYMMDDHH();
        boolean verifyPublishingStatusOfReady = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(verifyPublishingStatusOfReady, "publishing status should be ready");

        //verify the PUB_READY_DATE column contains today's date and current time (central)
        String pubReadyDate = PublishingDatabaseUtils.getPublishingNodeReadyDate(contentUuid, contentSetIowa, uatConnection);

        //set the publish status of a greyNode or target section using set publish Approved
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        siblingMetadataContextMenu().setPublishApproved();
        /* When the approved status is set it will be set until the workflow finished at that point the workflow will be complete.
         This means it is hard to verify the status of Approved since it might have a chance of failing */
        String dateAndTimeForPublishStatusApproved = DateAndTimeUtils.getCurrentDateInTimeYYYYMMDDHH();

        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();

        toolsMenu().goToWorkflowReportingSystem();

        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowTimeAndStatus();
        Assertions.assertTrue(workflowFinished," workflow DOESN'T finish. Workflow ID:" + workflowId);
        String dateAndTimeForPublishStatusComplete = DateAndTimeUtils.getCurrentDateInTimeYYYYMMDDHH();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        //Refresh Toc_content_Publishing table verify PUB_APPROVED_DATE contains Date and Time (central)
        String pubApprovedDate = PublishingDatabaseUtils.getPublishingNodeApprovedDate(contentUuid, contentSetIowa, uatConnection);

        boolean verifyPublishingStatusOfComplete = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyNavigate();
        Assertions.assertTrue(verifyPublishingStatusOfComplete, "publishing status should be Complete");

        //refresh Toc_content_publishing table to verify PUB_COMPLETE_DATE column contains date and central time zone
        String pubCompleteDate = PublishingDatabaseUtils.getPublishingNodeCompleteDate(contentUuid, contentSetIowa, uatConnection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(pubCompleteDate.contains(dateAndTimeForPublishStatusComplete),"PUB_COMPLETE_DATE is not correct"),
            () -> Assertions.assertTrue(pubApprovedDate.contains(dateAndTimeForPublishStatusApproved),"PUB_READY_DATE is not correct" ),
            () -> Assertions.assertTrue(pubReadyDate.contains(dateAndTimeForPublishStatusReady),"PUB_APPROVED_DATE is not correct: ")
        );
    }

    /**
     * STORY: Haclcyonst-8078 <br>
     * SUMMARY: when a grade node version is created the statuses of both versions correctly gets changed to Not Published,
     * but all of the children of the grade node are also getting their statuses changed to Not Published and they should not
     * have their own publishing statuses changed because of the change in the Parent. When a grade node version is created,
     * only the publishing statuses of each grade node should change.  <br>
     * USER: legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void gradeNodeVersioningSetsAllChildrenToNotPublished()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getChapters().get(0).getNodeUUID();
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String treeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid, ContentSets.IOWA_DEVELOPMENT.getCode(), uatConnection);

        String childContentUuid1 = datapodObject.getSections().get(0).getContentUUID();
        String childContentUuid2 = datapodObject.getSections().get(1).getContentUUID();
        String childContentUuid3 = datapodObject.getSections().get(2).getContentUUID();

        String childValue1 = HierarchyDatabaseUtils.getNodeValue(childContentUuid1, ContentSets.IOWA_DEVELOPMENT.getCode(), uatConnection);
        String childValue2 = HierarchyDatabaseUtils.getNodeValue(childContentUuid2, ContentSets.IOWA_DEVELOPMENT.getCode(), uatConnection);
        String childValue3 = HierarchyDatabaseUtils.getNodeValue(childContentUuid3, ContentSets.IOWA_DEVELOPMENT.getCode(), uatConnection);

        String[] childrenValues = {childValue1, childValue2, childValue3};


        String yesterdaysDate = DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros();

        PublishingDatabaseUtils.setPublishingNodeToReady(childContentUuid1, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childContentUuid2, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childContentUuid3, contentSetIowa, uatConnection);

        //log in with Legal User
        homePage().goToHomePage();
        loginPage().logIn();

        //Hierarchy -> Navigate
        boolean hiearchyNavigateAppeared = hierarchyMenu().goToNavigate();
        Assertions.assertTrue(hiearchyNavigateAppeared, "Hiearchy Navigate page did not appear");

        //Find Node with UUID
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedGridRowValue();

        //expand tree
        hierarchyTreePage().clickExpandButtonNextToGivenValue(treeValue);

        //verify children has status other then Not Published
        hierarchyTreePage().getNodeValueDownSelectedTreeNode();

        siblingMetadataPage().selectNodes(childrenValues[0]);
        boolean nodeChild1Status = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertFalse(nodeChild1Status, "Status should be something other then not published for child 1");

        siblingMetadataPage().selectNodes(childrenValues[1]);
        boolean nodeChild2StatusISNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertFalse(nodeChild2StatusISNotPublished, "Status should be something other then not published for child 2");

        siblingMetadataPage().selectNodes(childrenValues[2]);
        boolean nodeChild3StatusISNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertFalse(nodeChild3StatusISNotPublished, "Status should be something other then not published for child 3");

        //Right click searched NOde and Go to Edit content
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //Fake change and check in changes
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpaceRunningHeadBlock();
        editorPage().closeAndCheckInChangesWithGivenDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,
                String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s.",nodeUuid,yesterdaysDate));
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");

        //Verify two nodes now exist with that value
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //get the new nodes uuid
        hierarchyTreePage().setNavigationTreeToCurrentDate();
        siblingMetadataPage().selectNodes(nodeValue);

        //verify node with start date of today has correct status
        hierarchyTreePage().clickExpandButtonNextToGivenValue(treeValue);
        boolean nodeWithStartDateStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        //verify children has status other then Not Published
        hierarchyTreePage().getNodeValueDownSelectedTreeNode();
        siblingMetadataPage().selectNodes(childrenValues[0]);
        boolean nodeChild1StatusOtherThenNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().selectNodes(childrenValues[1]);
        boolean nodeChild2StatusOtherThenNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().selectNodes(childrenValues[2]);
        boolean nodeChild3StatusOtherThenNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeWithStartDateStatus, "Status should Not Published"),
            () -> Assertions.assertFalse(nodeChild1StatusOtherThenNotPublished, "Status should be something other then not published for child 1"),
            () -> Assertions.assertFalse(nodeChild2StatusOtherThenNotPublished, "Status should be something other then not published for child 2"),
            () -> Assertions.assertFalse(nodeChild3StatusOtherThenNotPublished, "Status should be something other then not published for child 3")
        );
    }


    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}