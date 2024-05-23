package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.InputAsChildPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import java.sql.Connection;
import java.util.ArrayList;

public class SectionLevelInputAsChildGRCTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that an error message appears after attempting to input a file as a child of a section level node <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void sectionLevelInputAsChildGRCTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputAsChild();
        boolean errorMessageAppears = inputAsChildPage().isElementDisplayed(InputAsChildPageElements.ERROR_MESSAGE_FOR_SECTION_NODE);
        boolean inputAsChildWindowClosed = inputAsChildPage().clickCancel();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(errorMessageAppears, "The given error message didn't appear when it should have"),
            () -> Assertions.assertTrue(inputAsChildWindowClosed, "The 'Input as Child' window did not close when it should have")
        );
    }

    /**
     * STORY/BUG - HALCYONST-11115, HALCYONST-11011, and HALCYONST-1090 <br>
     * SUMMARY - This test verifies that the hierarchy function 'Input as Child' works correctly and can be found in the navigation tree pane <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void gradeLevelInputAsChildGRCTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        HierarchyDatapodConfiguration.getConfig().setChapterCount(1);
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String nodeUUID = datapodObject.getAllNodes().get(0).getNodeUUID();
        String gradeNodeTreeValue = "QED Testing Document";
        String fileToInput = "sample Keyed Guernsey File (2).xml";
        String child1NodeValue = "1-REGULATORY REQUIREMENTS AND GUIDANCE NOTES";
        String child2NodeValue = "1-INTRODUCTION";
        String nodeValue1 = "1.1 Background and Scope";
        String nodeValue2 = "1.2 Purpose of the Handbook";
        String nodeValue3 = "1.3 Contents of the Handbook";
        String nodeValue4 = "1.4 Risk-Based Approach";
        int HIDLowerLimit = 1000000;
        int HIDUpperLimit = 999999999;

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Input file as child
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        String nodeVols = siblingMetadataPage().getSelectedNodeVols();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputAsChild();

        inputAsChildPage().selectFileToUpload(fileToInput);
        inputAsChildPage().clickOk();

        //Check that the workflow finished
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
        hierarchyNavigatePage().switchToWindow(HierarchyPageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, String.format("The workflow with id: %s did not finish", workflowId));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the file was inputted as a child
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        hierarchyTreePage().clickExpandButtonNextToGivenValue(gradeNodeTreeValue);
        boolean childNodeAppears = hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(child1NodeValue);
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child1NodeValue);

        siblingMetadataPage().selectNodesByValue(child1NodeValue);
        boolean publishStatusColumn = hierarchyNavigatePage().doesPublishingColumnExist();
        String insertNodeVols = siblingMetadataPage().getSelectedNodeVols();
        int insertNodeHID = Integer.parseInt(siblingMetadataPage().getSelectedNodeHID());

        // Implicit checks making sure those nodes exist
        hierarchyTreePage().clickExpandButtonNextToGivenValue(child1NodeValue);
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child2NodeValue);
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(nodeValue1);
        siblingMetadataPage().selectNodesByValue(nodeValue2);
        siblingMetadataPage().selectNodesByValue(nodeValue3);
        siblingMetadataPage().selectNodesByValue(nodeValue4);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(childNodeAppears, "The expected sibling node does not appear when it should have"),
            () -> Assertions.assertEquals(nodeVols, insertNodeVols, String.format("The first child node has %s vols value instead of %s", nodeVols, insertNodeVols)),
            () -> Assertions.assertTrue(HIDLowerLimit <= insertNodeHID, String.format("The first child node HID value is %d. It is LESS than %d", insertNodeHID, HIDLowerLimit)),
            () -> Assertions.assertTrue(insertNodeHID <= HIDUpperLimit, String.format("The inputted sibling node HID value is %d. It  is more than %d", insertNodeHID, HIDUpperLimit)),
            () -> Assertions.assertFalse(publishStatusColumn, "Publish status column EXIST for RISK USER")
        );
    }

    @AfterEach
    public void deleteNodes()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
