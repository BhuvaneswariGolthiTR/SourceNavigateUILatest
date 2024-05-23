package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextnodesonly;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishApproveTextNodesOnlyStatusesAfterFindByTests extends TestService
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
        String liveContentUuid = "IC3341AA01B0111DAB311FB76B2E4F553";
        String repealContentUuid = "IC344BC701B0111DAB311FB76B2E4F553";
        String reserveContentUuid = "IC35425C01B0111DAB311FB76B2E4F553";
        String transferContentUuid = "IC3638F101B0111DAB311FB76B2E4F553";
        String sectionKeyword = "= ";

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

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(liveContentUuid);

        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        siblingMetadataPage().selectSelectedNodesNextNode();
        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        siblingMetadataPage().selectSelectedNodesNextNode();
        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        siblingMetadataPage().selectSelectedNodesNextNode();
        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        boolean toolboxWindowAppeared = publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text nodes only window appeared");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(codeName);

        //filter for each node with a given status
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(sectionKeyword + liveNodeValue, sectionKeyword + repealNodeValue, sectionKeyword + reserveNodeValue, sectionKeyword + transferNodeValue);

        //node by node, go to the node in hierarchy and verify what we see in the Status column of the publishing UI
        //is the status we see in the Status column in Hierarchy
        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + liveNodeValue);
        String liveNodeStatusInPublishingUI = gridPage().getSelectedNodesStatus();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextNodesOnlyWindow();

        gridPage().rightClickByNodeTargetValue(liveNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToCorrectLiveNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(liveNodeValue);
        Assertions.assertTrue(broughtToCorrectLiveNodeAfterFindDocumentInHierarchy, "Brought to correct live node after Find Document in Hierarchy");

        String liveNodeStatusInHierarchyEdit = siblingMetadataPage().getSelectedNodeStatus();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextNodesOnlyWindow();

        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + repealNodeValue);
        String repealNodeStatusInPublishingUI = gridPage().getSelectedNodesStatus();

        gridPage().rightClickByNodeTargetValue(repealNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToCorrectRepealNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(repealNodeValue);
        Assertions.assertTrue(broughtToCorrectRepealNodeAfterFindDocumentInHierarchy, "Brought to correct repeal node after Find Document in Hierarchy");

        String repealNodeStatusInHierarchyEdit = siblingMetadataPage().getSelectedNodeStatus();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextNodesOnlyWindow();

        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + reserveNodeValue);
        String reserveNodeStatusInPublishingUI = gridPage().getSelectedNodesStatus();

        gridPage().rightClickByNodeTargetValue(reserveNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToCorrectReserveNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(reserveNodeValue);
        Assertions.assertTrue(broughtToCorrectReserveNodeAfterFindDocumentInHierarchy, "Brought to correct reserve node after Find Document in Hierarchy");

        String reserveNodeStatusInHierarchyEdit = siblingMetadataPage().getSelectedNodeStatus();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextNodesOnlyWindow();

        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + transferNodeValue);
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

