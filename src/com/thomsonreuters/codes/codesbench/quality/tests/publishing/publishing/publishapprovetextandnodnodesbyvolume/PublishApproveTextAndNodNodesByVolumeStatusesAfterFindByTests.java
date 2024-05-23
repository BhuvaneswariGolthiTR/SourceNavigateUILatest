package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextandnodnodesbyvolume;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishApproveTextAndNodNodesByVolumeStatusesAfterFindByTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject;

    /**
     * STORY: HALCYONST-6132, HALCYONST-6372, HALCYONST-7168  <br>
     * SUMMARY: This test verifies the find document in hierarchy button opens the correct node and verifies the node status in hierarchy <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void statusAfterFindDocumentInHierarchyTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, uatConnection);

        HierarchyDatapodConfiguration.getConfig().setSectionCount(4);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String liveContentUuid = datapodObject.getSections().get(0).getContentUUID();
        String repealContentUuid = datapodObject.getSections().get(1).getContentUUID();
        String reserveContentUuid = datapodObject.getSections().get(2).getContentUUID();
        String transferContentUuid = datapodObject.getSections().get(3).getContentUUID();

        HierarchyDatabaseUtils.setNodeToLiveStatus(liveContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToRepealStatus(repealContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToReserveStatus(reserveContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToTransferStatus(transferContentUuid, contentSetIowa, uatConnection);

        String liveNodeValue = HierarchyDatabaseUtils.getNodeValue(liveContentUuid, contentSetIowa, uatConnection);
        String repealNodeValue = HierarchyDatabaseUtils.getNodeValue(repealContentUuid, contentSetIowa, uatConnection);
        String reserveNodeValue = HierarchyDatabaseUtils.getNodeValue(reserveContentUuid, contentSetIowa, uatConnection);
        String transferNodeValue = HierarchyDatabaseUtils.getNodeValue(transferContentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean toolboxWindowAppeared = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text and NOD nodes by volume window did not appear and should have");

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume("9999");
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickConfirm();

        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(liveNodeValue, repealNodeValue, reserveNodeValue, transferNodeValue);

        gridPage().rightClickByNodeTargetValue(liveNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean correctLiveNodeIsSelected = siblingMetadataPage().getSelectedNodeValue().equals(liveNodeValue);
        boolean nodeHasLiveStatusAfterFinInHierarchy = siblingMetadataPage().getSelectedNodeStatus().equals("Live");
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextAndNodNodesByVolumeWindow();

        gridPage().rightClickByNodeTargetValue(repealNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean correctRepealNodeIsSelected = siblingMetadataPage().getSelectedNodeValue().equals(repealNodeValue);
        boolean nodeHasRepealStatusAfterFinInHierarchy = siblingMetadataPage().getSelectedNodeStatus().equals("Repeal");
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextAndNodNodesByVolumeWindow();

        gridPage().rightClickByNodeTargetValue(reserveNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean correctReserveNodeIsSelected = siblingMetadataPage().getSelectedNodeValue().equals(reserveNodeValue);
        boolean nodeHasReserveStatusAfterFinInHierarchy = siblingMetadataPage().getSelectedNodeStatus().equals("Reserve");
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextAndNodNodesByVolumeWindow();

        gridPage().rightClickByNodeTargetValue(transferNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean correctTransferNodeIsSelected = siblingMetadataPage().getSelectedNodeValue().equals(transferNodeValue);
        boolean nodeHasTransferStatusAfterFinInHierarchy = siblingMetadataPage().getSelectedNodeStatus().equals("Transfer");
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextAndNodNodesByVolumeWindow();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(correctLiveNodeIsSelected,"The live node did not select the correct node after find in hierarchy"),
            () -> Assertions.assertTrue(nodeHasLiveStatusAfterFinInHierarchy,"The live node did not have live status after find in hierarchy"),
            () -> Assertions.assertTrue(correctRepealNodeIsSelected,"The repeal node did not select the correct node after find in hierarchy"),
            () -> Assertions.assertTrue(nodeHasRepealStatusAfterFinInHierarchy,"The repeal node did not have repeal status after find in hierarchy"),
            () -> Assertions.assertTrue(correctReserveNodeIsSelected,"The reserve node did not select the correct node after find in hierarchy"),
            () -> Assertions.assertTrue(nodeHasReserveStatusAfterFinInHierarchy,"The reserve node did not have reserve status after find in hierarchy"),
            () -> Assertions.assertTrue(correctTransferNodeIsSelected,"The transfer node did not select the correct node after find in hierarchy"),
            () -> Assertions.assertTrue(nodeHasTransferStatusAfterFinInHierarchy,"The transfer node did not have transfer status after find in hierarchy")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if(uatConnection != null)
        {
            BaseDatabaseUtils.disconnect(uatConnection);
        }
    }
}

