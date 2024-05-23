package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextnodesonly;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
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

public class PublishApproveTextNodesOnlyMultipleNodeSelectionTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

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

        PublishingDatabaseUtils.setPublishingNodeToReady(childNodeOfParent1ContentUuid, contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childNodeOfParent2ContentUuid, contentSetIowa, connection);

        // 1-Step Approval - Approve Selection UI Verification
        boolean toolboxWindowAppeared = publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text nodes only window did not appear and should have");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(code);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(sectionKeyword + childNode1OfParentValue, sectionKeyword + childNode2OfParentValue);

        gridPage().selectAllNodesBetweenTwoNodeHierarchyColumnValues(sectionKeyword + childNode1OfParentValue, sectionKeyword + childNode2OfParentValue);
        gridPage().rightClickMultipleSelectedNodes();

        boolean approvedStatusContextMenuOptionIsEnabledOn1Step = gridContextMenu().isElementEnabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsEnabledOn1Step = gridContextMenu().isElementEnabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsEnabledOn1Step, "On the 1-Step Approval - Approve Selection UI, the Approved Status context menu option is disabled and should be enabled"),
            () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsEnabledOn1Step, "On the 1-Step Approval - Approve Selection UI, the Remove Approved Status context menu option is disabled and should be enabled")
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
        boolean toolboxWindowAppeared;

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

        // 1-Step Approval - Approve Selection UI Verification
        toolboxWindowAppeared = publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text nodes only window did not appear and should have");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(codeName);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(childNode1OfParentValue, childNode2OfParentValue);

        gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(childNode1OfParentValue, childNode2OfParentValue);
        gridPage().rightClickMultipleSelectedNodes();

        boolean approvedStatusContextMenuOptionIsDisabledOn1Step = gridContextMenu().isElementDisabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsDisabledOn1Step = gridContextMenu().isElementDisabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(childNodeOfParent1ContentUuid, contentSetIowa, connection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsDisabledOn1Step, "On the Publish Approve-Text nodes only, the Approved Status context menu option is enabled and should be disabled"),
            () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsDisabledOn1Step, "On the Publish Approve-Text nodes only UI, the Remove Approved Status context menu option is enabled and should be disabled")
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
        boolean toolboxWindowAppeared = publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text nodes only window did not appear and should have");
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
        boolean approvedStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsDisabled, "Publish Approve-Text nodes only UI, the Approved Status context menu option is enabled and should be disabled"),
            () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsDisabled, "Publish Approve-Text nodes only UI, the Remove Approved Status context menu option is enabled and should be disabled")
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
            publishingMenu().goToPublishingPublishApproveTextNodesOnly();
            gridPage().waitForGridLoaded();
            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValues);

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsInSameVolume();
            boolean node1IsHighlightedGreenForSameVolume = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsHighlightedGreenForSameVolume = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue3);
            boolean node3IsNotHighlightedGreenForSameVolume = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsInSameVolume();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsWithSameCode();
            boolean node1IsHighlightedGreenForSameCode = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsHighlightedGreenForSameCode = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue3);
            boolean node3IsNotHighlightedGreenForSameCode = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsWithSameCode();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus2 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus2 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsModifiedBySameUser();
            boolean node1IsHighlightedGreenForSameModifiedByUser = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsHighlightedGreenForSameModifiedByUser = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue3);
            boolean node3IsNotHighlightedGreenForSameModifiedUser = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsModifiedBySameUser();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus3 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus3 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad();
            boolean node1IsHighlightedGreenForSameLawTracking = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsHighlightedGreenForSameLawTracking = gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue3);
            boolean node3IsNotHighlightedGreenForSameLawTracking = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus4 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().selectByNodeTargetValue(nodeValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus4 = !gridPage().isBackgroundGreenForSelectedRow();

            Assertions.assertAll
            (
                //1-Step Approval - Approve Selection
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameVolume, "The selected node should have a dark green background but does not"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameVolume, "The selected node should have a dark green background but does not"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameVolume, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameCode, "The selected node should have a dark green background but does not"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameCode, "The selected node should have a dark green background but does not"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameCode, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus2, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus2, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameModifiedByUser, "The selected node should have a dark green background but does not"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameModifiedByUser, "The selected node should have a dark green background but does not"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameModifiedUser, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus3, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus3, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameLawTracking, "The selected node should have a dark green background but does not"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameLawTracking, "The selected node should have a dark green background but does not"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameLawTracking, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus4, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus4, "The selected node should not have a dark green background but does")
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
