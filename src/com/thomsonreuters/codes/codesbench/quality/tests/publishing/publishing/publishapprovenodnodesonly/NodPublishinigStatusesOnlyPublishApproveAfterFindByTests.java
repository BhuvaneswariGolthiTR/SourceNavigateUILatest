package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovenodnodesonly;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import java.sql.Connection;

public class NodPublishinigStatusesOnlyPublishApproveAfterFindByTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY: HALCYONST-6132, HALCYONST-6372, HALCYONST-7168  <br>
     * SUMMARY: This test verifies that when in a publishing UI, after filtering for a node with Live status, if we find the node
     * in Hierarchy Edit, the node status in the sibling metadata pane should have Live status as well. The test does this for 3 other
     * statuses as well.  <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void statusAfterFindDocumentInHierarchyTest()
    {
        String liveContentUuid = "IBEC58B001BB511DC868395DC84A0FC63";
        String repealContentUuid = "IBF0E7AE01BB511DC868395DC84A0FC63";
        String reserveContentUuid = "IBF4BF9101BB511DC868395DC84A0FC63";
        String transferContentUuid = "IBF87A2801BB511DC868395DC84A0FC63";
        String bluelineKeyword = "BL ";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, uatConnection);

        String codeName = HierarchyDatabaseUtils.getNodeCodeName(liveContentUuid, uatConnection);

        //live node
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(liveContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToLiveStatus(liveContentUuid, contentSetIowa, uatConnection);
        String liveNodeValue = HierarchyDatabaseUtils.getNodeValue(liveContentUuid, contentSetIowa, uatConnection);

        //repeal node
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(repealContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToRepealStatus(repealContentUuid, contentSetIowa, uatConnection);
        String repealNodeValue = HierarchyDatabaseUtils.getNodeValue(repealContentUuid, contentSetIowa, uatConnection);

        //reserve node
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(reserveContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToReserveStatus(reserveContentUuid, contentSetIowa, uatConnection);
        String reserveNodeValue = HierarchyDatabaseUtils.getNodeValue(reserveContentUuid, contentSetIowa, uatConnection);

        //transfer node
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(transferContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToTransferStatus(transferContentUuid, contentSetIowa, uatConnection);
        String transferNodeValue = HierarchyDatabaseUtils.getNodeValue(transferContentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(liveContentUuid);

        siblingMetadataPage().selectNodesByValue(liveNodeValue);
        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        siblingMetadataPage().selectNodesByValue(repealNodeValue);
        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        siblingMetadataPage().selectNodesByValue(reserveNodeValue);
        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        siblingMetadataPage().selectNodesByValue(transferNodeValue);
        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        boolean toolboxWindowAppeared = publishingMenu().goToPublishApproveNodNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "NOD Publishing Only window appeared");
        gridPage().waitForGridLoaded();

        //filter for each node with a given status
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(bluelineKeyword + liveNodeValue, bluelineKeyword + repealNodeValue, bluelineKeyword + reserveNodeValue, bluelineKeyword + transferNodeValue);

        //node by node, go to the node in hierarchy and verify what we see in the Status column of the publishing UI
        //is the status we see in the Status column in Hierarchy
//        gridPage().selectNodebyHierarchyColumnValue(bluelineKeyword + liveNodeValue);
        gridPage().selectByNodeTargetValue(liveNodeValue);
        String liveNodeStatusInPublishingUI = gridPage().getSelectedNodesStatus();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveNodNodesOnlyWindow();

        gridPage().rightClickByNodeTargetValue(liveNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToCorrectLiveNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(liveNodeValue);
        Assertions.assertTrue(broughtToCorrectLiveNodeAfterFindDocumentInHierarchy, "Brought to correct live node after Find Document in Hierarchy");

        String liveNodeStatusInHierarchyEdit = siblingMetadataPage().getSelectedNodeStatus();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveNodNodesOnlyWindow();

//        gridPage().selectNodebyHierarchyColumnValue(bluelineKeyword + repealNodeValue);
        gridPage().selectByNodeTargetValue(repealNodeValue);
        String repealNodeStatusInPublishingUI = gridPage().getSelectedNodesStatus();

        gridPage().rightClickByNodeTargetValue(repealNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToCorrectRepealNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(repealNodeValue);
        Assertions.assertTrue(broughtToCorrectRepealNodeAfterFindDocumentInHierarchy, "Brought to correct repeal node after Find Document in Hierarchy");

        String repealNodeStatusInHierarchyEdit = siblingMetadataPage().getSelectedNodeStatus();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveNodNodesOnlyWindow();

//        gridPage().selectNodebyHierarchyColumnValue(bluelineKeyword + reserveNodeValue);
//        gridPage().selectNodebyHierarchyColumnValue(reserveNodeValue);
        gridPage().selectByNodeTargetValue(reserveNodeValue);
        String reserveNodeStatusInPublishingUI = gridPage().getSelectedNodesStatus();

        gridPage().rightClickByNodeTargetValue(reserveNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToCorrectReserveNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(reserveNodeValue);
        Assertions.assertTrue(broughtToCorrectReserveNodeAfterFindDocumentInHierarchy, "Brought to correct reserve node after Find Document in Hierarchy");

        String reserveNodeStatusInHierarchyEdit = siblingMetadataPage().getSelectedNodeStatus();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveNodNodesOnlyWindow();

//        gridPage().selectNodebyHierarchyColumnValue(bluelineKeyword + transferNodeValue);
        gridPage().selectByNodeTargetValue(transferNodeValue);
        String transferNodeStatusInPublishingUI = gridPage().getSelectedNodesStatus();

        gridPage().rightClickByNodeTargetValue(transferNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToCorrectTransferNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(transferNodeValue);
        Assertions.assertTrue(broughtToCorrectTransferNodeAfterFindDocumentInHierarchy, "Brought to correct transfer node after Find Document in Hierarchy");

        String transferNodeStatusInHierarchyEdit = siblingMetadataPage().getSelectedNodeStatus();

        //set nodes back to live status
        HierarchyDatabaseUtils.setNodeToLiveStatus(repealContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToLiveStatus(reserveContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToLiveStatus(transferContentUuid, contentSetIowa, uatConnection);

        Assertions.assertAll
        (
            () -> Assertions.assertEquals(liveNodeStatusInPublishingUI, liveNodeStatusInHierarchyEdit, "The live node's status in the publishing UI and hierarchy edit do not match and should"),
            () -> Assertions.assertEquals(repealNodeStatusInPublishingUI, repealNodeStatusInHierarchyEdit, "The repeal node's status in the publishing UI and hierarchy edit do not match and should"),
            () -> Assertions.assertEquals(reserveNodeStatusInPublishingUI, reserveNodeStatusInHierarchyEdit, "The reserve node's status in the publishing UI and hierarchy edit do not match and should"),
            () -> Assertions.assertEquals(transferNodeStatusInPublishingUI, transferNodeStatusInHierarchyEdit, "The transfer node's status in the publishing UI and hierarchy edit do not match and should")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
