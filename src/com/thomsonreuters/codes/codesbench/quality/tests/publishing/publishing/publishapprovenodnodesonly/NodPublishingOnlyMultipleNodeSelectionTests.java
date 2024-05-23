package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovenodnodesonly;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
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

public class NodPublishingOnlyMultipleNodeSelectionTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetTexas = ContentSets.TEXAS_DEVELOPMENT.getCode();
    String contentSetTexasText = ContentSets.TEXAS_DEVELOPMENT.getName();

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
        String nodChildNode1OfParentNodeUuid = "I870549701B7011DC9D3CF03F0F398677";
        String nodChildNode1OfParentContentUuid = "I870E71301B7011DC9D3CF03F0F398677";

        String nodChildNode2OfParentNodeUuid = "I7F4641301B7011DCB9F3C3D5E8630D0B";
        String nodChildNode2OfParentContentUuid = "I7F50EF901B7011DCB9F3C3D5E8630D0B";

        String code = "CONSTITUTION OF THE STATE OF";
        String nodKeyword = "BL ";
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        boolean toolboxWindowAppeared;

        // NOD node values and mock up - we are setting the nodes to ready status so that we are always able to update them to not published through codesbench
        // in doing this, we are able to use the modified date in our filter
        PublishingDatabaseUtils.setPublishingNodeToReady(nodChildNode1OfParentContentUuid, contentSetIowa, connection);
        String nodChildNodeOfParent1Value = HierarchyDatabaseUtils.getNodeValue(nodChildNode1OfParentContentUuid, contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(nodChildNode2OfParentContentUuid, contentSetIowa, connection);
        String nodChildNodeOfParent2Value = HierarchyDatabaseUtils.getNodeValue(nodChildNode2OfParentContentUuid, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        // nod node type mock up - using codesbench so that we can use the modified date as a filtering mechanism
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodChildNode1OfParentNodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().setNotPublishedStatusForSelectedNode();

        hierarchySearchPage().searchNodeUuid(nodChildNode2OfParentNodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().setNotPublishedStatusForSelectedNode();

        // NOD Publishing Only UI Verification
        toolboxWindowAppeared = publishingMenu().goToPublishApproveNodNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "NOD Publishing Only window did not appear and should have");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(code);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(nodKeyword + nodChildNodeOfParent1Value, nodKeyword + nodChildNodeOfParent2Value);

        gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(nodChildNodeOfParent1Value, nodChildNodeOfParent2Value);
        gridPage().rightClickMultipleSelectedNodes();

        boolean approvedStatusContextMenuOptionIsEnabledOnNod = gridContextMenu().isElementEnabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsEnabledOnNod = gridContextMenu().isElementEnabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsEnabledOnNod, "On the NOD Publishing Only UI, the Approved Status context menu option is disabled and should be enabled"),
            () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsEnabledOnNod, "On the NOD Publishing Only UI, the Remove Approved Status context menu option is disabled and should be enabled")
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
        String childNodeOfParent1ContentUuid = "I41356080EB2711DA9E2D8B86CC63AC44";
        String nodChildNode1OfParentNodeUuid = "I5AA732701B7611DCB9F3C3D5E8630D0B";
        String nodChildNode1OfParentContentUuid = "I5AB2A4201B7611DCB9F3C3D5E8630D0B";

        String nodChildNode2OfParentNodeUuid = "I882180201B7611DC98A28542D1D6139C";
        String nodChildNode2OfParentContentUuid = "I882DB5201B7611DC98A28542D1D6139C";
        String nodKeyword = "BL ";
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        boolean toolboxWindowAppeared;

        String codeName = HierarchyDatabaseUtils.getNodeCodeName(childNodeOfParent1ContentUuid, connection);

        // NOD node values and mock up - we are setting the nodes to ready status so that we are always able to update them to not published through codesbench
        // in doing this, we are able to use the modified date in our filter
        PublishingDatabaseUtils.setPublishingNodeToReady(nodChildNode1OfParentContentUuid, contentSetIowa, connection);
        String nodChildNodeOfParent1Value = HierarchyDatabaseUtils.getNodeValue(nodChildNode1OfParentContentUuid, contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(nodChildNode2OfParentContentUuid, contentSetIowa, connection);
        String nodChildNodeOfParent2Value = HierarchyDatabaseUtils.getNodeValue(nodChildNode2OfParentContentUuid, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        // nod node type mock up - using codesbench so that we can use the modified date as a filtering mechanism
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodChildNode1OfParentNodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().setNotPublishedStatusForSelectedNode();

        hierarchySearchPage().searchNodeUuid(nodChildNode2OfParentNodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().setNotPublishedStatusForSelectedNode();

        HierarchyDatabaseUtils.setNodeToErrorValidationFlag(nodChildNode1OfParentContentUuid, contentSetIowa, connection);

        // NOD Publishing Only UI Verification
        toolboxWindowAppeared = publishingMenu().goToPublishApproveNodNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "NOD Publishing Only window did not appear and should have");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(codeName);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(nodKeyword + nodChildNodeOfParent1Value, nodKeyword + nodChildNodeOfParent2Value);

        gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(nodChildNodeOfParent1Value, nodChildNodeOfParent2Value);
        gridPage().rightClickMultipleSelectedNodes();

        boolean approvedStatusContextMenuOptionIsDisabledOnNod = gridContextMenu().isElementDisabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsDisabledOnNod = gridContextMenu().isElementDisabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(nodChildNode1OfParentContentUuid, contentSetIowa, connection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsDisabledOnNod, "On the NOD Publishing Only UI, the Approved Status context menu option is enabled and should be disabled"),
            () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsDisabledOnNod, "On the NOD Publishing Only UI, the Remove Approved Status context menu option is enabled and should be disabled")
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
        String nodKeyword = "BL ";
        String blNode1Value = "val1";
        String blNode2Value = "val2";

        //Mock up 2 bl nodes in different chapters
        HierarchyDatapodConfiguration.getConfig().setChapterCount(2);
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        //BL node 1 variables
        String node1ContentUuid = datapodObject.getSections().get(0).getContentUUID();
        String blNode1ContentUuid = datapodObject.getBluelines().get(0).getContentUUID();
        String blNode1NodeUuid = datapodObject.getBluelines().get(0).getNodeUUID();
        String blNode1Volume = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(blNode1ContentUuid, contentSetIowa, connection);
        HierarchyDatabaseUtils.setNodeValue(connection, blNode1NodeUuid, blNode1Value);

        //Bl node 2 variables and change value to be different than bl node 1
        String node2ContentUuid = datapodObject.getSections().get(1).getContentUUID();
        String blNode2NodeUuid = datapodObject.getBluelines().get(1).getNodeUUID();
        String blNode2ContentUuid = datapodObject.getBluelines().get(1).getContentUUID();
        HierarchyDatabaseUtils.setNodeValue(connection, blNode2NodeUuid, blNode2Value);

        //Set nodes to 'not published' so they appear in nod publish approve ui
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(blNode1ContentUuid, contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(blNode2ContentUuid, contentSetIowa, connection);

        // Set a lock on bl node 1
        HierarchyDatabaseUtils.insertNodeIntoTocNodeLockTable(lockUuid, blNode1Value, 1, legalUser, todaysDate, 2, blNode1Volume, blNode1NodeUuid, connection);
        HierarchyDatabaseUtils.insertNodeIntoLockedTocNodeTable(blNode1NodeUuid, lockUuid, connection);

        // Go to NOD Publishing Only and filter for the mocked nodes
        homePage().goToHomePage();
        loginPage().logIn();
        boolean toolboxWindowAppeared = publishingMenu().goToPublishApproveNodNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "NOD Publishing Only window did not appear and should have");
        gridPage().waitForGridLoaded();
        gridHeaderPage().openMenuForVolColumn();
        gridHeaderFiltersPage().setFilterValue(volume);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(nodKeyword + blNode1Value, nodKeyword + blNode2Value);
        gridPage().waitForGridLoaded();

        //UI selection across parents test
        gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(blNode1Value, blNode2Value);
        boolean areNodesSelected = gridPage().isMultipleNodesSelected(blNode1Value, blNode2Value);
        Assertions.assertTrue(areNodesSelected, "The nodes are not selected when they should be");
        gridPage().rightClickMultipleSelectedNodes();
        boolean approvedStatusContextMenuOptionIsDisabledOnNod = gridContextMenu().isElementDisabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsDisabledOnNod = gridContextMenu().isElementDisabled(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(approvedStatusContextMenuOptionIsDisabledOnNod, "On the NOD Publishing Only UI, the Approved Status context menu option is enabled and should be disabled"),
            () -> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsDisabledOnNod, "On the NOD Publishing Only UI, the Remove Approved Status context menu option is enabled and should be disabled")
        );
    }

    //Note: keep this in texas due to Iowa not haveing an ARL node in iowa
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
//        String nodContentUuid = "IA9C6E830F77911EAB3EDE34C7B64E511";
//        String nodContentUuid2 = "IA9CEFE80F77911EAB3EDE34C7B64E511";
        String nodContentUuid = "IB77B813061B911EA9FA5D6CD05DC5E6C";
        String nodContentUuid2 = "IB77C1D7061B911EA9FA5D6CD05DC5E6C";
        //node from different volume and code
        String nodContentUuid3 = "I4ED13620A5FF11ECB365B9DD8DE05961";

        String originalStartDate = "31-DEC-05";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        connection = BaseDatabaseUtils.connectToDatabaseUAT();

        String nodValue = HierarchyDatabaseUtils.getNodeValue(nodContentUuid, contentSetTexas, connection);
        String nodValue2 = HierarchyDatabaseUtils.getNodeValue(nodContentUuid2, contentSetTexas, connection);
        String nodValue3 = HierarchyDatabaseUtils.getNodeValue(nodContentUuid3, contentSetTexas, connection);
        String[] nodeValues = {nodValue, nodValue2, nodValue3};

        String citelineUser = user().getPublishingToolboxUsername();
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(nodContentUuid3, contentSetTexas, connection);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();

            homePage().setMyContentSet(contentSetTexasText);

            hierarchyMenu().goToNavigate();
            hierarchySearchPage().searchContentUuid(nodContentUuid);
            siblingMetadataPage().updateSelectedSiblingMetadata();
            updateMetadataPage().clearAndEnterStartDate(currentDate);
            updateMetadataPage().clickQuickLoadOk();

            hierarchyNavigatePage().switchToHierarchyEditPage();
            boolean selectedNodeIsSetToNotPublished3 = siblingMetadataPage().isSelectedNodeStatusNotPublished();
            Assertions.assertTrue(selectedNodeIsSetToNotPublished3, "The selected node should have a publishing status of 'Not Published' but does not");

            hierarchySearchPage().searchContentUuid(nodContentUuid2);
            siblingMetadataPage().updateSelectedSiblingMetadata();
            updateMetadataPage().clearAndEnterStartDate(currentDate);
            updateMetadataPage().clickQuickLoadOk();

            hierarchyNavigatePage().switchToHierarchyEditPage();
            boolean selectedNodeIsSetToNotPublished4 = siblingMetadataPage().isSelectedNodeStatusNotPublished();
            Assertions.assertTrue(selectedNodeIsSetToNotPublished4, "The selected node should have a publishing status of 'Not Published' but does not");

            //Filter for the NOD nodes we are checking
            publishingMenu().goToPublishApproveNodNodesOnly();
            gridPage().waitForGridLoaded();
            gridHeaderPage().openMenuForValueColumn();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValues);

            //Check the data is correct to successfully be able to carry out the rest of the test in this UI
            gridPage().selectByNodeTargetValue(nodValue3);
            String nod3ModifiedUser = gridPage().getSelectedNodesModifiedByUsername();
            String nod3LawTracking = gridPage().getSelectedNodesLawTrackingStatus();
            boolean modifiedUsersAreDifferent2 = !nod3ModifiedUser.equals(citelineUser);
            boolean lawTrackingIsCorrect2 = !nod3LawTracking.equals("Quick Load");
            Assertions.assertTrue(modifiedUsersAreDifferent2, "The third node should have a different Modified By user than the other two nodes but does not. You will have to change this for the third node manually");
            Assertions.assertTrue(lawTrackingIsCorrect2, "The third node should have a different law tracking status than the other two nodes but does not. You will have to change the third node's law tracking manually");

            gridPage().rightClickByNodeTargetValue(nodValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsInSameVolume();
            boolean node1IsHighlightedGreenForSameVolume = gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue2);
            gridPage().selectByNodeTargetValue(nodValue2);
            boolean node2IsHighlightedGreenForSameVolume = gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue3);
            gridPage().selectByNodeTargetValue(nodValue3);
            boolean node3IsNotHighlightedGreenForSameVolume = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsInSameVolume();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus = !gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue2);
            gridPage().selectByNodeTargetValue(nodValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsWithSameCode();
            boolean node1IsHighlightedGreenForSameCode = gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue2);
            gridPage().selectByNodeTargetValue(nodValue2);
            boolean node2IsHighlightedGreenForSameCode = gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue3);
            gridPage().selectByNodeTargetValue(nodValue3);
            boolean node3IsNotHighlightedGreenForSameCode = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsWithSameCode();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus2 = !gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue2);
            gridPage().selectByNodeTargetValue(nodValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus2 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsModifiedBySameUser();
            boolean node1IsHighlightedGreenForSameModifiedByUser = gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue2);
            gridPage().selectByNodeTargetValue(nodValue2);
            boolean node2IsHighlightedGreenForSameModifiedByUser = gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue3);
            gridPage().selectByNodeTargetValue(nodValue3);
            boolean node3IsNotHighlightedGreenForSameModifiedUser = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsModifiedBySameUser();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus3 = !gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue2);
            gridPage().selectByNodeTargetValue(nodValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus3 = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodValue);
            gridContextMenu().massSelectionApprovedStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad();
            boolean node1IsHighlightedGreenForSameLawTracking = gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue2);
            gridPage().selectByNodeTargetValue(nodValue2);
            boolean node2IsHighlightedGreenForSameLawTracking = gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue3);
            gridPage().selectByNodeTargetValue(nodValue3);
            boolean node3IsNotHighlightedGreenForSameLawTracking = !gridPage().isBackgroundGreenForSelectedRow();

            gridPage().rightClickByNodeTargetValue(nodValue);
            gridContextMenu().massSelectionRemoveApprovedStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad();
            boolean node1IsNotHighlightedGreenAfterRemovingApprovedStatus4 = !gridPage().isBackgroundGreenForSelectedRow();

//            gridPage().selectByNodeTargetValueOnlyDiv(nodValue2);
            gridPage().selectByNodeTargetValue(nodValue2);
            boolean node2IsNotHighlightedGreenAfterRemovingApprovedStatus4 = !gridPage().isBackgroundGreenForSelectedRow();

            Assertions.assertAll
            (
                //NOD Publishing Only
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameVolume, "node1 should have a dark green background after for same volume but does not"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameVolume, "node2 should have a dark green background after for same volume but does not"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameVolume, "node3 should not have a dark green background after for same volume but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus, "node1 should not have a dark green background after removing approved status but does"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus, "node2 should not have a dark green background after removing approved status but does"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameCode, "node1  should have a dark green background after same for code but does not"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameCode, "node2 should have a dark green background  after same for code but does not"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameCode, "node3 should not have a dark green background  after for same code but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus2, "node1 should not have a dark green background after removing approved status but does"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus2, "node2 should not have a dark green background after removing approved status but does"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameModifiedByUser, "node1 should have a dark green background after for same modified user but does not"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameModifiedByUser, "node2 should have a dark green background after for same modified user but does not"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameModifiedUser, "node3 not have a dark green background after for same modified user but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus3, "node1 should not have a dark green background after removing approved status but does"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus3, "node2 should not have a dark green background after removing approved status but does"),
                () -> Assertions.assertTrue(node1IsHighlightedGreenForSameLawTracking, "node1 should have a dark green background after for same law tracking but does not"),
                () -> Assertions.assertTrue(node2IsHighlightedGreenForSameLawTracking, "node2 should have a dark green background after for same law tracking but does not"),
                () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameLawTracking, "node3 should not have a dark green background after for same law tracking but does"),
                () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingApprovedStatus4, "node1 should not have a dark green background after removing approved status  but does"),
                () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingApprovedStatus4, "node2 should not have a dark green background after removing approved status  but does")
            );
        }
        finally
        {
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(nodContentUuid, originalStartDate, connection);
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(nodContentUuid2, originalStartDate, connection);
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
