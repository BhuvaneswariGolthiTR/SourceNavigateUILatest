package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import java.sql.Connection;

public class ReorderChildrenTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the order of a section level node's child nodes can be successfully
     * changed in the 'Reorder Children' page, and correctly displayed in the sibling metadata pane <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reorderChildrenSectionLevelLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String chapterNodeUUID = datapodObject.getChapters().get(0).getNodeUUID();
        String section1NodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String section2NodeUUID = datapodObject.getSections().get(1).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
        String section1Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(section1NodeUUID, contentSetIowa, connection);
        String section2Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(section2NodeUUID, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Drag first child node down one spot
        hierarchySearchPage().searchNodeUuid(chapterNodeUUID);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(chapterNodeUUID, connection), contentSetIowa, connection);
        BaseDatabaseUtils.disconnect(connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        boolean chapterNodeIsLoadedToWestlawBeforeReorder = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
        Assertions.assertTrue(chapterNodeIsLoadedToWestlawBeforeReorder, "Chapter node is not Loaded to Westlaw status and should be after running the database method");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsReorderChildren();
        reorderChildrenPage().clickAndDragFirstChildDownOne();
        reorderChildrenPage().clickQuickLoadSave();

        boolean articleNodeIsLoadedToWestlawAfterReorder = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();

        //Check that the node order was successfully changed
        hierarchySearchPage().searchNodeUuid(section2NodeUUID);
        String firstChildNode = siblingMetadataPage().getSelectedNodeValue();
        String secondChildNode = siblingMetadataPage().getNodeValueBelowSelectedNode();
        boolean firstChildNodeIsCorrect = firstChildNode.equals(section2Value);
        boolean secondChildNodeIsCorrect = secondChildNode.equals(section1Value);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(articleNodeIsLoadedToWestlawAfterReorder, "Chapter node is not Loaded to Westlaw status and should be after Hierarchy Functions > Reorder Children call"),
            () -> Assertions.assertTrue(firstChildNodeIsCorrect, "The selected node should have a node value of 999.991 but does not"),
            () -> Assertions.assertTrue(secondChildNodeIsCorrect, "The selected node should have a node value of 999.990 but does not")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the order of a blue-line level node's child nodes can be successfully
     * changed in the 'Reorder Children' page, and correctly displayed in the sibling metadata pane <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reorderChildrenBlueLineLevelLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String blueLineAnalysisUUID = datapodObject.getBLAnalyses().get(0).getNodeUUID();
        String blueLine2UUID = datapodObject.getBluelines().get(1).getNodeUUID();
        String blueLine1Value = "1";
        String blueLine2Value = "2";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Drag first child node down one spot
        hierarchySearchPage().searchNodeUuid(blueLineAnalysisUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsReorderChildren();
        reorderChildrenPage().clickAndDragFirstChildDownOne();
        reorderChildrenPage().clickQuickLoadSave();

        //Check that the node order was successfully changed
        hierarchySearchPage().searchNodeUuid(blueLine2UUID);
        String firstChildNode = siblingMetadataPage().getSelectedNodeValue();
        String secondChildNode = siblingMetadataPage().getNodeValueBelowSelectedNode();
        boolean firstChildNodeIsCorrect = firstChildNode.equals(blueLine2Value);
        boolean secondChildNodeIsCorrect = secondChildNode.equals(blueLine1Value);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(firstChildNodeIsCorrect, "The selected node should have a node value of 2 but does not"),
            () -> Assertions.assertTrue(secondChildNodeIsCorrect, "The selected node should have a node value of 1 but does not")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
