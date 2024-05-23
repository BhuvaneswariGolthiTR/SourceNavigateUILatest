package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishreadytextnodesonly;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishReadyTextNodesOnlyMultipleNodesSelectionTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY: Halyconst-8308 <br>
     * SUMMARY: On the Publish Ready UI, on either of the Publish Approve UIs, or on the Pub Navigate Troubleshooting UI,
     * when selecting/deselecting a node with the context menus on either the Selection or Submission pages, if that node
     * occurs under multiple parents in the UI, then all instances of the same node should also be selected/deselected.  <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void selectionAndDeselectionOfMultipleNodes()
    {
        String keyword = "= ";
        String nodeUuid = "IDEE5201014F211DA8AC5CD53670E6B4E";
        String treeValue = "Ch. 216E ASSISTIVE DEVICES(7)";
        String[] childValues = {"216E.1", "216E.2", "216E.3", "216E.4", "216E.5", "216E.6", "216E.7"};

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedGridRowValue();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //Fake change and check in changes
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpace();
        editorPage().closeAndCheckInChangesWithGivenDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,
                String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s.",nodeUuid, DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros()));
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");

        //Verify two nodes now exist with that value
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyTreePage().setNavigationTreeToCurrentDate();
        siblingMetadataPage().selectNodes(nodeValue);

        siblingMetadataPage().updateSelectedSiblingMetadata();
        String newNodeUuid = updateMetadataPage().getNodeUuid().toUpperCase();
        updateMetadataPage().clickCancel();

        //expand tree
        hierarchyTreePage().clickExpandButtonNextToGivenValue(treeValue);

        //verify children have a status of not published if not set them to that
        //this gets the children for the new node
        hierarchyTreePage().getNodeValueDownSelectedTreeNode();
        siblingMetadataPage().selectNodes(childValues[0]);
        siblingMetadataPage().setNotPublishedStatusForSelectedNode();

        siblingMetadataPage().selectNodes(childValues[1]);
        siblingMetadataPage().setNotPublishedStatusForSelectedNode();

        boolean twoStepApprovalPageLoaded = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(twoStepApprovalPageLoaded, "Publish Ready-Text nodes only page loaded");
        gridPage().waitForGridLoaded();

        //add sort and filter to any column
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(childValues[0]);

        //left click one child and assert that we see the same child from the other parent is selected
        gridPage().selectNodebyHierarchyColumnValue(keyword + childValues[0]);
        boolean areBothChildrenSelected = gridPage().isChildFromBothParentsSelected(keyword + childValues[0]);
        Assertions.assertTrue(areBothChildrenSelected ,"Both children were not selected");

        //Set Ready status
        gridPage().rightClickMultipleSelected();
        gridContextMenu().readyStatus();
        boolean verifyHighlightedGreen = gridPage().isChildFromBothParentsHighlightedGreen(keyword + childValues[0]);

        //click next and verify selected nodes are shown
        toolbarPage().clickNext();
        boolean selectedNodeShowsUpInSubmissionPage = gridPage().isNodeHierarchyColumnValueInGrid(keyword + childValues[0]);

        // close toolbox and right click node with date of today and delete
        toolbarPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        /*
         * clean up steps have been changes to fit the ones Mike gave us, reason for this being is because The nodes children we " deleted" were appearing when
         * they should not have been.
         */
        siblingMetadataPage().selectNodes(childValues);

        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        /*
         * Note: This switch to page is here as a fix for a werid bug with the navigate page that appears after searching: this has been looked into
         * and is not a bug in codes bench. It only appears when running this test here.
         */
        hierarchySearchPage().searchNodeUuid(newNodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, DateAndTimeUtils.getCurrentDateMMddyyyy());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsRemoveBinAsChild();
        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //now its safe to go in a delete that node
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();
        deletePage().clickQuickLoad();
        deletePage().clickSubmit();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyTreePage().setNavigationTreeToYesterdaysDate();

        // right click node and go to update metadata to remove the end date of this node
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean updateMetadataWindowAppeared = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataWindowAppeared, "Update Metadata window did not appear");
        updateMetadataPage().clearEffectiveEndDate();
        updateMetadataPage().clickQuickLoadOk();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(areBothChildrenSelected ,"Both children were not selected"),
            () ->  Assertions.assertTrue(verifyHighlightedGreen ,"Both Children were not highlighted green"),
            () -> Assertions.assertTrue(selectedNodeShowsUpInSubmissionPage, "The selected node was not shown in the submission page")
        );

    }

    /**
     * STORY: HALCYONST-10494 <br>
     * SUMMARY: This test verifies that when multi selecting nodes that span across different parents, if the nodes within the selection are
     * not locked and do not have error flags, the first two context menu options on the publishing UIs should be enabled <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleNodeSelectionAcrossParentsPositiveTest()
    {
        String childNodeOfParent1ContentUuid = "I428329601AF611DAB310FB76B2E4F553";
        String childNodeOfParent2ContentUuid = "I78AB09D009E811DC8EE5B07DC6DF8E8D";
        String code = "TITLE IV ";
        String sectionKeyword = "= ";
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();

        // section node mock up
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent1ContentUuid, contentSetIowa, connection);
        String childNode1OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent1ContentUuid, contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent2ContentUuid, contentSetIowa, connection);
        String childNode2OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent2ContentUuid, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();
        // 2-Step Approval - Ready Selection UI Verification
        boolean toolboxWindowAppeared = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Ready-Text nodes only window did not appear and should have");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(code);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(sectionKeyword + childNode1OfParentValue, sectionKeyword + childNode2OfParentValue);

        gridPage().selectAllNodesBetweenTwoNodeHierarchyColumnValues(sectionKeyword + childNode1OfParentValue, sectionKeyword + childNode2OfParentValue);
        gridPage().rightClickMultipleSelectedNodes();

        boolean readyStatusContextMenuOptionIsEnabled = gridContextMenu().isElementEnabled(GridContextMenuElements.READY_STATUS_XPATH);
        boolean removeReadyStatusContextMenuOptionIsEnabled = gridContextMenu().isElementEnabled(GridContextMenuElements.REMOVE_READY_STATUS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(readyStatusContextMenuOptionIsEnabled, "On the 2-Step Approval - Ready Selection UI, the Ready Status context menu option is disabled and should be enabled"),
            () -> Assertions.assertTrue(removeReadyStatusContextMenuOptionIsEnabled, "On the 2-Step Approval - Ready Selection UI, the Remove Ready Status context menu option is disabled and should be enabled")
        );
    }

    /**
     * STORY: HALCYONST-10494 <br>
     * SUMMARY: This test verifies that when multi selecting nodes that span across different parents, if any of the nodes within the selection have an error flag,
     * the first two context menu options on the publishing UIs should be disabled <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleNodeSelectionAcrossParentsErrorFlagTest()
    {
        String childNodeOfParent1ContentUuid = "I428329601AF611DAB310FB76B2E4F553";
        String childNodeOfParent2ContentUuid = "I78AB09D009E811DC8EE5B07DC6DF8E8D";
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();

        String codeName = HierarchyDatabaseUtils.getNodeCodeName(childNodeOfParent1ContentUuid, connection);

        // section node error flag mock up
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent1ContentUuid, contentSetIowa, connection);
        HierarchyDatabaseUtils.setNodeToErrorValidationFlag(childNodeOfParent1ContentUuid, contentSetIowa, connection);
        String childNode1OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent1ContentUuid, contentSetIowa, connection);

        // other section node mock up
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent2ContentUuid, contentSetIowa, connection);
        String childNode2OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent2ContentUuid, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        // 2-Step Approval - Ready Selection UI Verification
        boolean toolboxWindowAppeared = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Ready-Text nodes only window did not appear and should have");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(codeName);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(childNode1OfParentValue, childNode2OfParentValue);

        gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(childNode1OfParentValue, childNode2OfParentValue);
        gridPage().rightClickMultipleSelectedNodes();

        boolean readyStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.READY_STATUS_XPATH);
        boolean removeReadyStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.REMOVE_READY_STATUS_XPATH);

        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(childNodeOfParent1ContentUuid, contentSetIowa, connection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(readyStatusContextMenuOptionIsDisabled, "On the 2-Step Approval - Ready Selection UI, the Ready Status context menu option is enabled and should be disabled"),
            () -> Assertions.assertTrue(removeReadyStatusContextMenuOptionIsDisabled, "On the 2-Step Approval - Ready Selection UI, the Remove Ready Status context menu option is enabled and should be disabled")
        );
    }

    /**
     * STORY: HALCYONST-10494 <br>
     * SUMMARY: This test verifies that when multi selecting nodes that span across different parents, if any of the nodes within the selection are locked,
     * the first two context menu options on the publishing UIs should be disabled <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleNodeSelectionAcrossParentsLockedNodeTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String lockUuid = CommonDataMocking.generateUUID();
        String todaysDate = DateAndTimeUtils.getCurentDateDDDashMMMDashYY();
        String legalUser = user().getUsername();
        String volume = "9999";
        String node1Value = "val1";
        String node2Value = "val2";

        //Mock up 2 bl nodes in different chapters
        HierarchyDatapodConfiguration.getConfig().setChapterCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        //section node 1 variables
        String node1NodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String node1ContentUuid = datapodObject.getSections().get(0).getContentUUID();
        String node1Volume = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(node1ContentUuid, contentSetIowa, connection);
        HierarchyDatabaseUtils.setNodeValue(connection, node1NodeUuid, node1Value);

        //section node 2 variables and change value to be different than bl node 1
        String node2NodeUuid = datapodObject.getSections().get(1).getNodeUUID();
        String node2ContentUuid = datapodObject.getSections().get(1).getContentUUID();
        HierarchyDatabaseUtils.setNodeValue(connection, node2NodeUuid, node2Value);

        //Set nodes to 'not published' so they appear in nod publish approve ui
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(node1ContentUuid, contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(node2ContentUuid, contentSetIowa, connection);

        // Set a lock on bl node 1
        HierarchyDatabaseUtils.insertNodeIntoTocNodeLockTable(lockUuid, node1Value, 1, legalUser, todaysDate, 2, node1Volume, node1NodeUuid, connection);
        HierarchyDatabaseUtils.insertNodeIntoLockedTocNodeTable(node1NodeUuid, lockUuid, connection);

        // Go to One-Step Publishing Publish Approve and filter for the mocked nodes
        homePage().goToHomePage();
        loginPage().logIn();
        boolean toolboxWindowAppeared = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Ready-Text nodes only window did not appear and should have");
        gridPage().waitForGridLoaded();
        gridHeaderPage().openMenuForVolColumn();
        gridHeaderFiltersPage().setFilterValue(volume);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(node1Value, node2Value);

        //UI selection across parents test
        gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(node1Value, node2Value);
        boolean areNodesSelected = gridPage().isMultipleNodesSelected(node1Value, node2Value);
        Assertions.assertTrue(areNodesSelected, "The nodes are not selected when they should be");
        gridPage().rightClickMultipleSelectedNodes();
        boolean approvedStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.READY_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.REMOVE_READY_STATUS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsDisabled, "On the NOD Publishing Only UI, the Approved Status context menu option is enabled and should be disabled"),
            () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsDisabled, "On the NOD Publishing Only UI, the Remove Approved Status context menu option is enabled and should be disabled")
        );
    }

    /**
     * STORY/BUG - HALCYONST-8264, HALCYONST-9543 <br>
     * SUMMARY - This test verifies that all of the sub context menu options under the 'Mass Selection' option are working correctly in each main Publishing UI<br>
     * USER - CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void massSelectionFunctionalityTest()
    {
        //2 nodes with same code and volume
        String contentUuid = "I7016EA201B0411DAB311FB76B2E4F553";
        String contentUuid2 = "I702590201B0411DAB311FB76B2E4F553";
        //node from different volume and code
        String contentUuid3 = "I4300B8A01B0811DAB311FB76B2E4F553";

        String originalStartDate = "31-DEC-05";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        String citelineUser = user().getPublishingToolboxUsername();

        connection = BaseDatabaseUtils.connectToDatabaseUAT();

        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, connection);
        String nodeValue2 = HierarchyDatabaseUtils.getNodeValue(contentUuid2, contentSetIowa, connection);
        String nodeValue3 = HierarchyDatabaseUtils.getNodeValue(contentUuid3, contentSetIowa, connection);
        String[] nodeValues = {nodeValue, nodeValue2, nodeValue3};

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid3, contentSetIowa, connection);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();

            hierarchyMenu().goToNavigate();
            hierarchySearchPage().searchContentUuid(contentUuid);
            siblingMetadataPage().updateSelectedSiblingMetadata();
            updateMetadataPage().clearAndEnterStartDate(currentDate);
            updateMetadataPage().clickQuickLoadOk();

            hierarchyNavigatePage().switchToHierarchyEditPage();
            boolean selectedNodeIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
            Assertions.assertTrue(selectedNodeIsSetToNotPublished, "The selected node should have a publishing status of 'Not Published' but does not");

            hierarchySearchPage().searchContentUuid(contentUuid2);
            siblingMetadataPage().updateSelectedSiblingMetadata();
            updateMetadataPage().clearAndEnterStartDate(currentDate);
            updateMetadataPage().clickQuickLoadOk();

            hierarchyNavigatePage().switchToHierarchyEditPage();
            boolean selectedNodeIsSetToNotPublished2 = siblingMetadataPage().isSelectedNodeStatusNotPublished();
            Assertions.assertTrue(selectedNodeIsSetToNotPublished2, "The selected node should have a publishing status of 'Not Published' but does not");

            //Filter for nodes we are checking
            publishingMenu().goToPublishReadyTextNodesOnly();
            gridPage().waitForGridLoaded();
            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValues);

            gridPage().selectByNodeTargetValue(nodeValue3);
            String node3ModifiedUser = gridPage().getSelectedNodesModifiedByUsername();
            String node3LawTracking = gridPage().getSelectedNodesLawTrackingStatus();
            boolean modifiedUsersAreDifferent = !node3ModifiedUser.equals(citelineUser);
            boolean lawTrackingIsCorrect = !node3LawTracking.equals("Quick Load");
            Assertions.assertTrue(modifiedUsersAreDifferent, "The third node should have a different Modified By user than the other two nodes but does not. You will have to change this for the third node manually");
            Assertions.assertTrue(lawTrackingIsCorrect, "The third node should have a different law tracking status than the other two nodes but does not. You will have to change the third node's law tracking manually");

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionReadyStatusAllDocumentsInSameVolume();
            boolean node1IsHighlightedGreenForSameVolume = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsHighlightedGreenForSameVolume = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue3);
            boolean node3IsNotHighlightedGreenForSameVolume = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveReadyStatusAllDocumentsInSameVolume();
            boolean node1IsNotHighlightedGreenAfterRemovingReadyStatus = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingReadyStatus = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionReadyStatusAllDocumentsWithSameCode();
            boolean node1IsHighlightedGreenForSameCode = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsHighlightedGreenForSameCode = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue3);
            boolean node3IsNotHighlightedGreenForSameCode = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveReadyStatusAllDocumentsWithSameCode();
            boolean node1IsNotHighlightedGreenAfterRemovingReadyStatus2 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingReadyStatus2 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionReadyStatusAllDocumentsModifiedBySameUser();
            boolean node1IsHighlightedGreenForSameModifiedByUser = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsHighlightedGreenForSameModifiedByUser = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue3);
            boolean node3IsNotHighlightedGreenForSameModifiedUser = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveReadyStatusAllDocumentsModifiedBySameUser();
            boolean node1IsNotHighlightedGreenAfterRemovingReadyStatus3 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingReadyStatus3 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionReadyStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad();
            boolean node1IsHighlightedGreenForSameLawTracking = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsHighlightedGreenForSameLawTracking = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue3);
            boolean node3IsNotHighlightedGreenForSameLawTracking = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveReadyStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad();
            boolean node1IsNotHighlightedGreenAfterRemovingReadyStatus4 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingReadyStatus4 = !gridPage().isBackgroundGreenForSelectedRow();

            Assertions.assertAll
                (
                    //2-Step Approval - Ready Selection
                    () -> Assertions.assertTrue(node1IsHighlightedGreenForSameVolume, "The selected node should have a dark green background but does not"),
                    () -> Assertions.assertTrue(node2IsHighlightedGreenForSameVolume, "The selected node should have a dark green background but does not"),
                    () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameVolume, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingReadyStatus, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingReadyStatus, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node1IsHighlightedGreenForSameCode, "The selected node should have a dark green background but does not"),
                    () -> Assertions.assertTrue(node2IsHighlightedGreenForSameCode, "The selected node should have a dark green background but does not"),
                    () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameCode, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingReadyStatus2, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingReadyStatus2, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node1IsHighlightedGreenForSameModifiedByUser, "The selected node should have a dark green background but does not"),
                    () -> Assertions.assertTrue(node2IsHighlightedGreenForSameModifiedByUser, "The selected node should have a dark green background but does not"),
                    () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameModifiedUser, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingReadyStatus3, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingReadyStatus3, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node1IsHighlightedGreenForSameLawTracking, "The selected node should have a dark green background but does not"),
                    () -> Assertions.assertTrue(node2IsHighlightedGreenForSameLawTracking, "The selected node should have a dark green background but does not"),
                    () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameLawTracking, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingReadyStatus4, "The selected node should not have a dark green background but does"),
                    () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingReadyStatus4, "The selected node should not have a dark green background but does")
                );
        }
        finally
        {
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid, originalStartDate, connection);
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid2, originalStartDate, connection);
        }
    }

    @AfterEach
    public void cleanUp()
    {
        if(connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
