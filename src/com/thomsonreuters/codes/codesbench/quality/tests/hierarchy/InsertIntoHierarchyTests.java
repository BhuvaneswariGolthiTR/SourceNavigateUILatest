package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

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
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class InsertIntoHierarchyTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies after inserting a new node into the hierarchy, that the new node is shown in the sibling metadata pane
     * and that scripts of the new node are the same as the initial node. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertIntoHierarchyGradeHeadLevelLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String titleNodeUUID = datapodObject.getAllNodes().get(0).getNodeUUID();
        String value = "Test";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Get list of scripts from initial node
        hierarchySearchPage().searchNodeUuid(titleNodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        List<String> listOfScriptsBefore = removeAssignedScriptsPage().getListOfAllScripts();
        /*
        NOTE: For this test, it's not expected that Checkpoint scripts are inherited to nodes added via
        the Insert In Hierarchy function.  This lambda function will check each script pulled from the UI
        and only add those that do not contain "Checkpoint" to the list we'll check against later in the test.
        I'd really like to be able to just remove it from the list, but get a ConcurrentModificationException.
        There may be other Checkpoint scripts named similarly, but those can be added to this list.
         */
        List<String> listOfScripts = new ArrayList<>();
        listOfScriptsBefore.forEach
        (
            script ->
            {
                if (!script.contains("Checkpoint"))
                {
                    listOfScripts.add(script);
                }
            }
        );
        removeAssignedScriptsPage().clickClose();

        //Create new node with given filters
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        insertNewNodesPage().setNewNodeDepthByRowNumber("0",1);//This method has starting index of 1, used by EPAM so won't refactor
        insertNewNodesPage().setWestlawFormatStatus("Default");
        insertNewNodesPage().setNodeTypeofGivenRow("GRADE",0);
        insertNewNodesPage().setKeywordOfGivenRow("T.",0);
        insertNewNodesPage().setValueToGivenRow(value,0);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow("01/05/2017",1);//This method has starting index of 1, used by EPAM so won't refactor
        insertNewNodesPage().setEffectiveEndDateOfGivenRow("",0);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        //Check that the new node is displayed and has same scripts as original
        boolean isNewNodeDisplayed = siblingMetadataPage().isNodeDisplayedWithValue(value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        List<String> listOfScriptsAfter = removeAssignedScriptsPage().getListOfAllScripts();
        boolean nodeScriptsAreEqual = listOfScripts.equals(listOfScriptsAfter);
        removeAssignedScriptsPage().clickClose();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isNewNodeDisplayed,"The new node is not shown in the sibling metadata pane when it should"),
            () -> Assertions.assertTrue(nodeScriptsAreEqual,"Scripts of the new node should match those of the original node")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies after inserting a new node, that the new node has the same scripts as the original node
     * and that the node below the new node has the correct value and keyword <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertIntoHierarchySectionLevelLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionType("SECTION");
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String value = "Test";
        String nodeBelowUUID = datapodObject.getContainerNodes().get(0).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeBelowValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeBelowUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);
        String nodeBelowKeyword = "NOD CONTAINER";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Get list of scripts from original node
        hierarchySearchPage().searchNodeUuid(sectionNodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        List<String> listOfScriptsBefore = removeAssignedScriptsPage().getListOfAllScripts();
        removeAssignedScriptsPage().clickClose();

        //Create new node with given filters
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        insertNewNodesPage().setNewNodeDepthByRowNumber("1",1);//This method has starting index of 1, used by EPAM so won't refactor
        insertNewNodesPage().setWestlawFormatStatus("Default");
        insertNewNodesPage().setNodeTypeofGivenRow("SECTION",0);
        insertNewNodesPage().setKeywordOfGivenRow("&sect;",0);
        insertNewNodesPage().setValueToGivenRow(value,0);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow("01/05/2017",1);//This method has starting index of 1, used by EPAM so won't refactor
        insertNewNodesPage().setEffectiveEndDateOfGivenRow("",0);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        //Check that new node is displayed and scripts are unchanged
        boolean isNewNodeDisplayed = siblingMetadataPage().isNodeDisplayedWithValue(value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        List<String> listOfScriptsAfter = removeAssignedScriptsPage().getListOfAllScripts();
        boolean nodeScriptsAreEqual = listOfScriptsBefore.equals(listOfScriptsAfter);
        removeAssignedScriptsPage().clickClose();

        //Check that the node below the new node is correct
        String nodeValueBelowSelected = siblingMetadataPage().getNodeValueBelowSelectedNode();
        boolean nodeBelowSelectedNodeIsCorrect = nodeValueBelowSelected.equals(nodeBelowValue);
        String nodeKeywordBelowSelected = siblingMetadataPage().getNodeKeywordBelowSelectedNode();
        boolean nodeBelowSelectedNodeIsCorrect2 = nodeKeywordBelowSelected.equals(nodeBelowKeyword);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isNewNodeDisplayed,"The new node is not displayed in the sibling metadata pane when it should be"),
            () -> Assertions.assertTrue(nodeScriptsAreEqual,"The new node scripts don't match the initial node scripts when they should"),
            () -> Assertions.assertTrue(nodeBelowSelectedNodeIsCorrect,"The node value below the selected node is incorrect"),
            () -> Assertions.assertTrue(nodeBelowSelectedNodeIsCorrect2,"The node keyword below the selected node is incorrect")
        );
    }

    @AfterEach
    public void deleteNode()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}