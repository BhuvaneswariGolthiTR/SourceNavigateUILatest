package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class DeleteUndeleteFunctionsTests extends TestService
{
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    Connection connection;
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-12811 <br>
     * SUMMARY - This test verifies that the user can successfully delete and undelete a node through the navigation tree pane.
     * Also verifies that after Delete Functions > Delete, the deleted node gets a publishing status of Not Published <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteAndUndeleteTreeNodeTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //delete the node
        hierarchySearchPage().searchContentUuid(contentUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        hierarchyTreePage().uncheckHideDeletedForNavigationTree();
        hierarchyTreePage().rightClickSelectedNavigationTreeNode();
        treeContextMenu().deleteFunctionsDelete();
        deletePage().clickQuickLoad();
        deletePage().clickSubmit();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that node was deleted
        hierarchySearchPage().searchNodeUuid(datapodObject.getSections().get(0).getNodeUUID());
        boolean nodeExists = hierarchyTreePage().isTreeNodeWithGivenValueDeleted(nodeValue);
        Assertions.assertTrue(nodeExists, "The node has not been deleted when it should have been");
        boolean nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInSiblingMetadataGrid = siblingMetadataPage().isSelectedNodeNotPublished();
        boolean nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInDispoDerivPane = dispositionDerivationPage().isSelectedNodeStatusNotPublished();

        //Undelete the node
        hierarchyTreePage().rightClickSelectedNavigationTreeNode();
        treeContextMenu().deleteFunctionsUndelete();
        undeletePage().pressQuickLoad();
        undeletePage().pressSubmit();

        //Check that node was undeleted
        hierarchySearchPage().searchNodeUuid(datapodObject.getSections().get(0).getNodeUUID());
        boolean nodeExists2 = hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(nodeValue);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeExists2, "The node has not been undeleted when it should have been"),
            () -> Assertions.assertTrue(nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInSiblingMetadataGrid, "Node updated to not published after Delete Functions > Delete call in the sibling metadata grid"),
            () -> Assertions.assertTrue(nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInDispoDerivPane, "Node updated to not published after Delete Functions > Delete call in the disposition derivation pane")
        );
    }

    /**
     * STORY/BUG - HALCYONST-12811 <br>
     * SUMMARY - This test verifies that the user can successfully delete and undelete a node through the sibling metadata pane.
     * Also verifies that after Delete Functions > Delete, the deleted node gets a publishing status of Not Published <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteUndeleteSiblingMetadataTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //delete the node
        hierarchySearchPage().searchContentUuid(contentUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        hierarchyTreePage().uncheckHideDeletedForNavigationTree();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();
        deletePage().clickQuickLoad();
        deletePage().clickSubmit();

        //Check that node was deleted
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean nodeExists = siblingMetadataPage().isNodeDisplayedWithValue(nodeValue);
        Assertions.assertFalse(nodeExists, "The node has not been deleted when it should have been");
        boolean nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInSiblingMetadataGrid = siblingMetadataPage().isSelectedNodeNotPublished();
        boolean nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInDispoDerivPane = dispositionDerivationPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInSiblingMetadataGrid, "Node updated to not published after Delete Functions > Delete call in the sibling metadata grid"),
            () -> Assertions.assertTrue(nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInDispoDerivPane, "Node updated to not published after Delete Functions > Delete call in the disposition derivation pane")
        );
    }

    /**
     * STORY/BUG - HALCYONST-12811 <br>
     * SUMMARY - This test verifies that the user can successfully delete and undelete a node through the disposition/derivation pane.
     * Also verifies that after Delete Functions > Delete, the deleted node gets a publishing status of Not Published <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteUndeleteDispDerTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //delete the node
        hierarchySearchPage().searchContentUuid(contentUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        hierarchyTreePage().uncheckHideDeletedForNavigationTree();
        dispositionDerivationPage().rightClickNodeByValue(nodeValue);
        dispositionDerivationContextMenu().deleteFunctionsDelete();
        deletePage().clickQuickLoad();
        deletePage().clickSubmit();

        //Check that node was deleted
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean nodeExists = dispositionDerivationPage().isNodeValueDisplayed(nodeValue);
        Assertions.assertFalse(nodeExists, "The node has not been deleted when it should have been");
        boolean nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInSiblingMetadataGrid = siblingMetadataPage().isSelectedNodeNotPublished();
        boolean nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInDispoDerivPane = dispositionDerivationPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInSiblingMetadataGrid, "Node updated to not published after Delete Functions > Delete call in the sibling metadata grid"),
            () -> Assertions.assertTrue(nodeUpdatedToNotPublishedAfterDeleteFunctionsDeleteInDispoDerivPane, "Node updated to not published after Delete Functions > Delete call in the disposition derivation pane")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if (connection != null)
        {
            disconnect(connection);
        }
    }
}