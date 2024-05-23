package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

public class KeyedRulebookConversionTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - JETS-19871 <br>
     * SUMMARY - This test verifies after inserting a file as a sibling, creating a workflow, and deleting nodes with promoting children,
     * that the corner piece text in the remaining nodes are correct for each node <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void assignmentOfArbitraryCitesGRCTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        String docName = "Insurance Authority Circular.xml";
        String gradeHeadLevelNodeValue = "Circulars";
        String nodeValue2 = "Circulars on Regulatory Matters";
        String nodeValue3 = "2017";
        String nodeValue4 = "Insurance Intermediaries Quality Assurance Scheme Study Notes for Insurance Intermediaries Qualifying Examination";
        String nodeValue5 = "Test the normal assignment";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Input file as sibling
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputAsSibling();
        hierarchyInputAsSiblingPage().selectFileToUpload(docName);
        hierarchyInputAsSiblingPage().clickSave();

        //Get workflow id
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
        hierarchyNavigatePage().switchToWindow(HierarchyPageElements.PAGE_TITLE);

        //Check that workflow has finished
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("The workflow with id: %s did not finish", workflowId));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that GradeHead level node is displayed
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        boolean gradeHeadLevelNodeAppeared = siblingMetadataPage().isNodeDisplayedWithValue(gradeHeadLevelNodeValue);
        Assertions.assertTrue(gradeHeadLevelNodeAppeared, "The GradeHead level node didn't appear when it should");

        //Get the node uuids of all the newly inserted nodes
        siblingMetadataPage().selectNodesByValue(gradeHeadLevelNodeValue);
        hierarchyTreePage().clickExpandButtonNextToGivenValue(gradeHeadLevelNodeValue);

        boolean nodeValue2Exists = hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(nodeValue2);
        boolean nodeValue3Exists = hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(nodeValue3);
        boolean nodeValue4Exists = hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(nodeValue4);
        boolean nodeValue5Exists = hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(nodeValue5);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeValue2Exists, "Node 2 was not found in the tree."),
            () -> Assertions.assertTrue(nodeValue3Exists, "Node 3 was not found in the tree."),
            () -> Assertions.assertTrue(nodeValue4Exists, "Node 4 was not found in the tree."),
            () -> Assertions.assertTrue(nodeValue5Exists, "Node 5 was not found in the tree.")
        );

        //Check that HID matches corner piece text
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(nodeValue4);
        boolean selectedNodeHasExpectedValue = siblingMetadataPage().getSelectedNodeValue().equals(nodeValue4);
        Assertions.assertTrue(selectedNodeHasExpectedValue, "The selected node did not have the expected value!");
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        String cornerPieceText = editorTextPage().getCornerPieceText();
        int cornerPieceHIDValue = Integer.parseInt(cornerPieceText);
        boolean cornerPieceEqualsHID = cornerPieceHIDValue > 0 && cornerPieceHIDValue < 999999999;
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the node value equals the corner piece text
        siblingMetadataPage().selectNodesByValue(nodeValue5);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        String cornerPieceText2 = editorTextPage().getCornerPieceText();
        boolean cornerPieceEqualsNodeValue = cornerPieceText2.equals(nodeValue5);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(cornerPieceEqualsHID, "The Cornerpiece HID value is valid, meaning it is between 0 and 999,999,999"),
            () -> Assertions.assertTrue(cornerPieceEqualsNodeValue, "The Cornerpiece text doesn't match the node's value")
        );
    }

    @AfterEach
    public void setNodeDeleted()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
