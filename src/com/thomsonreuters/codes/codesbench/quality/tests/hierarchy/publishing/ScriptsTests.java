package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ScriptsTests extends TestService
{
    Connection connection;

    HierarchyDatapodObject datapodObject;

    /**
     * STORY: HALCYONST - 7889 <br>
     * SUMMARY: When users remove scripts at a parent level that doesn't actually have the script assigned, but some of its descendants do have the script assigned,
     * the remove script process changes all nodes to a Not Published publishing status even when no changes to script assignments were made and no WIP versions were created.
     * Workflows for removing pubtag scripts should only change a node's publishing status to Not Published when script assignments for the nodes have actually been removed or a WIP version has been created <br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addRemoveScriptFromParentAndChapterNodeTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        HierarchyDatapodConfiguration.getConfig().setChapterCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String grandParentNodeUuid = datapodObject.getSubtitles().get(0).getNodeUUID();
        String grandParentContentUuid = datapodObject.getSubtitles().get(0).getContentUUID();

        String parent1NodeUuid = datapodObject.getChapters().get(0).getNodeUUID();
        String parent1ContentUuid = datapodObject.getChapters().get(0).getContentUUID();

        String parent2ContentUuid = datapodObject.getChapters().get(1).getContentUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String parent2NodeValue = HierarchyDatabaseUtils.getNodeValue(parent2ContentUuid, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);

        String childNode1OfParentNode1ContentUuid = datapodObject.getSections().get(0).getContentUUID();
        String childNode2OfParentNode1ContentUuid = datapodObject.getSections().get(1).getContentUUID();
        String childNode2OfParentNode1Value = HierarchyDatabaseUtils.getNodeValue(childNode2OfParentNode1ContentUuid, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);

        String childNode1OfParentNode2ContentUuid = datapodObject.getSections().get(2).getContentUUID();
        String childNode2OfParentNode2ContentUuid = datapodObject.getSections().get(3).getContentUUID();
        String childNode2OfParentNode2Value = HierarchyDatabaseUtils.getNodeValue(childNode2OfParentNode2ContentUuid, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);

        String pubTagValue = "CIV";
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();

        //Set nodes to 'Ready' status to prep data for the test
        PublishingDatabaseUtils.setPublishingNodeToReady(grandParentContentUuid,contentSetId,connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(parent1ContentUuid,contentSetId,connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(parent2ContentUuid,contentSetId,connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childNode1OfParentNode1ContentUuid,contentSetId,connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childNode2OfParentNode1ContentUuid,contentSetId,connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childNode1OfParentNode2ContentUuid,contentSetId,connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childNode2OfParentNode2ContentUuid,contentSetId,connection);

        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetId, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Check that both parent nodes have a publishing status of 'Ready'
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(grandParentNodeUuid);
        if(!siblingMetadataPage().isSelectedNodeReadyStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setReadyPublishingStatus();
            Assertions.assertTrue(siblingMetadataPage().isSelectedNodeReadyStatus(), "Parent Node 1 should have a publishing status of 'Ready'");
        }

        hierarchySearchPage().searchNodeUuid(parent1NodeUuid);
        if(!siblingMetadataPage().isSelectedNodeReadyStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setReadyPublishingStatus();
            Assertions.assertTrue(siblingMetadataPage().isSelectedNodeReadyStatus(), "Parent Node 1 should have a publishing status of 'Ready'");
        }

        siblingMetadataPage().selectNodesByValue(parent2NodeValue);
        if(!siblingMetadataPage().isSelectedNodeReadyStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setReadyPublishingStatus();
            Assertions.assertTrue(siblingMetadataPage().isSelectedNodeReadyStatus(),"Parent Node 2 should have a publishing status of 'Ready' but it does not");
        }

        //Check that all child nodes are set to 'Ready' status
        hierarchySearchPage().searchContentUuid(childNode1OfParentNode1ContentUuid);
        if(!siblingMetadataPage().isSelectedNodeReadyStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setReadyPublishingStatus();
            Assertions.assertTrue(siblingMetadataPage().isSelectedNodeReadyStatus(),"Child Node 1 of Parent 1 should have a publishing status of 'Ready' but it does not");
        }

        siblingMetadataPage().selectNodesByValue(childNode2OfParentNode1Value);
        if(!siblingMetadataPage().isSelectedNodeReadyStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setReadyPublishingStatus();
            Assertions.assertTrue(siblingMetadataPage().isSelectedNodeReadyStatus(),"Child Node 2 of Parent 1 should have a publishing status of 'Ready' but it does not");
        }

        hierarchySearchPage().searchContentUuid(childNode1OfParentNode2ContentUuid);
        if(!siblingMetadataPage().isSelectedNodeReadyStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setReadyPublishingStatus();
            Assertions.assertTrue(siblingMetadataPage().isSelectedNodeReadyStatus(),"Child Node 1 of Parent 2 have a publishing status of 'Ready' but it does not");
        }

        siblingMetadataPage().selectNodesByValue(childNode2OfParentNode2Value);
        if(!siblingMetadataPage().isSelectedNodeReadyStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setReadyPublishingStatus();
            Assertions.assertTrue(siblingMetadataPage().isSelectedNodeReadyStatus(),"Child Node 2 of Parent 2 have a publishing status of 'Ready' but it does not");
        }

        //Add script to node
        hierarchySearchPage().searchNodeUuid(parent1NodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsAddToScript();
        addAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(pubTagValue);
        addAssignedScriptsContextMenu().assignWithDescendants();

        //Check that the workflow started and finished
        boolean workflowStarted = yourWorkflowHasBeenCreatedPage().workflowHasStarted();
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        Assertions.assertTrue(workflowStarted,"The workflow didn't started when it should have. id:" + workflowId);

        yourWorkflowHasBeenCreatedPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean addAssignedScriptsWindowClosed = addAssignedScriptsPage().isAddAssignedScriptsPageClosed();
        Assertions.assertTrue(addAssignedScriptsWindowClosed,"The 'Add Assigned Scripts' page did not close when it should have");

        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedTenMinutes();
        Assertions.assertTrue(workflowFinished,"The workflow did not finish- id: " + workflowId);

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the selected node and its children have a publishing status of 'Not Published'
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        boolean parentNode1IsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchySearchPage().searchContentUuid(childNode1OfParentNode1ContentUuid);
        boolean childNode1OfParentNode1IsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean childNode2OfParentNode1IsSetToNotPublished = siblingMetadataPage().isNodeBelowSelectedNodeNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(parentNode1IsSetToNotPublished,"Parent 1 should have a publishing status of 'Not Published' but does not"),
            () -> Assertions.assertTrue(childNode1OfParentNode1IsSetToNotPublished,"Child 1 of parent 1 should have a publishing status of 'Not Published' but it does not"),
            () -> Assertions.assertTrue(childNode2OfParentNode1IsSetToNotPublished,"Child 2 of parent 1 should have a publishing status of 'Not Published' but it does not")
        );

        //Set Parent node 1 and its children to 'Ready' status
        PublishingDatabaseUtils.setPublishingNodeToReady(parent1ContentUuid,contentSetId,connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childNode1OfParentNode1ContentUuid,contentSetId,connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childNode2OfParentNode1ContentUuid,contentSetId,connection);

        //Check that selected node and its children are now set to 'Ready' publishing status
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        boolean parentNode1IsNotSetToNotPublished2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        hierarchySearchPage().searchContentUuid(childNode1OfParentNode1ContentUuid);
        boolean childNode1OfParentNode1IsNotSetToNotPublished2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        siblingMetadataPage().selectNodesByValue(childNode2OfParentNode1Value);
        boolean childNode2OfParentNode1IsNotSetToNotPublished2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        //Check that the grandparent node has the correct publishing status and remove script
        hierarchySearchPage().searchNodeUuid(grandParentNodeUuid);
        boolean grandParentNodeIsSetToReady = siblingMetadataPage().isSelectedNodeReadyStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(parentNode1IsNotSetToNotPublished2,"parent 1 should have a publishing status of 'Ready' but does not"),
            () -> Assertions.assertTrue(childNode1OfParentNode1IsNotSetToNotPublished2,"Child 1 of parent 1 should have a publishing status of 'Ready' but it does not"),
            () -> Assertions.assertTrue(childNode2OfParentNode1IsNotSetToNotPublished2,"Child 2 of parent 1 should have a publishing status of 'Ready' but it does not"),
            () -> Assertions.assertTrue(grandParentNodeIsSetToReady,"The grandparent node should have a publishing status of 'Ready' but does not")
        );

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        removeAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(pubTagValue);
        removeAssignedScriptsContextMenu().removeWithDescendants();

        //Check that the workflow to remove the script started and finished
        boolean workflowStarted2 = yourWorkflowHasBeenCreatedPage().workflowHasStarted();
        Assertions.assertTrue(workflowStarted2,"The workflow didn't started when it should have");

        String workflowId2 = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean removeAssignedScriptsWindowClosed = removeAssignedScriptsPage().isRemoveAssignedScriptsPageClosed();
        Assertions.assertTrue(removeAssignedScriptsWindowClosed,"The 'Remove Assigned Scripts' page did not close when it should have");

        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId2);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished2 = workflowSearchPage().checkFirstWorkflowFinishedTenMinutes();
        Assertions.assertTrue(workflowFinished2,"The workflow 2 did not finish. id: " + workflowId2);

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the selected node and its children have a publishing status of 'Not Published'
        hierarchySearchPage().searchNodeUuid(parent1NodeUuid);
        boolean parentNode1IsSetToNotPublished2 = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchySearchPage().searchContentUuid(childNode1OfParentNode1ContentUuid);
        boolean childNode1OfParentNode1IsSetToNotPublished2 = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean childNode2OfParentNode1IsSetToNotPublished2 = siblingMetadataPage().isNodeBelowSelectedNodeNotPublished();

        //Check that the selected node and its children do not have a publishing status of 'Not Published'
        hierarchySearchPage().searchContentUuid(parent2ContentUuid);
        boolean parentNode2IsSetToReady2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        hierarchySearchPage().searchContentUuid(childNode1OfParentNode2ContentUuid);
        boolean childNode1OfParentNode2IsSetToReady2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        siblingMetadataPage().selectNodesByValue(childNode2OfParentNode2Value);
        boolean childNode2OfParentNode2IsSetToReady2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(parentNode1IsSetToNotPublished2,"Parent node 1 should have a publishing status of 'Not Published' but does not"),
            () -> Assertions.assertTrue(childNode1OfParentNode1IsSetToNotPublished2,"child node 1, of parent 1, should have a publishing status of 'Not Published' but it does not"),
            () -> Assertions.assertTrue(childNode2OfParentNode1IsSetToNotPublished2,"Child node 2, of parent 1, should have a publishing status of 'Not Published' but it does not"),
            () -> Assertions.assertTrue(parentNode2IsSetToReady2,"Parent 2 should have a publishing status of 'Ready' but does not"),
            () -> Assertions.assertTrue(childNode1OfParentNode2IsSetToReady2,"Child Node 1 of parent 2 should have a publishing status of 'Ready' but does not"),
            () -> Assertions.assertTrue(childNode2OfParentNode2IsSetToReady2,"Child node 2 of parent 2 should have a publishing status of 'Ready' but does not")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }

        BaseDatabaseUtils.disconnect(connection);
    }
}
