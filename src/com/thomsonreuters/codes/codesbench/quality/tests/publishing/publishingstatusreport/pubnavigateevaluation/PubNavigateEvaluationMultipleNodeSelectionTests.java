package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.pubnavigateevaluation;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PubNavigateEvaluationMultipleNodeSelectionTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY: HALCYONST-10494 <br>
     * SUMMARY: This test verifies that when multi selecting nodes that span across different parents, if the nodes within the selection are
     * not locked and do not have error flags, the first two context menu options on the publishing UIs should be enabled <br>
     * USER: Legal <br>
     * */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleNodeSelectionAcrossParentsPositiveTestTest()
    {
        String childNodeOfParent1ContentUuid = "I618310601AF511DAB310FB76B2E4F553";
        String childNodeOfParent2ContentUuid = "I7DF5C7601AF511DAB310FB76B2E4F553";
        String code = "TITLE III";
        String sectionKeyword = "= ";
         uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        // section node mock up
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        String childNode1OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent1ContentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);
        String childNode2OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent2ContentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean toolboxWindowAppeared = publishingMenu().goToPublishingStatusReportsPubNavigateEvaluation();
        Assertions.assertTrue(toolboxWindowAppeared, "Pub Navigate Evaluation window did not appear and should have");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(code);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(sectionKeyword + childNode1OfParentValue, sectionKeyword + childNode2OfParentValue);

        gridPage().selectAllNodesBetweenTwoNodeHierarchyColumnValues(sectionKeyword + childNode1OfParentValue, sectionKeyword + childNode2OfParentValue);
        gridPage().rightClickMultipleSelectedNodes();

        boolean selectForUpdatedStatusContextMenuOptionIsEnabled = gridContextMenu().isElementEnabled(GridContextMenuElements.SELECT_FOR_UPDATED_STATUS_XPATH);
        boolean removeSelectionContextMenuOptionIsEnabled = gridContextMenu().isElementEnabled(GridContextMenuElements.REMOVE_SELECTION_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(selectForUpdatedStatusContextMenuOptionIsEnabled, "On the Pub Navigate Evaluation UI, the Select For Updated Status context menu option is disabled and should be enabled"),
            () -> Assertions.assertTrue(removeSelectionContextMenuOptionIsEnabled, "On the Pub Navigate Evaluation UI, the Remove Selection context menu option is disabled and should be enabled")
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

        String[] nodeUuids = {"I0497A86014F211DA8AC5CD53670E6B4E","I049C635014F211DA8AC5CD53670E6B4E","I7B523CD014EE11DA8AC5CD53670E6B4E"};
        String[] contentUuids = {"I38E6AE901AF611DAB310FB76B2E4F553", "I38F4DF601AF611DAB310FB76B2E4F553", "I1B2055D01AF311DAB310FB76B2E4F553"};

        //Set nodes to a publishing status of 'Pub Complete' to view the nodes in this Publishing UI
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuids[0],contentSetIowa,uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuids[1],contentSetIowa,uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuids[2],contentSetIowa,uatConnection);

        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuids[0], contentSetIowa, uatConnection);
        String nodeValue2 = HierarchyDatabaseUtils.getNodeValue(contentUuids[1], contentSetIowa, uatConnection);
        String nodeValue3 = HierarchyDatabaseUtils.getNodeValue(contentUuids[2], contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(contentUuids[0],contentSetIowa,uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(contentUuids[1],contentSetIowa,uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(contentUuids[2],contentSetIowa,uatConnection);

        //Filter for the nodes we are checking
        publishingMenu().goToPublishingStatusReportsPubNavigateEvaluation();
        gridPage().waitForGridLoaded();
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(nodeUuids);

        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue);
        gridContextMenu().massSelectionSelectForUpdatedStatusAllDocumentsInSameVolume();
        boolean node1IsHighlightedGreenForSameVolume5 = gridPage().isBackgroundGreenForSelectedRow();
        boolean node2IsHighlightedGreenForSameVolume5 = gridPage().isBackgroundGreenForRow(nodeValue2);
        boolean node3IsNotHighlightedGreenForSameVolume5 = !gridPage().isBackgroundGreenForRow(nodeValue3);

        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue);
        gridContextMenu().massSelectionRemoveSelectionAllDocumentsInSameVolume();
        boolean node1IsNotHighlightedGreenAfterRemovingSelection = !gridPage().isBackgroundGreenForSelectedRow();
        boolean node2IsNotHighlightedGreenAfterRemovingSelection = !gridPage().isBackgroundGreenForRow(nodeValue2);

        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue);
        gridContextMenu().massSelectionSelectForUpdatedStatusAllDocumentsWithSameCode();
        boolean node1IsHighlightedGreenForSameCode5 = gridPage().isBackgroundGreenForSelectedRow();
        boolean node2IsHighlightedGreenForSameCode5 = gridPage().isBackgroundGreenForRow(nodeValue2);
        boolean node3IsNotHighlightedGreenForSameCode5 = !gridPage().isBackgroundGreenForRow(nodeValue3);

        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue);
        gridContextMenu().massSelectionRemoveSelectionAllDocumentsWithSameCode();
        boolean node1IsNotHighlightedGreenAfterRemovingSelection2 = !gridPage().isBackgroundGreenForSelectedRow();
        boolean node2IsNotHighlightedGreenAfterRemovingSelection2 = !gridPage().isBackgroundGreenForRow(nodeValue2);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(node1IsHighlightedGreenForSameVolume5,"node 1, with for same volumne, should have a dark green background but does not"),
            () -> Assertions.assertTrue(node2IsHighlightedGreenForSameVolume5,"node 2, with for same volumne, should have a dark green background but does not"),
            () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameVolume5,"node 3, with for same volumne, should not have a dark green background but does"),
            () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingSelection,"node 1, after remove, should not have a dark green background but does"),
            () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingSelection,"node 2, after remove, should not have a dark green background but does"),
            () -> Assertions.assertTrue(node1IsHighlightedGreenForSameCode5,"node 1, after for same code, should have a dark green background but does not"),
            () -> Assertions.assertTrue(node2IsHighlightedGreenForSameCode5,"node 2. after for same code, should have a dark green background but does not"),
            () -> Assertions.assertTrue(node3IsNotHighlightedGreenForSameCode5,"node 3, after for same code, should not have a dark green background but does"),
            () -> Assertions.assertTrue(node1IsNotHighlightedGreenAfterRemovingSelection2,"node 1, after remove selection. should not have a dark green background but does"),
            () -> Assertions.assertTrue(node2IsNotHighlightedGreenAfterRemovingSelection2,"node 2, after remove selection should not have a dark green background but does")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
