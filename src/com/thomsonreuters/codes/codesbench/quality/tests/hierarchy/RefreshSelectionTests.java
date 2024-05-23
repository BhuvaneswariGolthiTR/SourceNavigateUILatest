package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ParentageGraphPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
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

public class RefreshSelectionTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the selected node appears in all 4 panes of the Hierarchy Edit page
     * after selecting the 'Refresh Selection' context menu option in the sibling metadata, parentage graph, and disp/deriv panes <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void refreshSelectionLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(0);
        HierarchyDatapodConfiguration.getConfig().setGradeCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String keyword = "@";
        String nodeUUID = datapodObject.getGrades().get(0).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String searchTerm = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);
        String treeNodeValue = keyword + " " + searchTerm;

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().quickSearch(keyword, searchTerm);

        //Check that node appears in all 4 panes
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(searchTerm);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(treeNodeValue);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(searchTerm);

        //Refresh selection through the sibling metadata pane
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(treeNodeValue);

        //Check that the node appears in all 4 panes
        boolean metadataNodeAppears2 = siblingMetadataPage().isNodeDisplayedWithValue(searchTerm);
        boolean navTreeNodeAppears2 = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(treeNodeValue);
        boolean parentageGraphNodeAppears2 = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears2 = dispositionDerivationPage().isNodeValueDisplayed(searchTerm);

        //Refresh selection through the parentage graph pane
        parentageGraphPage().clickExpandButton();
        parentageGraphPage().rightClickSelectedParentageGraphNodeImage();
        parentageGraphContextMenu().refreshSelection();
        parentageGraphPage().clickExpandButton();

        //Check that the node appears in all 4 panes
        boolean metadataNodeAppears3 = siblingMetadataPage().isNodeDisplayedWithValue(searchTerm);
        boolean navTreeNodeAppears3 = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(treeNodeValue);
        boolean parentageGraphNodeAppears3 = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears3 = dispositionDerivationPage().isNodeValueDisplayed(searchTerm);

        //Refresh selection through the Disp/Deriv pane
        dispositionDerivationPage().rightClickSelectedNodeImage();
        dispositionDerivationContextMenu().refreshSelectionGraph();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(treeNodeValue);

        //Check that the node appears in all 4 panes
        boolean metadataNodeAppears4 = siblingMetadataPage().isNodeDisplayedWithValue(searchTerm);
        boolean navTreeNodeAppears4 = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(treeNodeValue);
        boolean parentageGraphNodeAppears4 = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears4 = dispositionDerivationPage().isNodeValueDisplayed(searchTerm);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(metadataNodeAppears, "The node with the given value doesn't appear in the sibling metadata pane when it should"),
                        () -> Assertions.assertTrue(navTreeNodeAppears, "The node with the given value doesn't appear in the navigation tree pane when it should"),
                        () -> Assertions.assertTrue(parentageGraphNodeAppears, "The node with the given value doesn't appear in the parentage graph pane when it should"),
                        () -> Assertions.assertTrue(dispDerivNodeAppears, "The node with the given value doesn't appear in the disposition/derivation pane when it should"),
                        () -> Assertions.assertTrue(metadataNodeAppears2, "The node with the given value doesn't appear in the sibling metadata pane when it should"),
                        () -> Assertions.assertTrue(navTreeNodeAppears2, "The node with the given value doesn't appear in the navigation tree pane when it should"),
                        () -> Assertions.assertTrue(parentageGraphNodeAppears2, "The node with the given value doesn't appear in the parentage graph pane when it should"),
                        () -> Assertions.assertTrue(dispDerivNodeAppears2, "The node with the given value doesn't appear in the disposition/derivation pane when it should"),
                        () -> Assertions.assertTrue(metadataNodeAppears3, "The node with the given value doesn't appear in the sibling metadata pane when it should"),
                        () -> Assertions.assertTrue(navTreeNodeAppears3, "The node with the given value doesn't appear in the navigation tree pane when it should"),
                        () -> Assertions.assertTrue(parentageGraphNodeAppears3, "The node with the given value doesn't appear in the parentage graph pane when it should"),
                        () -> Assertions.assertTrue(dispDerivNodeAppears3, "The node with the given value doesn't appear in the disposition/derivation pane when it should"),
                        () -> Assertions.assertTrue(metadataNodeAppears4, "The node with the given value doesn't appear in the sibling metadata pane when it should"),
                        () -> Assertions.assertTrue(navTreeNodeAppears4, "The node with the given value doesn't appear in the navigation tree pane when it should"),
                        () -> Assertions.assertTrue(parentageGraphNodeAppears4, "The node with the given value doesn't appear in the parentage graph pane when it should"),
                        () -> Assertions.assertTrue(dispDerivNodeAppears4, "The node with the given value doesn't appear in the disposition/derivation pane when it should")
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