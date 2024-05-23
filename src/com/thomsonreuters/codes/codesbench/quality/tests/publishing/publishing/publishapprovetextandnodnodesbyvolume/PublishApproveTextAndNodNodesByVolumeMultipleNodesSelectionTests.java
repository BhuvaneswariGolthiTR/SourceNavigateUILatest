package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextandnodnodesbyvolume;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishApproveTextAndNodNodesByVolumeMultipleNodesSelectionTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY/BUG - HALCYONST-8264, HALCYONST-9543 <br>
     * SUMMARY - This test verifies that all sub context menu options under the 'Mass Selection' option are working correctly in the Publish Approve-Text and NOD nodes By Volume toolbox <br>
     * USER - CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void massSelectionFunctionalityTest()
    {
        //2 nodes with same code and volume
        String contentUuid = "I6FBE1BC01B0411DAB311FB76B2E4F553";
        String contentUuid2 = "I6FD6D3E01B0411DAB311FB76B2E4F553";
        //node from different volume and code
        String contentUuid3 = "I1AE340401B0811DAB311FB76B2E4F553";
        String originalStartDate = "31-DEC-05";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid3, contentSetIowa, uatConnection);
        String nodeVolume = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid, contentSetIowa, uatConnection);
        String nodeVolume2 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid3, contentSetIowa, uatConnection);

        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, uatConnection);
        String nodeValue2 = HierarchyDatabaseUtils.getNodeValue(contentUuid2, contentSetIowa, uatConnection);
        String nodeValue3 = HierarchyDatabaseUtils.getNodeValue(contentUuid3, contentSetIowa, uatConnection);
        String[] nodeValues = {nodeValue, nodeValue2, nodeValue3};

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid2, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid3, contentSetIowa, uatConnection);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();

            DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
            hierarchyMenu().goToNavigate();
            hierarchySearchPage().searchContentUuid(contentUuid);
            siblingMetadataPage().updateSelectedSiblingMetadata();
            updateMetadataPage().clearAndEnterStartDate(currentDate);
            updateMetadataPage().clickQuickLoadOk();

            hierarchySearchPage().searchContentUuid(contentUuid2);
            siblingMetadataPage().updateSelectedSiblingMetadata();
            updateMetadataPage().clearAndEnterStartDate(currentDate);
            updateMetadataPage().clickQuickLoadOk();

            //Filter for nodes we are checking
            boolean publishingToolboxAppeared = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
            Assertions.assertTrue(publishingToolboxAppeared, "The Publish Approve-Text and NOD nodes by volume Page loaded");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
            toolbarPage().clickVolumeSelection();
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume);
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume2);
            volumeSelectionPage().clickAdd();
            volumeSelectionPage().clickConfirm();

            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValues);
            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsInSameVolume();
            boolean node1IsHighlightedGreenForSameVolume = gridPage().isBackgroundGreenForSelectedRow();
            boolean node2IsHighlightedGreenForSameVolume = gridPage().isBackgroundGreenForRow(nodeValue2);
            boolean node3IsNotHighlightedGreenForSameVolume = !gridPage().isBackgroundGreenForRow(nodeValue3);

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsInSameVolume();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus = !gridPage().isBackgroundGreenForSelectedRow();
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus = !gridPage().isBackgroundGreenForRow(nodeValue2);

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsWithSameCode();
            boolean node1IsHighlightedGreenForSameCode = gridPage().isBackgroundGreenForSelectedRow();
            boolean node2IsHighlightedGreenForSameCode = gridPage().isBackgroundGreenForRow(nodeValue2);
            boolean node3IsNotHighlightedGreenForSameCode = !gridPage().isBackgroundGreenForRow(nodeValue3);

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsWithSameCode();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus2 = !gridPage().isBackgroundGreenForSelectedRow();
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus2 = !gridPage().isBackgroundGreenForRow(nodeValue2);

            gridPage().rightClickByNodeTargetValue(nodeValue);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsModifiedBySameUser();
            boolean node1IsHighlightedGreenForSameModifiedByUser = gridPage().isBackgroundGreenForSelectedRow();
            boolean node2IsHighlightedGreenForSameModifiedByUser = gridPage().isBackgroundGreenForRow(nodeValue2);
            boolean node3IsNotHighlightedGreenForSameModifiedUser = !gridPage().isBackgroundGreenForRow(nodeValue3);

            gridPage().rightClickByNodeTargetValue(nodeValue);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsModifiedBySameUser();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus3 = !gridPage().isBackgroundGreenForSelectedRow();
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus3 = !gridPage().isBackgroundGreenForRow(nodeValue2);

            gridPage().rightClickByNodeTargetValue(nodeValue);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad();
            boolean node1IsHighlightedGreenForSameLawTracking = gridPage().isBackgroundGreenForSelectedRow();
            boolean node2IsHighlightedGreenForSameLawTracking = gridPage().isBackgroundGreenForRow(nodeValue2);
            boolean node3IsNotHighlightedGreenForSameLawTracking = !gridPage().isBackgroundGreenForRow(nodeValue3);

            gridPage().rightClickByNodeTargetValue(nodeValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus4 = !gridPage().isBackgroundGreenForSelectedRow();
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus4 = !gridPage().isBackgroundGreenForRow(nodeValue2);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameVolume, "The selected node should have a dark green background but does not1"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameVolume, "The selected node should have a dark green background but does not2"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameVolume, "The selected node should not have a dark green background but does3"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus, "The selected node should not have a dark green background but does4"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus, "The selected node should not have a dark green background but does5"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameCode, "The selected node should have a dark green background but does not6"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameCode, "The selected node should have a dark green background but does not7"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameCode, "The selected node should not have a dark green background but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus2, "The selected node should not have a dark green background but does8"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus2, "The selected node should not have a dark green background but does9"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameModifiedByUser, "The selected node should have a dark green background but does not10"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameModifiedByUser, "The selected node should have a dark green background but does not11"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameModifiedUser, "The selected node should not have a dark green background but does12"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus3, "The selected node should not have a dark green background but does13"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus3, "The selected node should not have a dark green background but does14"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameLawTracking, "The selected node should have a dark green background but does not15"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameLawTracking, "The selected node should have a dark green background but does not16"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameLawTracking, "The selected node should not have a dark green background but does17"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus4, "The selected node should not have a dark green background but does18"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus4, "The selected node should not have a dark green background but does19")
            );
        }
        finally
        {
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid, originalStartDate, uatConnection);
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid2, originalStartDate, uatConnection);
        }
    }

    /**
     * STORY: HALCYONST-10494 <br>
     * SUMMARY: This test verifies that when multi selecting nodes that span across different parents, if the nodes within the selection are
     * not locked and do not have error flags, the context menu options 'Approved Status' and 'Remove Approved Status' on the publishing UIs should be enabled <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleNodeSelectionAcrossParentsPositiveTest()
    {
        //Two nodes across different parents
        String childNodeOfParent1ContentUuid = "I117BF9C1157711DA8AC5CD53670E6B4E";
        String childNodeOfParent2ContentUuid = "I4950FF80EB5A11DAA49F8300AC8BB1CF";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        //set nodes to not deleted
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        String childNode1OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);
        String childNode2OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);
        String nodeVolume1 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        String nodeVolume2 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean toolboxWindowAppeared = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text and NOD nodes by volume window did not appear and should have");

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume(nodeVolume1);
        volumeSelectionPage().clickCheckBoxForVolume(nodeVolume2);
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickConfirm();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(childNode1OfParentValue, childNode2OfParentValue);

        gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(childNode1OfParentValue, childNode2OfParentValue);
        gridPage().rightClickMultipleSelectedNodes();

        boolean approvedStatusContextMenuOptionIsEnabled = gridContextMenu().isElementEnabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsEnabled = gridContextMenu().isElementEnabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsEnabled, "The Approved Status context menu option is disabled and should be enabled"),
            () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsEnabled, "The Remove Approved Status context menu option is disabled and should be enabled")
        );
    }

    /**
     * STORY: HALCYONST-10494 <br>
     * SUMMARY: This test verifies that when multi selecting nodes that span across different parents, if any of the nodes within the selection have an error flag,
     * the context menu options 'Approved Status' and 'Remove Approved Status' on the publishing UIs should be disabled <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleNodeSelectionAcrossParentsErrorFlagTest()
    {
        //Two nodes across different parents
        String childNodeOfParent1ContentUuid = "I117BF9C1157711DA8AC5CD53670E6B4E";
        String childNodeOfParent2ContentUuid = "I4950FF80EB5A11DAA49F8300AC8BB1CF";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        //set one node to error status
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToErrorValidationFlag(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        String childNode1OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);
        String childNode2OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);
        String nodeVolume1 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        String nodeVolume2 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();

            boolean toolboxWindowAppeared = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
            Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text and NOD nodes by volume window did not appear and should have");

            toolbarPage().clickVolumeSelection();
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume1);
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume2);
            volumeSelectionPage().clickAdd();
            volumeSelectionPage().clickConfirm();

            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(childNode1OfParentValue, childNode2OfParentValue);

            gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(childNode1OfParentValue, childNode2OfParentValue);
            gridPage().rightClickMultipleSelectedNodes();

            boolean approvedStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
            boolean removeApprovedStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsDisabled, "The Approved Status context menu option is enabled and should be disabled"),
                () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsDisabled, "The Remove Approved Status context menu option is enabled and should be disabled")
            );
        }
        finally
        {
            HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        }
    }

    /**
     * STORY: HALCYONST-10494 <br>
     * SUMMARY: This test verifies that when multi selecting nodes that span across different parents, if any of the nodes within the selection are locked,
     * the context menu options 'Approved Status' and 'Remove Approved Status' on the publishing UIs should be disabled  <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleNodeSelectionAcrossParentsLockedNodeTest()
    {
        //Two nodes across different parents
        String childNodeOfParent1ContentUuid = "I117BF9C1157711DA8AC5CD53670E6B4E";
        String childNodeOfParent2ContentUuid = "I4950FF80EB5A11DAA49F8300AC8BB1CF";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String lockUUID = CommonDataMocking.generateUUID();
        String todaysDate = DateAndTimeUtils.getCurentDateDDDashMMMDashYY();
        String childOfParent1NodeUuid = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(uatConnection, childNodeOfParent1ContentUuid);

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToErrorValidationFlag(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        String childNode1OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);
        String childNode2OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);
        String nodeVolume1 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        String nodeVolume2 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);

        //set a lock on one of the nodes
        HierarchyDatabaseUtils.insertNodeIntoTocNodeLockTable(lockUUID, childNode1OfParentValue, 1, user().getUsername(), todaysDate, 2, nodeVolume1, childOfParent1NodeUuid, uatConnection);
        HierarchyDatabaseUtils.insertNodeIntoLockedTocNodeTable(childOfParent1NodeUuid, lockUUID, uatConnection);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();

            boolean toolboxWindowAppeared = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
            Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text and NOD nodes by volume window did not appear and should have");

            toolbarPage().clickVolumeSelection();
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume1);
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume2);
            volumeSelectionPage().clickAdd();
            volumeSelectionPage().clickConfirm();

            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(childNode1OfParentValue, childNode2OfParentValue);

            gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(childNode1OfParentValue, childNode2OfParentValue);
            gridPage().rightClickMultipleSelectedNodes();

            boolean approvedStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
            boolean removeApprovedStatusContextMenuOptionIsDisabled = gridContextMenu().isElementDisabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsDisabled, "The Approved Status context menu option is enabled and should be disabled"),
                () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsDisabled, "The Remove Approved Status context menu option is enabled and should be disabled")
            );
        }
        finally
        {
            HierarchyDatabaseUtils.deleteNodeFromTocNodeLockTable(childOfParent1NodeUuid, uatConnection);
        }
    }
}
